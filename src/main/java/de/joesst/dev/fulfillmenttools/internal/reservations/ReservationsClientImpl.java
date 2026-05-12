package de.joesst.dev.fulfillmenttools.internal.reservations;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
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
        SdkHttpResponse response = execute(buildListRequest(request).build());
        ReservationListResponse body = responseHandler.handle(response, ReservationListResponse.class);
        String cursor = body.pageInfo() != null ? body.pageInfo().endCursor() : null;
        return new Page<>(body.reservations() != null ? body.reservations() : List.of(), cursor);
    }

    @Override
    public Iterable<Reservation> listAll(ReservationListRequest request) {
        return Pages.all(cursor -> {
            ReservationListRequest r = cursor == null
                    ? request
                    : request.toBuilder().after(cursor).build();
            return list(r);
        });
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
        return transport.executeAsync(buildListRequest(request).build()).thenApply(response -> {
            ReservationListResponse body = responseHandler.handle(response, ReservationListResponse.class);
            String cursor = body.pageInfo() != null ? body.pageInfo().endCursor() : null;
            return new Page<>(body.reservations() != null ? body.reservations() : List.of(), cursor);
        });
    }

    private SdkHttpRequest.Builder buildListRequest(ReservationListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/reservations");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.after() != null) builder.queryParam("after", request.after());

        return builder;
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
