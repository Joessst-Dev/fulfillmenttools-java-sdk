package de.joesst.dev.fulfillmenttools.externalactions;

/**
 * Discriminator type for an external action definition.
 *
 * <p>Maps to the {@code ExternalActionType} enum in the fulfillmenttools OpenAPI spec.
 */
public enum ExternalActionType {

    /** An action that opens a blank link in the browser. */
    BLANK_LINK,

    /** A form-based action that collects structured user input. */
    FORM,

    /** A comment action that allows the user to add a free-text comment. */
    COMMENT
}
