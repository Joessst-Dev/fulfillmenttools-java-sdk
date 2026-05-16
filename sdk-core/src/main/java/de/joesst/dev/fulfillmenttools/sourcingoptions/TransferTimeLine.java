package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Timeline information for a sourcing option transfer, describing key timing milestones.
 *
 * <p>Maps to the {@code TransferTimeLine} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param calculationStartPoint      the base point from which all time calculations are derived
 * @param earliestPickingStart       the earliest time picking can begin
 * @param latestPickingStart         the latest time picking can begin
 * @param targetTime                 the desired fulfillment time
 * @param earliestPossibleDeliveryTime the earliest time the items can be delivered
 * @param usedTransitTimeInDays      the transit days used for timeline calculation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransferTimeLine(
        Instant calculationStartPoint,
        Instant earliestPickingStart,
        Instant latestPickingStart,
        Instant targetTime,
        Instant earliestPossibleDeliveryTime,
        Integer usedTransitTimeInDays
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Instant calculationStartPoint;
        private Instant earliestPickingStart;
        private Instant latestPickingStart;
        private Instant targetTime;
        private Instant earliestPossibleDeliveryTime;
        private Integer usedTransitTimeInDays;

        public Builder calculationStartPoint(Instant calculationStartPoint) { this.calculationStartPoint = calculationStartPoint; return this; }
        public Builder earliestPickingStart(Instant earliestPickingStart) { this.earliestPickingStart = earliestPickingStart; return this; }
        public Builder latestPickingStart(Instant latestPickingStart) { this.latestPickingStart = latestPickingStart; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder earliestPossibleDeliveryTime(Instant earliestPossibleDeliveryTime) { this.earliestPossibleDeliveryTime = earliestPossibleDeliveryTime; return this; }
        public Builder usedTransitTimeInDays(Integer usedTransitTimeInDays) { this.usedTransitTimeInDays = usedTransitTimeInDays; return this; }

        public TransferTimeLine build() {
            return new TransferTimeLine(calculationStartPoint, earliestPickingStart, latestPickingStart, targetTime, earliestPossibleDeliveryTime, usedTransitTimeInDays);
        }
    }
}
