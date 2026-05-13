package de.joesst.dev.fulfillmenttools.internal.routingstrategies;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.id.RoutingStrategyId;
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
    public RoutingStrategy get(RoutingStrategyId routingStrategyId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routing/strategies/" + routingStrategyId.value())
                .build();
        return responseHandler.handle(execute(request), RoutingStrategy.class);
    }

    @Override
    public Page<RoutingStrategy> list(RoutingStrategyListRequest request) {
        RoutingStrategyListResponse body = responseHandler.handle(execute(buildListRequest(request)), RoutingStrategyListResponse.class);
        return new Page<>(body.routingStrategies() != null ? body.routingStrategies() : List.of(), null);
    }

    @Override
    public Iterable<RoutingStrategy> listAll(RoutingStrategyListRequest request) {
        return Pages.all(cursor -> list(request));
    }

    @Override
    public RoutingStrategy create(CreateRoutingStrategyRequest request) {
        CreateRoutingStrategyBody body = new CreateRoutingStrategyBody(request.nameLocalized());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routing/strategies")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), RoutingStrategy.class);
    }

    @Override
    public RoutingStrategy update(RoutingStrategyId routingStrategyId, UpdateRoutingStrategyRequest request) {
        UpdateRoutingStrategyBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/routing/strategies/" + routingStrategyId.value())
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), RoutingStrategy.class);
    }

    @Override
    public CompletableFuture<RoutingStrategy> getAsync(RoutingStrategyId routingStrategyId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routing/strategies/" + routingStrategyId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, RoutingStrategy.class));
    }

    @Override
    public CompletableFuture<Page<RoutingStrategy>> listAsync(RoutingStrategyListRequest request) {
        return transport.executeAsync(buildListRequest(request)).thenApply(response -> {
            RoutingStrategyListResponse body = responseHandler.handle(response, RoutingStrategyListResponse.class);
            return new Page<>(body.routingStrategies() != null ? body.routingStrategies() : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<RoutingStrategy> createAsync(CreateRoutingStrategyRequest request) {
        CreateRoutingStrategyBody body = new CreateRoutingStrategyBody(request.nameLocalized());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routing/strategies")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, RoutingStrategy.class));
    }

    @Override
    public CompletableFuture<RoutingStrategy> updateAsync(RoutingStrategyId routingStrategyId, UpdateRoutingStrategyRequest request) {
        UpdateRoutingStrategyBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/routing/strategies/" + routingStrategyId.value())
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, RoutingStrategy.class));
    }

    private SdkHttpRequest buildListRequest(RoutingStrategyListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routing/strategies");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());

        return builder.build();
    }

    private UpdateRoutingStrategyBody buildUpdateBody(UpdateRoutingStrategyRequest request) {
        return new UpdateRoutingStrategyBody(
                request.version(),
                request.nameLocalized(),
                request.rootNode(),
                request.globalConfiguration()
        );
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
