package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface representing the polymorphic {@code action} field of an
 * {@link ExternalAction}, {@link CreateExternalActionRequest}, and
 * {@link UpdateExternalActionRequest}.
 *
 * <p>Implementations are discriminated by the {@code type} property:
 * <ul>
 *   <li>{@code "BLANK_LINK"} — {@link ExternalLinkActionDefinition}</li>
 *   <li>{@code "FORM"} — {@link ExternalFormActionDefinition}</li>
 *   <li>{@code "COMMENT"} — {@link ExternalCommentActionDefinition}</li>
 * </ul>
 *
 * <p>Thread-safety: all implementations are immutable records.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExternalLinkActionDefinition.class,    name = "BLANK_LINK"),
        @JsonSubTypes.Type(value = ExternalFormActionDefinition.class,    name = "FORM"),
        @JsonSubTypes.Type(value = ExternalCommentActionDefinition.class, name = "COMMENT")
})
public sealed interface ExternalActionDefinition
        permits ExternalLinkActionDefinition,
                ExternalFormActionDefinition,
                ExternalCommentActionDefinition {

    /**
     * Returns the discriminator type of this action definition.
     *
     * @return the action type; never {@code null}
     */
    ExternalActionType type();
}
