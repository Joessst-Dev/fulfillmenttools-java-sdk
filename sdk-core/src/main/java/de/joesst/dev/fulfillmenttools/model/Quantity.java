package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Represents an integer quantity of items in fulfillmenttools.
 *
 * <p>Maps to the {@code Quantity} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param value the quantity count
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Quantity(Integer value) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Integer value;

        public Builder value(Integer value) {
            this.value = value;
            return this;
        }

        public Quantity build() {
            return new Quantity(value);
        }
    }
}
