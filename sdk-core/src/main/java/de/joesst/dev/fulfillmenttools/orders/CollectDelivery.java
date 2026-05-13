package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.time.Instant;
import java.util.List;

/**
 * Click-and-collect delivery configuration for an order.
 *
 * <p>Maps to the {@code CollectDelivery} schema in the fulfillmenttools OpenAPI spec.
 * Either {@code facilityRef} or {@code tenantFacilityId} must be specified.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef     The fulfillmenttools {@link FacilityId} of the facility where the
 *                        consumer will collect.
 * @param tenantFacilityId The tenant-specific {@link TenantFacilityId} facility identifier.
 * @param paid            Whether the order is already paid. Defaults to {@code false}.
 * @param provisioningTime The target time by which the order must be provisioned.
 * @param supplyingFacilities Deprecated: use {@code supplyingFacilitiesConfigurations}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CollectDelivery(
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId,
        Boolean paid,
        Instant provisioningTime,
        List<String> supplyingFacilities
) {

    /**
     * Returns a builder for constructing a {@code CollectDelivery}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CollectDelivery}.
     */
    public static final class Builder {

        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private Boolean paid;
        private Instant provisioningTime;
        private List<String> supplyingFacilities;

        private Builder() {}

        /**
         * Sets the fulfillmenttools facility reference.
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
         * Sets whether the order is already paid.
         * @param paid {@code true} if the order is paid
         * @return this builder
         */
        public Builder paid(Boolean paid) {
            this.paid = paid;
            return this;
        }

        /**
         * Sets the target time by which the order must be provisioned.
         * @param provisioningTime the provisioning timestamp
         * @return this builder
         */
        public Builder provisioningTime(Instant provisioningTime) {
            this.provisioningTime = provisioningTime;
            return this;
        }

        /**
         * Sets the deprecated list of supplying facilities.
         * @param supplyingFacilities the list of supplying facility identifiers
         * @return this builder
         */
        public Builder supplyingFacilities(List<String> supplyingFacilities) {
            this.supplyingFacilities = supplyingFacilities;
            return this;
        }

        /**
         * Builds a {@link CollectDelivery}.
         *
         * @return a new instance.
         */
        public CollectDelivery build() {
            return new CollectDelivery(facilityRef, tenantFacilityId, paid, provisioningTime, supplyingFacilities);
        }
    }
}
