package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A locale-keyed map of translated strings.
 *
 * <p>Keys are IETF BCP 47 language tags (e.g. {@code en}, {@code de-DE}).
 * Values are the translated text for that locale.
 *
 * <p>Maps to the {@code LocaleString} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param locales the locale-to-text mapping
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LocaleString(Map<String, String> locales) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Map<String, String> locales;

        public Builder locales(Map<String, String> locales) {
            this.locales = locales;
            return this;
        }

        public LocaleString build() {
            return new LocaleString(locales);
        }
    }
}
