package de.joesst.dev.fulfillmenttools.routingplans;

import java.time.Instant;

/**
 * A single entry in the status history of a routing plan.
 *
 * <p>Maps to the {@code RoutingPlanHistory} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param status  The routing plan status at the time of the history entry.
 * @param created The point in time when the status was set.
 */
public record RoutingPlanHistory(
        String status,
        Instant created
) {}
