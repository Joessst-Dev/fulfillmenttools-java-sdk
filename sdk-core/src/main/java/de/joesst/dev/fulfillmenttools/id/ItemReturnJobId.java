package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for an item return job ({@code itemReturnJob.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ItemReturnJobId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ItemReturnJobId(String value) {
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

        public ItemReturnJobId build() {
            return new ItemReturnJobId(value);
        }
    }
}
