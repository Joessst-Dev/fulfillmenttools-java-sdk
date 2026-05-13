package de.joesst.dev.fulfillmenttools.externalactions;

/**
 * Visual style hint for a form action element.
 *
 * <p>Only applicable when the element type is {@link ExternalFormActionElementType#TEXT}.
 *
 * <p>Maps to the {@code style} enum on {@code ExternalFormActionElement} in the
 * fulfillmenttools OpenAPI spec.
 */
public enum ExternalFormActionElementStyle {

    /** Default body text style. */
    BODY,

    /** Informational style (typically blue). */
    INFO,

    /** Warning style (typically yellow/orange). */
    WARN,

    /** Error style (typically red). */
    ERROR
}
