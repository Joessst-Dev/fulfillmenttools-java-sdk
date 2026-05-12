package de.joesst.dev.fulfillmenttools.internal.returns;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.returns.CreateReturnRequest;
import de.joesst.dev.fulfillmenttools.returns.Return;
import de.joesst.dev.fulfillmenttools.returns.ReturnListRequest;
import de.joesst.dev.fulfillmenttools.returns.ReturnsClient;
import de.joesst.dev.fulfillmenttools.returns.UpdateReturnRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ReturnsClientImpl implements ReturnsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public ReturnsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Return get(String returnId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/returns/" + returnId)
                .build();
        return responseHandler.handle(execute(request), Return.class);
    }

    @Override
    public Page<Return> list(ReturnListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/returns");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }
        if (request.status() != null) {
            builder.queryParam("status", request.status());
        }

        SdkHttpResponse response = execute(builder.build());
        ReturnListResponse body = responseHandler.handle(response, ReturnListResponse.class);
        return new Page<>(
                body.returns() != null ? body.returns() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<Return> listAll(ReturnListRequest request) {
        return Pages.all(cursor -> {
            ReturnListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Return create(CreateReturnRequest request) {
        CreateReturnBody body = new CreateReturnBody(request.facilityRef(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/returns")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Return.class);
    }

    @Override
    public Return update(String returnId, UpdateReturnRequest request) {
        UpdateReturnBody body = new UpdateReturnBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/returns/" + returnId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Return.class);
    }

    @Override
    public CompletableFuture<Return> getAsync(String returnId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/returns/" + returnId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Return.class));
    }

    @Override
    public CompletableFuture<Page<Return>> listAsync(ReturnListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/returns");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }
        if (request.status() != null) {
            builder.queryParam("status", request.status());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            ReturnListResponse body = responseHandler.handle(response, ReturnListResponse.class);
            return new Page<>(body.returns() != null ? body.returns() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Return> createAsync(CreateReturnRequest request) {
        CreateReturnBody body = new CreateReturnBody(request.facilityRef(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/returns")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Return.class));
    }

    @Override
    public CompletableFuture<Return> updateAsync(String returnId, UpdateReturnRequest request) {
        UpdateReturnBody body = new UpdateReturnBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/returns/" + returnId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Return.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
