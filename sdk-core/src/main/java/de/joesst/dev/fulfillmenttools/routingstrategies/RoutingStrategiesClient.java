package de.joesst.dev.fulfillmenttools.routingstrategies;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.id.RoutingStrategyId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for accessing and managing routing strategies via the fulfillmenttools API.
 *
 * <p>A routing strategy determines how fulfillment orders are routed to facilities.
 * Only one strategy can be active at a time. This interface provides methods for retrieving,
 * listing, creating, and updating routing strategies.
 */
public interface RoutingStrategiesClient {

    /**
     * Retrieves a single routing strategy by ID.
     *
     * @param routingStrategyId the unique identifier of the routing strategy
     * @return the routing strategy
     * @throws FulfillmenttoolsException if the request fails or the routing strategy is not found
     */
    RoutingStrategy get(RoutingStrategyId routingStrategyId);

    /**
     * Asynchronously retrieves a single routing strategy by ID.
     *
     * @param routingStrategyId the unique identifier of the routing strategy
     * @return a future that completes with the routing strategy
     * @throws FulfillmenttoolsException if the request fails or the routing strategy is not found
     */
    CompletableFuture<RoutingStrategy> getAsync(RoutingStrategyId routingStrategyId);

    /**
     * Retrieves a page of routing strategies with optional pagination.
     *
     * @param request filter and pagination parameters
     * @return a page of routing strategies
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<RoutingStrategy> list(RoutingStrategyListRequest request);

    /**
     * Asynchronously retrieves a page of routing strategies with optional pagination.
     *
     * @param request filter and pagination parameters
     * @return a future that completes with a page of routing strategies
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<Page<RoutingStrategy>> listAsync(RoutingStrategyListRequest request);

    /**
     * Retrieves all routing strategies, handling pagination automatically.
     *
     * <p>Returns an iterable that fetches pages on-demand as you iterate.
     * Note: this is a synchronous, blocking operation.
     *
     * @param request pagination parameters (page size controls batch fetching)
     * @return an iterable of all routing strategies
     * @throws FulfillmenttoolsException if any request fails
     */
    Iterable<RoutingStrategy> listAll(RoutingStrategyListRequest request);

    /**
     * Creates a new routing strategy.
     *
     * @param request the creation request with routing strategy configuration
     * @return the created routing strategy
     * @throws FulfillmenttoolsException if the request fails
     */
    RoutingStrategy create(CreateRoutingStrategyRequest request);

    /**
     * Asynchronously creates a new routing strategy.
     *
     * @param request the creation request with routing strategy configuration
     * @return a future that completes with the created routing strategy
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<RoutingStrategy> createAsync(CreateRoutingStrategyRequest request);

    /**
     * Updates an existing routing strategy.
     *
     * <p>The request must include the current version for optimistic locking.
     *
     * @param routingStrategyId the unique identifier of the routing strategy
     * @param request the update request containing new values
     * @return the updated routing strategy
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    RoutingStrategy update(RoutingStrategyId routingStrategyId, UpdateRoutingStrategyRequest request);

    /**
     * Asynchronously updates an existing routing strategy.
     *
     * @param routingStrategyId the unique identifier of the routing strategy
     * @param request the update request containing new values
     * @return a future that completes with the updated routing strategy
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<RoutingStrategy> updateAsync(RoutingStrategyId routingStrategyId, UpdateRoutingStrategyRequest request);
}
