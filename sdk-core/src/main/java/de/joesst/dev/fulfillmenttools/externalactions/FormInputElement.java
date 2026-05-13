package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * An interactive text-input element in a {@link ExternalFormActionDefinition}.
 *
 * <p>Discriminator value: {@code "TEXT_INPUT"}
 *
 * <p>Maps to the {@code ExternalFormActionInputElement} schema in the fulfillmenttools OpenAPI
 * spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param elementType    discriminator field, always
 *                       {@link ExternalFormActionElementType#TEXT_INPUT} (required)
 * @param elementId      unique identifier for this input element within the form (required)
 * @param titleLocalized localized label text; key is locale (e.g. {@code "en_US"}), value is
 *                       translation (required)
 * @param title          non-localized fallback label
 * @param isMandatory    whether the field must be filled before the form can be submitted
 * @param style          optional visual style hint
 * @param validation     optional validation rule applied to the user's input
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FormInputElement(
        ExternalFormActionElementType elementType,
        String elementId,
        Map<String, String> titleLocalized,
        String title,
        Boolean isMandatory,
        ExternalFormActionElementStyle style,
        FieldValidation validation
) implements FormElement {}
