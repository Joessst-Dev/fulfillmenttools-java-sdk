package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A tag reference attached to a listing.
 *
 * <p>Maps to the {@code TagReference} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id    The unique identifier of the tag.
 * @param value The tag value/name.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingTag(
        String id,
        String value
) {}
