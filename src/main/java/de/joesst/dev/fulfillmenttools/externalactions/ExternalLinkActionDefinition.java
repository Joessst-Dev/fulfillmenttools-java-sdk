package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * An external action that opens a URL in the browser (blank link).
 *
 * <p>Discriminator value: {@code "BLANK_LINK"}
 *
 * <p>Maps to the {@code ExternalLinkActionDefinition} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type    discriminator field, always {@link ExternalActionType#BLANK_LINK} (required)
 * @param linkUrl the URL to open (required)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExternalLinkActionDefinition(
        ExternalActionType type,
        String linkUrl
) implements ExternalActionDefinition {}
