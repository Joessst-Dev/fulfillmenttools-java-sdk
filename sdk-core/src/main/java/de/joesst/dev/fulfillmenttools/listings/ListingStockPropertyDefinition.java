package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Defines a property that can be captured when recording stock for a listing.
 *
 * <p>Maps to the {@code StockPropertyDefinition} schema in the fulfillmenttools OpenAPI spec.
 * This feature is currently in Alpha status.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param inputType    The input type for the property as rendered by clients;
 *                     one of {@code DATE} or {@code TEXT}.
 * @param required     Whether this property must be provided when recording stock.
 * @param defaultValue An optional default value. If {@code {{NOW}}} is provided,
 *                     it is replaced with the current timestamp at stock recording time.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingStockPropertyDefinition(
        String inputType,
        Boolean required,
        String defaultValue
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String inputType;
        private Boolean required;
        private String defaultValue;

        private Builder() {}

        public Builder inputType(String inputType) { this.inputType = inputType; return this; }
        public Builder required(Boolean required) { this.required = required; return this; }
        public Builder defaultValue(String defaultValue) { this.defaultValue = defaultValue; return this; }

        public ListingStockPropertyDefinition build() {
            return new ListingStockPropertyDefinition(inputType, required, defaultValue);
        }
    }
}
