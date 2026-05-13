package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;
import java.util.concurrent.CompletableFuture;

/**
 * Client for evaluating sourcing options via the fulfillmenttools API.
 *
 * <p>The sourcing options engine determines the best fulfillment options for an order
 * by analyzing facility availability, costs, and constraints. This interface provides
 * methods for evaluating orders and retrieving sourcing results.
 */
public interface SourcingOptionsClient {

    /**
     * Evaluates an order to compute feasible sourcing options.
     *
     * @param request the sourcing options request with order and constraints
     * @return the computed sourcing options
     * @throws FulfillmenttoolsException if the evaluation fails
     */
    SourcingOptionsResult evaluate(SourcingOptionsRequest request);

    /**
     * Asynchronously evaluates an order to compute feasible sourcing options.
     *
     * @param request the sourcing options request with order and constraints
     * @return a future that completes with the computed sourcing options
     * @throws FulfillmenttoolsException if the evaluation fails
     */
    CompletableFuture<SourcingOptionsResult> evaluateAsync(SourcingOptionsRequest request);

    /**
     * Retrieves a previously computed sourcing options result by ID.
     *
     * @param sourcingOptionsRequestId the unique identifier of the sourcing options request
     * @return the sourcing options result
     * @throws FulfillmenttoolsException if the request fails or the result is not found
     */
    SourcingOptionsResult get(SourcingOptionsRequestId sourcingOptionsRequestId);

    /**
     * Asynchronously retrieves a previously computed sourcing options result by ID.
     *
     * @param sourcingOptionsRequestId the unique identifier of the sourcing options request
     * @return a future that completes with the sourcing options result
     * @throws FulfillmenttoolsException if the request fails or the result is not found
     */
    CompletableFuture<SourcingOptionsResult> getAsync(SourcingOptionsRequestId sourcingOptionsRequestId);
}
