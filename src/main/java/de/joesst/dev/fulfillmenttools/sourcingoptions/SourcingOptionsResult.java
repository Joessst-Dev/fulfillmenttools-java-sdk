package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;

/**
 * The result of evaluating sourcing options for an order.
 *
 * <p>Contains computed feasible sourcing options and the request ID that produced them.
 *
 * @param id The unique identifier of the sourcing options request.
 * @param options The list of feasible sourcing options for the order.
 */
public record SourcingOptionsResult(
        String id,
        List<SourcingOption> options
) {}
