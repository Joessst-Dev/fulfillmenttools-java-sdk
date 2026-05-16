package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;

/**
 * Represents a facility in the fulfillmenttools platform.
 *
 * @param id unique {@link FacilityId} assigned by the system
 * @param version optimistic-locking version number
 * @param created creation timestamp in UTC
 * @param lastModified last modification timestamp in UTC
 * @param tenantFacilityId optional tenant-provided {@link TenantFacilityId} identifier
 * @param name human-readable name of the facility
 * @param status operational status (e.g. "ONLINE", "SUSPENDED", "OFFLINE")
 * @param type facility type (e.g. "MANAGED_FACILITY", "SUPPLIER")
 * @param locationType location classification (e.g. "STORE", "WAREHOUSE", "EXTERNAL")
 * @param address physical address of the facility
 * @param contact primary contact person for the facility
 * @param services list of fulfillment services offered by the facility
 * @param pickingMethods list of picking strategies supported
 * @param pickingTimes operating hours for picking operations
 * @param closingDays periods when the facility is unavailable
 * @param scanningRule scanning rule configuration for items
 * @param capacityEnabled whether capacity planning is enabled
 * @param capacityPlanningTimeframe planning horizon in days
 * @param fulfillmentProcessBuffer time buffer for fulfillment processing
 * @param operativeCosts operational cost estimates
 * @param configs linked configuration references
 * @param tags business tags for categorization
 * @param customAttributes free-form custom attributes
 */
public record Facility(
        FacilityId id,
        Integer version,
        Instant created,
        Instant lastModified,
        TenantFacilityId tenantFacilityId,
        String name,
        String status,
        String type,
        String locationType,
        FacilityAddress address,
        FacilityContact contact,
        List<FacilityService> services,
        List<String> pickingMethods,
        PickingTimes pickingTimes,
        List<ClosingDay> closingDays,
        ScanningRuleConfiguration scanningRule,
        Boolean capacityEnabled,
        Integer capacityPlanningTimeframe,
        Integer fulfillmentProcessBuffer,
        List<FacilityOperativeCost> operativeCosts,
        List<LinkedConfiguration> configs,
        List<TagReference> tags,
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing a {@link Facility}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Facility}.
     */
    public static final class Builder {

        private FacilityId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private TenantFacilityId tenantFacilityId;
        private String name;
        private String status;
        private String type;
        private String locationType;
        private FacilityAddress address;
        private FacilityContact contact;
        private List<FacilityService> services;
        private List<String> pickingMethods;
        private PickingTimes pickingTimes;
        private List<ClosingDay> closingDays;
        private ScanningRuleConfiguration scanningRule;
        private Boolean capacityEnabled;
        private Integer capacityPlanningTimeframe;
        private Integer fulfillmentProcessBuffer;
        private List<FacilityOperativeCost> operativeCosts;
        private List<LinkedConfiguration> configs;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * @param id unique {@link FacilityId} assigned by the system
         * @return this builder
         */
        public Builder id(FacilityId id) {
            this.id = id;
            return this;
        }

        /**
         * @param version optimistic-locking version number
         * @return this builder
         */
        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        /**
         * @param created creation timestamp in UTC
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * @param lastModified last modification timestamp in UTC
         * @return this builder
         */
        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        /**
         * @param tenantFacilityId optional tenant-provided {@link TenantFacilityId} identifier
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) {
            this.tenantFacilityId = tenantFacilityId;
            return this;
        }

        /**
         * @param name human-readable name of the facility
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @param status operational status (e.g. "ONLINE", "SUSPENDED", "OFFLINE")
         * @return this builder
         */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * @param type facility type (e.g. "MANAGED_FACILITY", "SUPPLIER")
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * @param locationType location classification (e.g. "STORE", "WAREHOUSE", "EXTERNAL")
         * @return this builder
         */
        public Builder locationType(String locationType) {
            this.locationType = locationType;
            return this;
        }

        /**
         * @param address physical address of the facility
         * @return this builder
         */
        public Builder address(FacilityAddress address) {
            this.address = address;
            return this;
        }

        /**
         * @param contact primary contact person for the facility
         * @return this builder
         */
        public Builder contact(FacilityContact contact) {
            this.contact = contact;
            return this;
        }

        /**
         * @param services list of fulfillment services offered by the facility
         * @return this builder
         */
        public Builder services(List<FacilityService> services) {
            this.services = services;
            return this;
        }

        /**
         * @param pickingMethods list of picking strategies supported
         * @return this builder
         */
        public Builder pickingMethods(List<String> pickingMethods) {
            this.pickingMethods = pickingMethods;
            return this;
        }

        /**
         * @param pickingTimes operating hours for picking operations
         * @return this builder
         */
        public Builder pickingTimes(PickingTimes pickingTimes) {
            this.pickingTimes = pickingTimes;
            return this;
        }

        /**
         * @param closingDays periods when the facility is unavailable
         * @return this builder
         */
        public Builder closingDays(List<ClosingDay> closingDays) {
            this.closingDays = closingDays;
            return this;
        }

        /**
         * @param scanningRule scanning rule configuration for items
         * @return this builder
         */
        public Builder scanningRule(ScanningRuleConfiguration scanningRule) {
            this.scanningRule = scanningRule;
            return this;
        }

        /**
         * @param capacityEnabled whether capacity planning is enabled
         * @return this builder
         */
        public Builder capacityEnabled(Boolean capacityEnabled) {
            this.capacityEnabled = capacityEnabled;
            return this;
        }

        /**
         * @param capacityPlanningTimeframe planning horizon in days
         * @return this builder
         */
        public Builder capacityPlanningTimeframe(Integer capacityPlanningTimeframe) {
            this.capacityPlanningTimeframe = capacityPlanningTimeframe;
            return this;
        }

        /**
         * @param fulfillmentProcessBuffer time buffer for fulfillment processing
         * @return this builder
         */
        public Builder fulfillmentProcessBuffer(Integer fulfillmentProcessBuffer) {
            this.fulfillmentProcessBuffer = fulfillmentProcessBuffer;
            return this;
        }

        /**
         * @param operativeCosts operational cost estimates
         * @return this builder
         */
        public Builder operativeCosts(List<FacilityOperativeCost> operativeCosts) {
            this.operativeCosts = operativeCosts;
            return this;
        }

        /**
         * @param configs linked configuration references
         * @return this builder
         */
        public Builder configs(List<LinkedConfiguration> configs) {
            this.configs = configs;
            return this;
        }

        /**
         * @param tags business tags for categorization
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * @param customAttributes free-form custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link Facility}.
         *
         * @return a new instance
         */
        public Facility build() {
            return new Facility(
                    id, version, created, lastModified, tenantFacilityId, name, status, type,
                    locationType, address, contact, services, pickingMethods, pickingTimes,
                    closingDays, scanningRule, capacityEnabled, capacityPlanningTimeframe,
                    fulfillmentProcessBuffer, operativeCosts, configs, tags, customAttributes);
        }
    }
}
