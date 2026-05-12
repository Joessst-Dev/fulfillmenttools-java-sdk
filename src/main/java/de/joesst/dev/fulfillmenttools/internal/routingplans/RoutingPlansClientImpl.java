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
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingplans");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        SdkHttpResponse response = execute(builder.build());
        RoutingPlanListResponse body = responseHandler.handle(response, RoutingPlanListResponse.class);
        return new Page<>(
                body.routingPlans() != null ? body.routingPlans() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<RoutingPlan> listAll(RoutingPlanListRequest request) {
        return Pages.all(cursor -> {
            RoutingPlanListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
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
        UpdateRoutingPlanBody body = new UpdateRoutingPlanBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
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
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingplans");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            RoutingPlanListResponse body = responseHandler.handle(response, RoutingPlanListResponse.class);
            return new Page<>(body.routingPlans() != null ? body.routingPlans() : List.of(), body.nextCursor());
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
        UpdateRoutingPlanBody body = new UpdateRoutingPlanBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
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

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
