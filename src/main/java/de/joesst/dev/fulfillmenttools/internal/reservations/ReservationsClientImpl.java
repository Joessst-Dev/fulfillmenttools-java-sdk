package de.joesst.dev.fulfillmenttools.internal.reservations;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.reservations.CreateReservationRequest;
import de.joesst.dev.fulfillmenttools.reservations.Reservation;
import de.joesst.dev.fulfillmenttools.reservations.ReservationListRequest;
import de.joesst.dev.fulfillmenttools.reservations.ReservationsClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ReservationsClientImpl implements ReservationsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public ReservationsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Reservation get(String reservationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/reservations/" + reservationId)
                .build();
        return responseHandler.handle(execute(request), Reservation.class);
    }

    @Override
    public Page<Reservation> list(ReservationListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/reservations");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }

        SdkHttpResponse response = execute(builder.build());
        ReservationListResponse body = responseHandler.handle(response, ReservationListResponse.class);
        return new Page<>(
                body.reservations() != null ? body.reservations() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<Reservation> listAll(ReservationListRequest request) {
        return Pages.all(cursor -> {
            ReservationListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Reservation create(CreateReservationRequest request) {
        CreateReservationBody body = new CreateReservationBody(
                request.facilityRef(), request.tenantArticleId(), request.quantity());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/reservations")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Reservation.class);
    }

    @Override
    public void delete(String reservationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/reservations/" + reservationId)
                .build();
        responseHandler.handleVoid(execute(request));
    }

    @Override
    public CompletableFuture<Reservation> getAsync(String reservationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/reservations/" + reservationId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Reservation.class));
    }

    @Override
    public CompletableFuture<Page<Reservation>> listAsync(ReservationListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/reservations");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            ReservationListResponse body = responseHandler.handle(response, ReservationListResponse.class);
            return new Page<>(body.reservations() != null ? body.reservations() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Reservation> createAsync(CreateReservationRequest request) {
        CreateReservationBody body = new CreateReservationBody(
                request.facilityRef(), request.tenantArticleId(), request.quantity());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/reservations")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Reservation.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String reservationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/reservations/" + reservationId)
                .build();
        return transport.executeAsync(request).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
