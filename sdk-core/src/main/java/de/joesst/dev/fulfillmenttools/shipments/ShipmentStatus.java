package de.joesst.dev.fulfillmenttools.shipments;

/**
 * The lifecycle status of a shipment.
 *
 * <p>Maps to the {@code ShipmentStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum ShipmentStatus {
    INITIAL,
    REQUEST,
    RETRYABLE,
    CONFIRMED,
    COMPLETED,
    CANCELED,
    OBSOLETE
}
