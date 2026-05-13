package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * An attribute attached to an order line item article, used for customization and display.
 *
 * <p>Maps to the {@code ArticleAttributeItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param key            The attribute key (e.g. {@code %%subtitle%%} for subtitle display).
 * @param value          The string value of the attribute.
 * @param type           The data type of the attribute value ({@code STRING}, {@code NUMBER},
 *                       {@code CURRENCY}, {@code BOOLEAN}). Defaults to {@code STRING}.
 * @param category       The category controlling how the platform uses the attribute
 *                       (e.g. {@code descriptive}, {@code miscellaneous}, {@code salesPrice}).
 * @param priority       Sort priority within the category; lower value means higher priority.
 * @param keyLocalized   Localized translations for the attribute key.
 * @param valueLocalized Localized translations for the attribute value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticleAttribute(
        String key,
        String value,
        String type,
        String category,
        Integer priority,
        Map<String, String> keyLocalized,
        Map<String, String> valueLocalized
) {

    /**
     * Returns a builder for constructing an {@code ArticleAttribute}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ArticleAttribute}.
     */
    public static final class Builder {

        private String key;
        private String value;
        private String type;
        private String category;
        private Integer priority;
        private Map<String, String> keyLocalized;
        private Map<String, String> valueLocalized;

        private Builder() {}

        /**
         * Sets the attribute key.
         * @param key the attribute key
         * @return this builder
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Sets the string value of the attribute.
         * @param value the attribute value
         * @return this builder
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the data type of the attribute value.
         * @param type the attribute type (e.g. {@code STRING}, {@code NUMBER})
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the category controlling how the platform uses the attribute.
         * @param category the attribute category
         * @return this builder
         */
        public Builder category(String category) {
            this.category = category;
            return this;
        }

        /**
         * Sets the sort priority within the category.
         * @param priority the priority value
         * @return this builder
         */
        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Sets the localized translations for the attribute key.
         * @param keyLocalized map of locale to localized key
         * @return this builder
         */
        public Builder keyLocalized(Map<String, String> keyLocalized) {
            this.keyLocalized = keyLocalized;
            return this;
        }

        /**
         * Sets the localized translations for the attribute value.
         * @param valueLocalized map of locale to localized value
         * @return this builder
         */
        public Builder valueLocalized(Map<String, String> valueLocalized) {
            this.valueLocalized = valueLocalized;
            return this;
        }

        /**
         * Builds an {@link ArticleAttribute}.
         *
         * @return a new instance.
         */
        public ArticleAttribute build() {
            return new ArticleAttribute(key, value, type, category, priority, keyLocalized, valueLocalized);
        }
    }
}
