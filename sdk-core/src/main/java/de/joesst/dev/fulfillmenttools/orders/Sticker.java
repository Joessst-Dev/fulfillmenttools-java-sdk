package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A visual sticker attached to an order for categorization and display in the platform UI.
 *
 * <p>Maps to the {@code Sticker} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param key           Unique identifier for the sticker.
 * @param nameLocalized Localized display names keyed by locale (e.g. {@code en_US}).
 * @param name          The translated display name (resolved from {@code nameLocalized}).
 * @param color         Optional color code for the sticker (e.g. {@code #19b6b5}).
 * @param priority      Display priority (1–10000); lower value means higher priority.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Sticker(
        String key,
        Map<String, String> nameLocalized,
        String name,
        String color,
        Integer priority
) {

    /**
     * Returns a builder for constructing a {@code Sticker}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Sticker}.
     */
    public static final class Builder {

        private String key;
        private Map<String, String> nameLocalized;
        private String name;
        private String color;
        private Integer priority;

        private Builder() {}

        /**
         * Sets the unique identifier for the sticker.
         * @param key the sticker key
         * @return this builder
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Sets the localized display names keyed by locale.
         * @param nameLocalized map of locale to localized name
         * @return this builder
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        /**
         * Sets the translated display name.
         * @param name the display name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the optional color code for the sticker.
         * @param color the color code (e.g. {@code #19b6b5})
         * @return this builder
         */
        public Builder color(String color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the display priority (1–10000); lower value means higher priority.
         * @param priority the priority value
         * @return this builder
         */
        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Builds a {@link Sticker}.
         *
         * @return a new instance.
         */
        public Sticker build() {
            return new Sticker(key, nameLocalized, name, color, priority);
        }
    }
}
