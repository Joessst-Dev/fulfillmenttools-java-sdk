package de.joesst.dev.fulfillmenttools.facilities;

/**
 * An email address with an optional recipient type classification.
 *
 * @param value the email address string
 * @param recipient classification of who should receive emails at this address (e.g. "Manager", "Warehouse")
 */
public record FacilityEmailAddress(String value, String recipient) {}
