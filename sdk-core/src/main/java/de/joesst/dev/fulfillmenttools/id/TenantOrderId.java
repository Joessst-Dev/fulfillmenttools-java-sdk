package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Customer-defined identifier for an order ({@code order.tenantOrderId} in the API).
 *
 * @param value the tenant-supplied order identifier
 */
public record TenantOrderId(String value) implements TenantId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public TenantOrderId(String value) {
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

        public TenantOrderId build() {
            return new TenantOrderId(value);
        }
    }
}
