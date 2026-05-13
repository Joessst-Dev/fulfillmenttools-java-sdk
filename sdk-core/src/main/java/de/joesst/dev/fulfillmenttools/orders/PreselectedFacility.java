package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

/**
 * A pre-selected facility reference for shipping delivery preferences.
 *
 * <p>Maps to the {@code PreselectedFacility} schema in the fulfillmenttools OpenAPI spec.
 * Either {@code facilityRef} or {@code tenantFacilityId} must be provided.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef      The fulfillmenttools {@link FacilityId} facility identifier.
 * @param tenantFacilityId The tenant-specific {@link TenantFacilityId} facility identifier.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreselectedFacility(
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId
) {

    /**
     * Returns a builder for constructing a {@code PreselectedFacility}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PreselectedFacility}.
     */
    public static final class Builder {

        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;

        private Builder() {}

        /**
         * Sets the fulfillmenttools facility identifier.
         * @param facilityRef the facility identifier
         * @return this builder
         */
        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        /**
         * Sets the tenant-specific facility identifier.
         * @param tenantFacilityId the tenant facility identifier
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) {
            this.tenantFacilityId = tenantFacilityId;
            return this;
        }

        /**
         * Builds a {@link PreselectedFacility}.
         *
         * @return a new instance.
         */
        public PreselectedFacility build() {
            return new PreselectedFacility(facilityRef, tenantFacilityId);
        }
    }
}
