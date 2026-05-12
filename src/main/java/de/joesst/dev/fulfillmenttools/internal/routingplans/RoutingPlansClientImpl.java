package de.joesst.dev.fulfillmenttools.internal.routingplans;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.routingplans.CreateRoutingPlanRequest;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlan;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlanListRequest;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlansClient;
import de.joesst.dev.fulfillmenttools.routingplans.UpdateRoutingPlanRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class RoutingPlansClientImpl implements RoutingPlansClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public RoutingPlansClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public RoutingPlan get(String routingPlanId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingplans/" + routingPlanId)
                .build();
        return responseHandler.handle(execute(request), RoutingPlan.class);
    }

    @Override
    public Page<RoutingPlan> list(RoutingPlanListRequest request) {
        RoutingPlanListResponse body = responseHandler.handle(execute(buildListRequest(request)), RoutingPlanListResponse.class);
        return new Page<>(body.routingPlans() != null ? body.routingPlans() : List.of(), null);
    }

    @Override
    public Iterable<RoutingPlan> listAll(RoutingPlanListRequest request) {
        return Pages.all(cursor -> list(request));
    }

    @Override
    public RoutingPlan create(CreateRoutingPlanRequest request) {
        CreateRoutingPlanBody body = new CreateRoutingPlanBody(request.name());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routingplans")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), RoutingPlan.class);
    }

    @Override
    public RoutingPlan update(String routingPlanId, UpdateRoutingPlanRequest request) {
        UpdateRoutingPlanBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/routingplans/" + routingPlanId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), RoutingPlan.class);
    }

    @Override
    public void delete(String routingPlanId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/routingplans/" + routingPlanId)
                .build();
        responseHandler.handleVoid(execute(request));
    }

    @Override
    public CompletableFuture<RoutingPlan> getAsync(String routingPlanId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingplans/" + routingPlanId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, RoutingPlan.class));
    }

    @Override
    public CompletableFuture<Page<RoutingPlan>> listAsync(RoutingPlanListRequest request) {
        return transport.executeAsync(buildListRequest(request)).thenApply(response -> {
            RoutingPlanListResponse body = responseHandler.handle(response, RoutingPlanListResponse.class);
            return new Page<>(body.routingPlans() != null ? body.routingPlans() : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<RoutingPlan> createAsync(CreateRoutingPlanRequest request) {
        CreateRoutingPlanBody body = new CreateRoutingPlanBody(request.name());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routingplans")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, RoutingPlan.class));
    }

    @Override
    public CompletableFuture<RoutingPlan> updateAsync(String routingPlanId, UpdateRoutingPlanRequest request) {
        UpdateRoutingPlanBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/routingplans/" + routingPlanId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, RoutingPlan.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String routingPlanId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/routingplans/" + routingPlanId)
                .build();
        return transport.executeAsync(request).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    private SdkHttpRequest buildListRequest(RoutingPlanListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingplans");

        if (request.orderRef() != null) builder.queryParam("orderRef", request.orderRef());

        return builder.build();
    }

    private UpdateRoutingPlanBody buildUpdateBody(UpdateRoutingPlanRequest request) {
        UpdateRoutingPlanBody.ModifyRoutingPlanAction action =
                new UpdateRoutingPlanBody.ModifyRoutingPlanAction(request.facilityRef(), request.status());
        return new UpdateRoutingPlanBody(request.version(), List.of(action));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
