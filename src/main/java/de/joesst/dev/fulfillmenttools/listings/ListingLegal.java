package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Legal information associated with a listing.
 *
 * <p>Maps to the {@code ListingLegal} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param hsCode The Harmonized System code for customs classification (max 50 characters).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingLegal(
        String hsCode
) {}
