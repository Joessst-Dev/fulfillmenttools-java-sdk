package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * The type of a sourcing option rating result (e.g. {@code StandardRating} or {@code ToolkitRating}).
 *
 * <p>Wraps the raw type string to prevent accidental misuse across type boundaries.
 *
 * @param value the raw type string
 */
public record RatingResultType(String value) {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public RatingResultType(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @JsonValue
    @Override public String toString() { return value; }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String value;

        public Builder value(String value) { this.value = value; return this; }

        public RatingResultType build() {
            return new RatingResultType(value);
        }
    }
}
