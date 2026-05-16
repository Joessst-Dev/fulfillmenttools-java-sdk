package de.joesst.dev.fulfillmenttools.purchaseorders;

/**
 * The type of requested delivery date on a purchase order.
 *
 * <p>Maps to the {@code InputRequestedDate.type} field in the fulfillmenttools OpenAPI specification.
 */
public enum RequestedDateType {
    /** Deliver as soon as possible; no specific date required. */
    ASAP,
    /** Deliver at or before the specific date given in {@code value}. */
    TIME_POINT
}
