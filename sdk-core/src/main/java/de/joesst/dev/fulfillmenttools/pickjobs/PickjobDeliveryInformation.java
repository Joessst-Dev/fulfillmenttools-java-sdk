package de.joesst.dev.fulfillmenttools.pickjobs;

import java.time.Instant;

/**
 * Delivery information for a pick job, describing the fulfilment channel and timing.
 *
 * <p>Maps to the {@code PickjobDeliveryInformation} schema in the fulfillmenttools OpenAPI spec.
 * The {@code channel} value determines which sub-object within {@code details} is populated:
 * use {@code "SHIPPING"} for ship-to-customer orders or {@code "COLLECT"} for
 * click-and-collect.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param channel             The fulfillment channel: {@code COLLECT} or {@code SHIPPING}.
 * @param targetTime          The time by which the delivery result is expected.
 * @param targetTimeBaseDate  The base date used for the target-time calculation.
 * @param details             Channel-specific delivery details.
 */
public record PickjobDeliveryInformation(
        String channel,
        Instant targetTime,
        Instant targetTimeBaseDate,
        PickjobDeliveryInformationDetails details
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String channel;
        private Instant targetTime;
        private Instant targetTimeBaseDate;
        private PickjobDeliveryInformationDetails details;

        private Builder() {}

        public Builder channel(String channel) { this.channel = channel; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder targetTimeBaseDate(Instant targetTimeBaseDate) { this.targetTimeBaseDate = targetTimeBaseDate; return this; }
        public Builder details(PickjobDeliveryInformationDetails details) { this.details = details; return this; }

        public PickjobDeliveryInformation build() {
            return new PickjobDeliveryInformation(channel, targetTime, targetTimeBaseDate, details);
        }
    }
}
