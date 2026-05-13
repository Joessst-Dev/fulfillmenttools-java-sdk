package de.joesst.dev.fulfillmenttools.routingplans;

import java.time.Instant;

/**
 * The earliest point in time at which picking for a routing plan may begin.
 *
 * <p>Maps to the {@code EarliestPickingStart} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code PickingStartBase}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param targetTime               The target time used as the baseline for calculations (required).
 * @param carrierRef               Optional reference to the carrier.
 * @param earliestPickingStartDate The earliest date and time at which picking may begin (required).
 */
public record EarliestPickingStart(
        Instant targetTime,
        String carrierRef,
        Instant earliestPickingStartDate
) {}
