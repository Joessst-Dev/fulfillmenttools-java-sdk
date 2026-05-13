package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

import java.util.concurrent.CompletableFuture;

/**
 * Client for accessing and managing routing plans via the fulfillmenttools API.
 *
 * <p>A routing plan represents the decision-making record for fulfilling a customer order
 * from one or more facilities. This interface provides methods for retrieving, listing,
 * creating, updating, and deleting routing plans.
 */
public interface RoutingPlansClient {

    /**
     * Retrieves a single routing plan by ID.
     *
     * @param routingPlanId the unique identifier of the routing plan
     * @return the routing plan
     * @throws FulfillmenttoolsException if the request fails or the routing plan is not found
     */
    RoutingPlan get(String routingPlanId);

    /**
     * Asynchronously retrieves a single routing plan by ID.
     *
     * @param routingPlanId the unique identifier of the routing plan
     * @return a future that completes with the routing plan
     * @throws FulfillmenttoolsException if the request fails or the routing plan is not found
     */
    CompletableFuture<RoutingPlan> getAsync(String routingPlanId);

    /**
     * Retrieves a page of routing plans matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a page of routing plans
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<RoutingPlan> list(RoutingPlanListRequest request);

    /**
     * Asynchronously retrieves a page of routing plans matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a future that completes with a page of routing plans
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<Page<RoutingPlan>> listAsync(RoutingPlanListRequest request);

    /**
     * Retrieves all routing plans matching the given filter criteria, handling pagination automatically.
     *
     * <p>Returns an iterable that fetches pages on-demand as you iterate.
     * Note: this is a synchronous, blocking operation.
     *
     * @param request filter parameters (pagination settings are ignored)
     * @return an iterable of all matching routing plans
     * @throws FulfillmenttoolsException if any request fails
     */
    Iterable<RoutingPlan> listAll(RoutingPlanListRequest request);

    /**
     * Creates a new routing plan.
     *
     * @param request the creation request with routing plan configuration
     * @return the created routing plan
     * @throws FulfillmenttoolsException if the request fails
     */
    RoutingPlan create(CreateRoutingPlanRequest request);

    /**
     * Asynchronously creates a new routing plan.
     *
     * @param request the creation request with routing plan configuration
     * @return a future that completes with the created routing plan
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<RoutingPlan> createAsync(CreateRoutingPlanRequest request);

    /**
     * Updates an existing routing plan.
     *
     * <p>The request must include the current version for optimistic locking.
     *
     * @param routingPlanId the unique identifier of the routing plan
     * @param request the update request containing new values
     * @return the updated routing plan
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    RoutingPlan update(String routingPlanId, UpdateRoutingPlanRequest request);

    /**
     * Asynchronously updates an existing routing plan.
     *
     * @param routingPlanId the unique identifier of the routing plan
     * @param request the update request containing new values
     * @return a future that completes with the updated routing plan
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<RoutingPlan> updateAsync(String routingPlanId, UpdateRoutingPlanRequest request);

    /**
     * Deletes a routing plan.
     *
     * @param routingPlanId the unique identifier of the routing plan to delete
     * @throws FulfillmenttoolsException if the request fails or the routing plan is not found
     */
    void delete(String routingPlanId);

    /**
     * Asynchronously deletes a routing plan.
     *
     * @param routingPlanId the unique identifier of the routing plan to delete
     * @return a future that completes when the routing plan is deleted
     * @throws FulfillmenttoolsException if the request fails or the routing plan is not found
     */
    CompletableFuture<Void> deleteAsync(String routingPlanId);
}
