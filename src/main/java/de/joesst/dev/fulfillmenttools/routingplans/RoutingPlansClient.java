package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface RoutingPlansClient {

    RoutingPlan get(String routingPlanId);
    CompletableFuture<RoutingPlan> getAsync(String routingPlanId);

    Page<RoutingPlan> list(RoutingPlanListRequest request);
    CompletableFuture<Page<RoutingPlan>> listAsync(RoutingPlanListRequest request);

    Iterable<RoutingPlan> listAll(RoutingPlanListRequest request);

    RoutingPlan create(CreateRoutingPlanRequest request);
    CompletableFuture<RoutingPlan> createAsync(CreateRoutingPlanRequest request);

    RoutingPlan update(String routingPlanId, UpdateRoutingPlanRequest request);
    CompletableFuture<RoutingPlan> updateAsync(String routingPlanId, UpdateRoutingPlanRequest request);

    void delete(String routingPlanId);
    CompletableFuture<Void> deleteAsync(String routingPlanId);
}
