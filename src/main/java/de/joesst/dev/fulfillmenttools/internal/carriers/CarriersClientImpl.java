package de.joesst.dev.fulfillmenttools.internal.carriers;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.carriers.Carrier;
import de.joesst.dev.fulfillmenttools.carriers.CarrierListRequest;
import de.joesst.dev.fulfillmenttools.carriers.CarriersClient;
import de.joesst.dev.fulfillmenttools.carriers.CreateCarrierRequest;
import de.joesst.dev.fulfillmenttools.carriers.UpdateCarrierRequest;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class CarriersClientImpl implements CarriersClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public CarriersClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Carrier get(String carrierId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/carriers/" + carrierId)
                .build();
        return responseHandler.handle(execute(request), Carrier.class);
    }

    @Override
    public Page<Carrier> list(CarrierListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/carriers");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        SdkHttpResponse response = execute(builder.build());
        CarrierListResponse body = responseHandler.handle(response, CarrierListResponse.class);
        return new Page<>(
                body.carriers() != null ? body.carriers() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<Carrier> listAll(CarrierListRequest request) {
        return Pages.all(cursor -> {
            CarrierListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Carrier create(CreateCarrierRequest request) {
        CreateCarrierBody body = new CreateCarrierBody(request.name());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/carriers")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Carrier.class);
    }

    @Override
    public Carrier update(String carrierId, UpdateCarrierRequest request) {
        UpdateCarrierBody body = new UpdateCarrierBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/carriers/" + carrierId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Carrier.class);
    }

    @Override
    public void delete(String carrierId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/carriers/" + carrierId)
                .build();
        responseHandler.handleVoid(execute(request));
    }

    @Override
    public CompletableFuture<Carrier> getAsync(String carrierId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/carriers/" + carrierId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Carrier.class));
    }

    @Override
    public CompletableFuture<Page<Carrier>> listAsync(CarrierListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/carriers");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            CarrierListResponse body = responseHandler.handle(response, CarrierListResponse.class);
            return new Page<>(body.carriers() != null ? body.carriers() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Carrier> createAsync(CreateCarrierRequest request) {
        CreateCarrierBody body = new CreateCarrierBody(request.name());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/carriers")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Carrier.class));
    }

    @Override
    public CompletableFuture<Carrier> updateAsync(String carrierId, UpdateCarrierRequest request) {
        UpdateCarrierBody body = new UpdateCarrierBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/carriers/" + carrierId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Carrier.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String carrierId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/carriers/" + carrierId)
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
