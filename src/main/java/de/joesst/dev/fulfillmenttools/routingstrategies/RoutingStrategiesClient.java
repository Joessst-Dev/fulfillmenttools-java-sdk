package de.joesst.dev.fulfillmenttools.routingstrategies;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface RoutingStrategiesClient {

    RoutingStrategy get(String routingStrategyId);
    CompletableFuture<RoutingStrategy> getAsync(String routingStrategyId);

    Page<RoutingStrategy> list(RoutingStrategyListRequest request);
    CompletableFuture<Page<RoutingStrategy>> listAsync(RoutingStrategyListRequest request);

    Iterable<RoutingStrategy> listAll(RoutingStrategyListRequest request);

    RoutingStrategy create(CreateRoutingStrategyRequest request);
    CompletableFuture<RoutingStrategy> createAsync(CreateRoutingStrategyRequest request);

    RoutingStrategy update(String routingStrategyId, UpdateRoutingStrategyRequest request);
    CompletableFuture<RoutingStrategy> updateAsync(String routingStrategyId, UpdateRoutingStrategyRequest request);
}
