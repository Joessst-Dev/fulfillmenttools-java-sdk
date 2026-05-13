package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.CarrierId;

import java.time.Instant;

/**
 * The latest point in time by which picking for a routing plan must begin.
 *
 * <p>Maps to the {@code LatestPickingStart} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code PickingStartBase}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param targetTime             The target time used as the baseline for calculations (required).
 * @param carrierRef             Optional reference to the carrier.
 * @param latestPickingStartDate The latest date and time by which picking must begin (required).
 */
public record LatestPickingStart(
        Instant targetTime,
        CarrierId carrierRef,
        Instant latestPickingStartDate
) {}
