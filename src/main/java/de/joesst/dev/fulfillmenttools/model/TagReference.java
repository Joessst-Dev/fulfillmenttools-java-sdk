package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A reference to a tag entity, carrying the tag's identifier and value.
 *
 * <p>Used wherever the fulfillmenttools API attaches tag references to a resource
 * (e.g. facilities, listings). Maps to the {@code TagReference} schema in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id    the unique identifier of the tag
 * @param value the value or label of the tag
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TagReference(String id, String value) {}
