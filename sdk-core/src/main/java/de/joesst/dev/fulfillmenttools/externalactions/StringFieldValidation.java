package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Validation rule that constrains a form input to a string with optional length bounds.
 *
 * <p>Discriminator value: {@code "STRING"}
 *
 * <p>Maps to the {@code StringValidation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param validationType discriminator field, always {@link ValidationType#STRING} (required)
 * @param minLength      optional minimum string length
 * @param maxLength      optional maximum string length
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StringFieldValidation(
        ValidationType validationType,
        Double minLength,
        Double maxLength
) implements FieldValidation {}
