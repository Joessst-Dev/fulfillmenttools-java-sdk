package de.joesst.dev.fulfillmenttools.model;

/**
 * A reference to a tag with its identifier and value.
 *
 * @param id the unique identifier of the tag.
 * @param value the value or label of the tag.
 */
public record TagReference(String id, String value) {}
