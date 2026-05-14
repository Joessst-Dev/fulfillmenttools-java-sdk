package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A display-only element in a {@link ExternalFormActionDefinition}: headline, sub-headline,
 * or plain text.
 *
 * <p>Discriminator values: {@code "HEADLINE"}, {@code "SUBHEADLINE"}, {@code "TEXT"}
 *
 * <p>Maps to the {@code ExternalFormActionElement} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param elementType    discriminator field (required)
 * @param titleLocalized localized display text; key is locale (e.g. {@code "en_US"}), value
 *                       is translation (required)
 * @param title          non-localized fallback title
 * @param style          visual style hint; only applicable when {@code elementType} is
 *                       {@link ExternalFormActionElementType#TEXT}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FormDisplayElement(
        ExternalFormActionElementType elementType,
        Map<String, String> titleLocalized,
        String title,
        ExternalFormActionElementStyle style
) implements FormElement {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ExternalFormActionElementType elementType;
        private Map<String, String> titleLocalized;
        private String title;
        private ExternalFormActionElementStyle style;

        public Builder elementType(ExternalFormActionElementType elementType) {
            this.elementType = elementType;
            return this;
        }

        public Builder titleLocalized(Map<String, String> titleLocalized) {
            this.titleLocalized = titleLocalized;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder style(ExternalFormActionElementStyle style) {
            this.style = style;
            return this;
        }

        public FormDisplayElement build() {
            return new FormDisplayElement(elementType, titleLocalized, title, style);
        }
    }
}
