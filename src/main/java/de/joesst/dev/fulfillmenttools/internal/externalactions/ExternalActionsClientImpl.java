package de.joesst.dev.fulfillmenttools.internal.externalactions;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.externalactions.CreateExternalActionRequest;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionListRequest;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionsClient;
import de.joesst.dev.fulfillmenttools.externalactions.UpdateExternalActionRequest;
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

public final class ExternalActionsClientImpl implements ExternalActionsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public ExternalActionsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public ExternalAction get(String externalActionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions/" + externalActionId)
                .build();
        return responseHandler.handle(execute(request), ExternalAction.class);
    }

    @Override
    public Page<ExternalAction> list(ExternalActionListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        SdkHttpResponse response = execute(builder.build());
        ExternalActionListResponse body = responseHandler.handle(response, ExternalActionListResponse.class);
        return new Page<>(
                body.externalActions() != null ? body.externalActions() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<ExternalAction> listAll(ExternalActionListRequest request) {
        return Pages.all(cursor -> {
            ExternalActionListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public ExternalAction create(CreateExternalActionRequest request) {
        CreateExternalActionBody body = new CreateExternalActionBody(request.name(), request.actionType());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/externalactions")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), ExternalAction.class);
    }

    @Override
    public ExternalAction update(String externalActionId, UpdateExternalActionRequest request) {
        UpdateExternalActionBody body = new UpdateExternalActionBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/externalactions/" + externalActionId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), ExternalAction.class);
    }

    @Override
    public CompletableFuture<ExternalAction> getAsync(String externalActionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions/" + externalActionId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, ExternalAction.class));
    }

    @Override
    public CompletableFuture<Page<ExternalAction>> listAsync(ExternalActionListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            ExternalActionListResponse body = responseHandler.handle(response, ExternalActionListResponse.class);
            return new Page<>(body.externalActions() != null ? body.externalActions() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<ExternalAction> createAsync(CreateExternalActionRequest request) {
        CreateExternalActionBody body = new CreateExternalActionBody(request.name(), request.actionType());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/externalactions")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, ExternalAction.class));
    }

    @Override
    public CompletableFuture<ExternalAction> updateAsync(String externalActionId, UpdateExternalActionRequest request) {
        UpdateExternalActionBody body = new UpdateExternalActionBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/externalactions/" + externalActionId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, ExternalAction.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
