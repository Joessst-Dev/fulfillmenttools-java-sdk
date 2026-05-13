package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Discriminator for the type of entity referenced in a {@link PackagingUnitContext}.
 */
public enum PackagingUnitContextType {
    /** Context by target postal code. */
    TARGET_POSTALCODE,

    /** Context by target country. */
    TARGET_COUNTRY,

    /** Context by category. */
    CATEGORY
}
