package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A reference to a linked configuration resource with relationship information.
 *
 * @param ref the URI or identifier of the linked configuration
 * @param rel the relationship type (e.g. "self", "parent", "config")
 */
public record LinkedConfiguration(String ref, String rel) {}
