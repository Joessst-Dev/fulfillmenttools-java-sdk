package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Tenant-assigned identifier for a stock record ({@code tenantStockId} in the API).
 *
 * @param value the raw string value
 */
public record TenantStockId(String value) implements TenantId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public TenantStockId(String value) {
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

        public TenantStockId build() {
            return new TenantStockId(value);
        }
    }
}
