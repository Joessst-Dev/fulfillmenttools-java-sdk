package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A labelled button (cancel or success) in a {@link ExternalFormActionDefinition}.
 *
 * <p>Maps to the {@code ExternalFormActionButton} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param labelLocalized localized button label; key is locale (e.g. {@code "en_US"}), value is
 *                       translation (required)
 * @param label          non-localized fallback label
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExternalFormActionButton(
        Map<String, String> labelLocalized,
        String label
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Map<String, String> labelLocalized;
        private String label;

        public Builder labelLocalized(Map<String, String> labelLocalized) {
            this.labelLocalized = labelLocalized;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public ExternalFormActionButton build() {
            return new ExternalFormActionButton(labelLocalized, label);
        }
    }
}
