package de.joesst.dev.fulfillmenttools.externalactions;

/**
 * Discriminator type for a form action element.
 *
 * <p>Maps to the {@code ExternalFormActionElementType} enum in the fulfillmenttools OpenAPI spec.
 */
public enum ExternalFormActionElementType {

    /** A single-line or multi-line text input field. */
    TEXT_INPUT,

    /** A headline display element. */
    HEADLINE,

    /** A sub-headline display element. */
    SUBHEADLINE,

    /** A plain text display element. */
    TEXT
}
