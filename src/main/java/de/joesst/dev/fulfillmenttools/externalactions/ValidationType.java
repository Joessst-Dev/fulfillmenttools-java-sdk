package de.joesst.dev.fulfillmenttools.externalactions;

/**
 * Discriminator type for a form input element validation rule.
 *
 * <p>Maps to the {@code validationType} enum on {@code BaseValidation} in the fulfillmenttools
 * OpenAPI spec.
 */
public enum ValidationType {

    /** Validate the input as a plain string, with optional length constraints. */
    STRING,

    /** Validate the input as a floating-point number, with optional min/max constraints. */
    FLOAT,

    /** Validate the input as an integer, with optional min/max constraints. */
    INTEGER
}
