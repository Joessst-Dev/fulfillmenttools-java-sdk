package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for a zone ({@code zone.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ZoneId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ZoneId(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public String toString() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String value;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public ZoneId build() {
            return new ZoneId(value);
        }
    }
}
