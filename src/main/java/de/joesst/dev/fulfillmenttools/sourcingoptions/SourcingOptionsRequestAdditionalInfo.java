package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Additional configuration options for sourcing options requests.
 *
 * @param includeListingCustomAttributes Whether to include custom attributes from the listing in the result.
 */
public record SourcingOptionsRequestAdditionalInfo(Boolean includeListingCustomAttributes) {}
