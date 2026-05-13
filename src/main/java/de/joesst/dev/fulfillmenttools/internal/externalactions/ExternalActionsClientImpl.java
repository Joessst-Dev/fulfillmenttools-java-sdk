package de.joesst.dev.fulfillmenttools.internal.externalactions;

import com.fasterxml.jackson.core.type.TypeReference;
import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.externalactions.CreateExternalActionRequest;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionListRequest;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionsClient;
import de.joesst.dev.fulfillmenttools.externalactions.UpdateExternalActionRequest;
import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
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

    private static final TypeReference<List<ExternalAction>> EXTERNAL_ACTION_LIST_TYPE = new TypeReference<>() {};

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public ExternalActionsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public ExternalAction get(ExternalActionId externalActionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions/" + externalActionId.value())
                .build();
        return responseHandler.handle(execute(request), ExternalAction.class);
    }

    @Override
    public Page<ExternalAction> list(ExternalActionListRequest request) {
        SdkHttpResponse response = execute(buildListRequest(request));
        List<ExternalAction> items = responseHandler.handle(response, EXTERNAL_ACTION_LIST_TYPE);
        return new Page<>(items != null ? items : List.of(), null);
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
        CreateExternalActionBody body = new CreateExternalActionBody(
                request.processRef(), request.nameLocalized(), request.groups(),
                request.action(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/externalactions")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), ExternalAction.class);
    }

    @Override
    public ExternalAction update(ExternalActionId externalActionId, UpdateExternalActionRequest request) {
        UpdateExternalActionBody body = new UpdateExternalActionBody(
                request.version(), request.nameLocalized(), request.groups(),
                request.action(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/externalactions/" + externalActionId.value())
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), ExternalAction.class);
    }

    @Override
    public CompletableFuture<ExternalAction> getAsync(ExternalActionId externalActionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions/" + externalActionId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, ExternalAction.class));
    }

    @Override
    public CompletableFuture<Page<ExternalAction>> listAsync(ExternalActionListRequest request) {
        return transport.executeAsync(buildListRequest(request)).thenApply(response -> {
            List<ExternalAction> items = responseHandler.handle(response, EXTERNAL_ACTION_LIST_TYPE);
            return new Page<>(items != null ? items : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<ExternalAction> createAsync(CreateExternalActionRequest request) {
        CreateExternalActionBody body = new CreateExternalActionBody(
                request.processRef(), request.nameLocalized(), request.groups(),
                request.action(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/externalactions")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, ExternalAction.class));
    }

    @Override
    public CompletableFuture<ExternalAction> updateAsync(ExternalActionId externalActionId, UpdateExternalActionRequest request) {
        UpdateExternalActionBody body = new UpdateExternalActionBody(
                request.version(), request.nameLocalized(), request.groups(),
                request.action(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/externalactions/" + externalActionId.value())
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, ExternalAction.class));
    }

    private SdkHttpRequest buildListRequest(ExternalActionListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/externalactions");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.processRef() != null) builder.queryParam("processRef", request.processRef());
        if (request.groups() != null) {
            request.groups().forEach(g -> builder.queryParam("groups", g));
        }

        return builder.build();
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
