package de.joesst.dev.fulfillmenttools.inbound;

/**
 * The lifecycle status of an inbound delivery (inbound process).
 *
 * <p>Maps to the {@code InboundProcess.status} field in the fulfillmenttools OpenAPI specification.
 */
public enum InboundDeliveryStatus {
    OPEN,
    PARTIAL_DELIVERY,
    ON_HOLD,
    CLOSED
}
