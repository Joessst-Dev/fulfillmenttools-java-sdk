package de.joesst.dev.fulfillmenttools.internal.routingstrategies;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.routingstrategies.CreateRoutingStrategyRequest;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategiesClient;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategy;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategyListRequest;
import de.joesst.dev.fulfillmenttools.routingstrategies.UpdateRoutingStrategyRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class RoutingStrategiesClientImpl implements RoutingStrategiesClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public RoutingStrategiesClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public RoutingStrategy get(String routingStrategyId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingstrategies/" + routingStrategyId)
                .build();
        return responseHandler.handle(execute(request), RoutingStrategy.class);
    }

    @Override
    public Page<RoutingStrategy> list(RoutingStrategyListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingstrategies");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        SdkHttpResponse response = execute(builder.build());
        RoutingStrategyListResponse body = responseHandler.handle(response, RoutingStrategyListResponse.class);
        return new Page<>(
                body.routingStrategies() != null ? body.routingStrategies() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<RoutingStrategy> listAll(RoutingStrategyListRequest request) {
        return Pages.all(cursor -> {
            RoutingStrategyListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public RoutingStrategy create(CreateRoutingStrategyRequest request) {
        CreateRoutingStrategyBody body = new CreateRoutingStrategyBody(request.name());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routingstrategies")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), RoutingStrategy.class);
    }

    @Override
    public RoutingStrategy update(String routingStrategyId, UpdateRoutingStrategyRequest request) {
        UpdateRoutingStrategyBody body = new UpdateRoutingStrategyBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/routingstrategies/" + routingStrategyId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), RoutingStrategy.class);
    }

    @Override
    public CompletableFuture<RoutingStrategy> getAsync(String routingStrategyId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingstrategies/" + routingStrategyId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, RoutingStrategy.class));
    }

    @Override
    public CompletableFuture<Page<RoutingStrategy>> listAsync(RoutingStrategyListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routingstrategies");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            RoutingStrategyListResponse body = responseHandler.handle(response, RoutingStrategyListResponse.class);
            return new Page<>(body.routingStrategies() != null ? body.routingStrategies() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<RoutingStrategy> createAsync(CreateRoutingStrategyRequest request) {
        CreateRoutingStrategyBody body = new CreateRoutingStrategyBody(request.name());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routingstrategies")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, RoutingStrategy.class));
    }

    @Override
    public CompletableFuture<RoutingStrategy> updateAsync(String routingStrategyId, UpdateRoutingStrategyRequest request) {
        UpdateRoutingStrategyBody body = new UpdateRoutingStrategyBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/routingstrategies/" + routingStrategyId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, RoutingStrategy.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
