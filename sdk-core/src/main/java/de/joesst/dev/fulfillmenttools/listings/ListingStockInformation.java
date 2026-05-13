package de.joesst.dev.fulfillmenttools.listings;

/**
 * Stock level information for a listing.
 *
 * <p>Maps to the {@code StockInformation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p><strong>Deprecated:</strong> This field has been deprecated since 11 September 2023.
 * Use the {@code /api/stocks} endpoints instead.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param stock     The total amount of this article in stock for the facility.
 * @param reserved  The reserved portion of the stock (cannot be overridden via API).
 * @param available The actual available quantity (stock minus reserved).
 */
@Deprecated
public record ListingStockInformation(
        Integer stock,
        Integer reserved,
        Integer available
) {}
