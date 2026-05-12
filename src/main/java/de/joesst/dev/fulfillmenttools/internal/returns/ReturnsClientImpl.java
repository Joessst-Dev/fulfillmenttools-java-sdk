package de.joesst.dev.fulfillmenttools.internal.returns;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ReturnsClientImpl implements ReturnsClient {

    private static final TypeReference<List<Return>> RETURN_LIST_TYPE = new TypeReference<>() {};

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
                .url(baseUrl + "/api/itemreturnjobs/" + returnId)
                .build();
        return responseHandler.handle(execute(request), Return.class);
    }

    @Override
    public Page<Return> list(ReturnListRequest request) {
        SdkHttpResponse response = execute(buildListRequest(request).build());
        List<Return> items = responseHandler.handle(response, RETURN_LIST_TYPE);
        return new Page<>(items != null ? items : List.of(), null);
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
        CreateReturnBody body = new CreateReturnBody(
                request.originFacilityRefs(),
                request.status(),
                request.consumerAddresses(),
                request.returnableLineItems(),
                request.notReturnableLineItems(),
                request.scannableCodes(),
                request.shortId(),
                request.tenantOrderId(),
                request.customAttributes()
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/itemreturnjobs")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Return.class);
    }

    @Override
    public Return start(String returnId, int version) {
        return action(returnId, "StartItemReturnJob", version);
    }

    @Override
    public Return finish(String returnId, int version) {
        return action(returnId, "FinishItemReturnJob", version);
    }

    @Override
    public Return restart(String returnId, int version) {
        return action(returnId, "RestartItemReturnJob", version);
    }

    @Override
    public CompletableFuture<Return> getAsync(String returnId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/itemreturnjobs/" + returnId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Return.class));
    }

    @Override
    public CompletableFuture<Page<Return>> listAsync(ReturnListRequest request) {
        return transport.executeAsync(buildListRequest(request).build()).thenApply(response -> {
            List<Return> items = responseHandler.handle(response, RETURN_LIST_TYPE);
            return new Page<>(items != null ? items : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<Return> createAsync(CreateReturnRequest request) {
        CreateReturnBody body = new CreateReturnBody(
                request.originFacilityRefs(),
                request.status(),
                request.consumerAddresses(),
                request.returnableLineItems(),
                request.notReturnableLineItems(),
                request.scannableCodes(),
                request.shortId(),
                request.tenantOrderId(),
                request.customAttributes()
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/itemreturnjobs")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Return.class));
    }

    @Override
    public CompletableFuture<Return> startAsync(String returnId, int version) {
        return actionAsync(returnId, "StartItemReturnJob", version);
    }

    @Override
    public CompletableFuture<Return> finishAsync(String returnId, int version) {
        return actionAsync(returnId, "FinishItemReturnJob", version);
    }

    @Override
    public CompletableFuture<Return> restartAsync(String returnId, int version) {
        return actionAsync(returnId, "RestartItemReturnJob", version);
    }

    private Return action(String returnId, String name, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/itemreturnjobs/" + returnId + "/actions")
                .body(responseHandler.encode(new ReturnJobActionBody(name, version)))
                .build();
        return responseHandler.handle(execute(httpRequest), Return.class);
    }

    private CompletableFuture<Return> actionAsync(String returnId, String name, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/itemreturnjobs/" + returnId + "/actions")
                .body(responseHandler.encode(new ReturnJobActionBody(name, version)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Return.class));
    }

    private SdkHttpRequest.Builder buildListRequest(ReturnListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/itemreturnjobs");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityId() != null) builder.queryParam("facilityId", request.facilityId());
        if (request.itemReturnJobStatus() != null) request.itemReturnJobStatus().forEach(s -> builder.queryParam("itemReturnJobStatus", s));
        if (request.itemReturnStatus() != null) request.itemReturnStatus().forEach(s -> builder.queryParam("itemReturnStatus", s));
        if (request.itemReturnJobScannableCodes() != null) request.itemReturnJobScannableCodes().forEach(c -> builder.queryParam("itemReturnJobScannableCodes", c));
        if (request.itemReturnScannableCodes() != null) request.itemReturnScannableCodes().forEach(c -> builder.queryParam("itemReturnScannableCodes", c));
        if (request.searchTerm() != null) builder.queryParam("searchTerm", request.searchTerm());
        if (request.anonymized() != null) builder.queryParam("anonymized", String.valueOf(request.anonymized()));

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
