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
) implements ExternalActionDefinition {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ExternalActionType type;
        private Boolean isInternal;

        public Builder type(ExternalActionType type) {
            this.type = type;
            return this;
        }

        public Builder isInternal(Boolean isInternal) {
            this.isInternal = isInternal;
            return this;
        }

        public ExternalCommentActionDefinition build() {
            return new ExternalCommentActionDefinition(type, isInternal);
        }
    }
}
