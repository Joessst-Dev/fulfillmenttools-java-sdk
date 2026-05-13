package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * Channel-specific delivery details for a pick job.
 *
 * <p>Maps to the {@code details} property of {@code PickjobDeliveryInformationForCreation}
 * in the fulfillmenttools OpenAPI spec. Exactly one of {@code shipping} or {@code collect}
 * will be populated depending on the delivery channel.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param shipping Shipping-specific details; present when the channel is {@code SHIPPING}.
 * @param collect  Collect-specific details; present when the channel is {@code COLLECT}.
 */
public record PickjobDeliveryInformationDetails(
        DeliveryInformationShipping shipping,
        DeliveryInformationCollect collect
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private DeliveryInformationShipping shipping;
        private DeliveryInformationCollect collect;

        private Builder() {}

        public Builder shipping(DeliveryInformationShipping shipping) { this.shipping = shipping; return this; }
        public Builder collect(DeliveryInformationCollect collect) { this.collect = collect; return this; }

        public PickjobDeliveryInformationDetails build() {
            return new PickjobDeliveryInformationDetails(shipping, collect);
        }
    }
}
