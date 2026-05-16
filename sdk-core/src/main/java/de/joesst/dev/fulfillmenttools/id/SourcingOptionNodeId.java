package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for a sourcing option node ({@code SourcingOptionNode.id} in the API).
 *
 * @param value the raw UUID string
 */
public record SourcingOptionNodeId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SourcingOptionNodeId(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }
    @Override public String toString() { return value; }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String value;

        public Builder value(String value) { this.value = value; return this; }

        public SourcingOptionNodeId build() {
            return new SourcingOptionNodeId(value);
        }
    }
}
