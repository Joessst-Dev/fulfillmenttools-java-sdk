package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Customer-defined identifier for a facility ({@code facility.tenantFacilityId} in the API).
 *
 * <p>Use {@link #toUrn(String)} to produce the URN path segment required for
 * tenant-ID lookups: {@code urn:fft:facility:tenantFacilityId:{value}}.
 *
 * @param value the tenant-supplied facility identifier
 */
public record TenantFacilityId(String value) implements TenantId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public TenantFacilityId(String value) {
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

        public TenantFacilityId build() {
            return new TenantFacilityId(value);
        }
    }
}
