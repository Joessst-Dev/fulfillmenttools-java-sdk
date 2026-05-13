package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface representing a single element in a {@link ExternalFormActionDefinition}.
 *
 * <p>Implementations are discriminated by the {@code elementType} property:
 * <ul>
 *   <li>{@code "HEADLINE"}, {@code "SUBHEADLINE"}, {@code "TEXT"} —
 *       {@link FormDisplayElement} (display-only elements)</li>
 *   <li>{@code "TEXT_INPUT"} — {@link FormInputElement} (interactive input)</li>
 * </ul>
 *
 * <p>Maps to the {@code ExternalFormActionElement} / {@code ExternalFormActionInputElement}
 * oneOf in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: all implementations are immutable records.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "elementType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FormDisplayElement.class, name = "HEADLINE"),
        @JsonSubTypes.Type(value = FormDisplayElement.class, name = "SUBHEADLINE"),
        @JsonSubTypes.Type(value = FormDisplayElement.class, name = "TEXT"),
        @JsonSubTypes.Type(value = FormInputElement.class,   name = "TEXT_INPUT")
})
public sealed interface FormElement
        permits FormDisplayElement, FormInputElement {

    /**
     * Returns the element type discriminator.
     *
     * @return the element type; never {@code null}
     */
    ExternalFormActionElementType elementType();
}
