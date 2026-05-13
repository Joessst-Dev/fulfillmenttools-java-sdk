package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * An external action that allows the user to submit a free-text comment.
 *
 * <p>Discriminator value: {@code "COMMENT"}
 *
 * <p>Maps to the {@code ExternalCommentActionDefinition} schema in the fulfillmenttools OpenAPI
 * spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type       discriminator field, always {@link ExternalActionType#COMMENT} (required)
 * @param isInternal whether this action is internal to the fulfillmenttools platform; when
 *                   {@code true} an additional entry in the actions dropdown is shown.
 *                   Defaults to {@code false}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExternalCommentActionDefinition(
        ExternalActionType type,
        Boolean isInternal
) implements ExternalActionDefinition {}
