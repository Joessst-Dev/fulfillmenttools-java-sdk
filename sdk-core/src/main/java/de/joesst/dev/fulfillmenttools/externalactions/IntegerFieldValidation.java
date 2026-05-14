package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Validation rule that constrains a form input to an integer with optional numeric bounds.
 *
 * <p>Discriminator value: {@code "INTEGER"}
 *
 * <p>Maps to the {@code IntegerValidation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param validationType discriminator field, always {@link ValidationType#INTEGER} (required)
 * @param minValue       optional minimum value
 * @param maxValue       optional maximum value
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record IntegerFieldValidation(
        ValidationType validationType,
        Double minValue,
        Double maxValue
) implements FieldValidation {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ValidationType validationType;
        private Double minValue;
        private Double maxValue;

        public Builder validationType(ValidationType validationType) {
            this.validationType = validationType;
            return this;
        }

        public Builder minValue(Double minValue) {
            this.minValue = minValue;
            return this;
        }

        public Builder maxValue(Double maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public IntegerFieldValidation build() {
            return new IntegerFieldValidation(validationType, minValue, maxValue);
        }
    }
}
