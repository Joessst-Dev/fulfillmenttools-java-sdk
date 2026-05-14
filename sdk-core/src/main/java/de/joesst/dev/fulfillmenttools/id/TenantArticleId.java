package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Tenant-assigned identifier for an article ({@code tenantArticleId} in the API).
 *
 * @param value the raw string value
 */
public record TenantArticleId(String value) implements TenantId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public TenantArticleId(String value) {
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

        public TenantArticleId build() {
            return new TenantArticleId(value);
        }
    }
}
