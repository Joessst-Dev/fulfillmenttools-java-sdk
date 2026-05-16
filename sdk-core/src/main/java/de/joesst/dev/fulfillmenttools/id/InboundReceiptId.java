package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for an inbound receipt ({@code inboundReceipt.id} in the API).
 *
 * @param value the raw UUID string
 */
public record InboundReceiptId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public InboundReceiptId(String value) {
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

        public InboundReceiptId build() {
            return new InboundReceiptId(value);
        }
    }
}
