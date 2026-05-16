package de.joesst.dev.fulfillmenttools.parcels;

/**
 * The lifecycle status of a parcel.
 *
 * <p>Maps to the {@code ParcelStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum ParcelStatus {
    OPEN,
    PROCESSING,
    DONE,
    FAILED,
    CANCELED,
    OBSOLETE,
    WAITING_FOR_INPUT
}
