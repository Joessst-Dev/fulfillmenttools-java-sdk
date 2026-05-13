package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.CarrierId;

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
        CarrierId carrierRef,
        Instant earliestPickingStartDate
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant targetTime;
        private CarrierId carrierRef;
        private Instant earliestPickingStartDate;

        private Builder() {}

        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder carrierRef(CarrierId carrierRef) { this.carrierRef = carrierRef; return this; }
        public Builder earliestPickingStartDate(Instant earliestPickingStartDate) { this.earliestPickingStartDate = earliestPickingStartDate; return this; }

        public EarliestPickingStart build() {
            return new EarliestPickingStart(targetTime, carrierRef, earliestPickingStartDate);
        }
    }
}
