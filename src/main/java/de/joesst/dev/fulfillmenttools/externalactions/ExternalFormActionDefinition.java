package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * An external action that presents the user with a structured form to fill in.
 *
 * <p>Discriminator value: {@code "FORM"}
 *
 * <p>Maps to the {@code ExternalFormActionDefinition} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type     discriminator field, always {@link ExternalActionType#FORM} (required)
 * @param elements list of form elements; must contain at least one entry (required)
 * @param cancel   the cancel button configuration (required)
 * @param success  the success / submit button configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExternalFormActionDefinition(
        ExternalActionType type,
        List<FormElement> elements,
        ExternalFormActionButton cancel,
        ExternalFormActionButton success
) implements ExternalActionDefinition {}
