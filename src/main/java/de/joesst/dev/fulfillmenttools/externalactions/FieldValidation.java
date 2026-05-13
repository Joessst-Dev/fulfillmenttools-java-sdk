package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface representing a validation rule for a form input element.
 *
 * <p>Implementations are discriminated by the {@code validationType} property:
 * <ul>
 *   <li>{@code "STRING"} — {@link StringFieldValidation}</li>
 *   <li>{@code "INTEGER"} — {@link IntegerFieldValidation}</li>
 *   <li>{@code "FLOAT"} — {@link FloatFieldValidation}</li>
 * </ul>
 *
 * <p>Maps to the {@code BaseValidation} hierarchy in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: all implementations are immutable records.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "validationType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringFieldValidation.class,  name = "STRING"),
        @JsonSubTypes.Type(value = IntegerFieldValidation.class, name = "INTEGER"),
        @JsonSubTypes.Type(value = FloatFieldValidation.class,   name = "FLOAT")
})
public sealed interface FieldValidation
        permits StringFieldValidation, IntegerFieldValidation, FloatFieldValidation {

    /**
     * Returns the discriminator type for this validation rule.
     *
     * @return the validation type; never {@code null}
     */
    ValidationType validationType();
}
