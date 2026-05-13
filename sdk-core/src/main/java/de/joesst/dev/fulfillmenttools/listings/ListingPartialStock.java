package de.joesst.dev.fulfillmenttools.listings;

import java.time.Instant;

/**
 * A partial stock entry attached to a listing.
 *
 * <p>Maps to the {@code PartialStock} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p><strong>Deprecated:</strong> This field has been deprecated since 11 September 2023.
 * Use the {@code /api/stocks} endpoints instead.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantPartialStockId The tenant-assigned identifier for this partial stock entry.
 * @param stockinformation     The stock levels for this partial stock.
 * @param eventLastModified    The timestamp when the inventory domain last reported a change.
 */
@Deprecated
public record ListingPartialStock(
        String tenantPartialStockId,
        ListingStockInformation stockinformation,
        Instant eventLastModified
) {}
