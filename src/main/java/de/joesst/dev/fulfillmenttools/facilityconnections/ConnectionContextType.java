package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Discriminator for the type of entity referenced in a {@link ConnectionContext}.
 */
public enum ConnectionContextType {
    /** A single facility. */
    FACILITY,
    /** A group of facilities. */
    FACILITY_GROUP,
    /** A product category. */
    CATEGORY,
    /** A tag reference. */
    TAG_REFERENCE
}
