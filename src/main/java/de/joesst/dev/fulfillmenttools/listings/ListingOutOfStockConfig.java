package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration for out-of-stock behaviour modes that require additional parameters.
 *
 * <p>Maps to the {@code OutOfStockConfig} schema in the fulfillmenttools OpenAPI spec.
 * This feature is currently in Alpha status.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param preorder Configuration for the {@code PREORDER} out-of-stock behaviour.
 * @param restock  Configuration for the {@code RESTOCK} out-of-stock behaviour.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingOutOfStockConfig(
        Preorder preorder,
        Restock restock
) {

    /**
     * Configuration for pre-order availability.
     *
     * @param availabilityTimeframe The timeframe within which the article becomes available.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Preorder(
            ListingAvailabilityTimeframe availabilityTimeframe
    ) {}

    /**
     * Configuration for restock availability.
     *
     * @param restockableInDays The number of days until the article is expected back in stock.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Restock(
            Integer restockableInDays
    ) {}
}
