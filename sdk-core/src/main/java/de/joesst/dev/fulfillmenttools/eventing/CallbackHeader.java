package de.joesst.dev.fulfillmenttools.eventing;

/**
 * A callback header for webhook subscriptions.
 *
 * @param key the header name
 * @param value the header value
 */
public record CallbackHeader(String key, String value) {}
