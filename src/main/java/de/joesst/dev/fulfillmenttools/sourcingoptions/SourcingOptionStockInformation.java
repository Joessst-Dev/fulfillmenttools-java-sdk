package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Stock availability information for a listing within a sourcing option.
 *
 * <p>Maps to stock information nested within the {@code SourcingOptionListingDetails} schema
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param available      Number of units available for sourcing.
 * @param reserved       Number of units currently reserved.
 * @param shelfCode      Optional shelf or bin code where the article is stored.
 */
public record SourcingOptionStockInformation(
        Integer available,
        Integer reserved,
        String shelfCode
) {}
