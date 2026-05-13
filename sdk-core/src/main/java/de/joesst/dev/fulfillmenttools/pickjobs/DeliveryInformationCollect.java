package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * Collect (click-and-collect) specific delivery details within a pick job's delivery information.
 *
 * <p>Maps to the {@code details.collect} sub-object of
 * {@code PickjobDeliveryInformationForCreation} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param identifier Information identifying the click-and-collect recipient.
 * @param paid       Whether the order has already been paid. Defaults to {@code false}.
 */
public record DeliveryInformationCollect(
        String identifier,
        Boolean paid
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String identifier;
        private Boolean paid;

        private Builder() {}

        public Builder identifier(String identifier) { this.identifier = identifier; return this; }
        public Builder paid(Boolean paid) { this.paid = paid; return this; }

        public DeliveryInformationCollect build() {
            return new DeliveryInformationCollect(identifier, paid);
        }
    }
}
