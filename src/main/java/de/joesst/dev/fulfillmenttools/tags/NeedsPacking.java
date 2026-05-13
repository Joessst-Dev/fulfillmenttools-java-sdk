package de.joesst.dev.fulfillmenttools.tags;

/**
 * Indicates whether a tag requires packing.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param needsPacking {@code true} if the tag requires packing; {@code false} otherwise.
 */
public record NeedsPacking(Boolean needsPacking) {}
