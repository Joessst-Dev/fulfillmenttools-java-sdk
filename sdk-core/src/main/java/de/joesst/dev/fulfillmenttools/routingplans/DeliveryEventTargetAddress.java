package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.FacilityId;

/**
 * Associates a delivery target address with a specific delivery event and facility.
 *
 * <p>Maps to the {@code DeliveryEventTargetAddress} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef   The ID of the facility associated with this delivery event (required).
 * @param targetAddress The delivery address for this event (required).
 * @param deliveryEvent The delivery event identifier (required).
 */
public record DeliveryEventTargetAddress(
        FacilityId facilityRef,
        TargetAddress targetAddress,
        String deliveryEvent
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FacilityId facilityRef;
        private TargetAddress targetAddress;
        private String deliveryEvent;

        private Builder() {}

        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder targetAddress(TargetAddress targetAddress) { this.targetAddress = targetAddress; return this; }
        public Builder deliveryEvent(String deliveryEvent) { this.deliveryEvent = deliveryEvent; return this; }

        public DeliveryEventTargetAddress build() {
            return new DeliveryEventTargetAddress(facilityRef, targetAddress, deliveryEvent);
        }
    }
}
