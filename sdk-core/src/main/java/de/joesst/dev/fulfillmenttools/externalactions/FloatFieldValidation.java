package de.joesst.dev.fulfillmenttools.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Validation rule that constrains a form input to a floating-point number with optional bounds.
 *
 * <p>Discriminator value: {@code "FLOAT"}
 *
 * <p>Maps to the {@code FloatValidation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param validationType discriminator field, always {@link ValidationType#FLOAT} (required)
 * @param minValue       optional minimum value
 * @param maxValue       optional maximum value
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FloatFieldValidation(
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

        public FloatFieldValidation build() {
            return new FloatFieldValidation(validationType, minValue, maxValue);
        }
    }
}
