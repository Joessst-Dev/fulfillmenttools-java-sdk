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
) {}
