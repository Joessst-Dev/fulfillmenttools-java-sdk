package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Defines how the "available until" date for stock items is calculated.
 *
 * <p>Maps to the {@code AvailableUntilDefinition} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param calculationBase The base date for the calculation; one of {@code EXPIRY} or
 *                        {@code CREATION}. If the base cannot be resolved (e.g. missing
 *                        expiry value), the {@code availableUntil} date is left undefined.
 * @param modifier        An optional ISO-8601 duration that shifts the calculated date.
 *                        Use negative values to move the date backwards
 *                        (e.g. {@code "-P30D"} = 30 days before the base date).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingStockAvailableUntil(
        String calculationBase,
        String modifier
) {}
