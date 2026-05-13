package de.joesst.dev.fulfillmenttools.facilitygroups;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request to create a new facility group.
 */
public final class CreateFacilityGroupRequest {

    private final String tenantFacilityGroupId;
    private final List<String> facilityRefs;
    private final Map<String, String> nameLocalized;
    private final Map<String, Object> customAttributes;

    private CreateFacilityGroupRequest(Builder builder) {
        this.tenantFacilityGroupId = Objects.requireNonNull(builder.tenantFacilityGroupId, "tenantFacilityGroupId must not be null");
        this.facilityRefs = Objects.requireNonNull(builder.facilityRefs, "facilityRefs must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the tenant-scoped identifier for this facility group.
     *
     * @return the tenant facility group ID
     */
    public String tenantFacilityGroupId() { return tenantFacilityGroupId; }

    /**
     * Returns the facility references in this group.
     *
     * @return the list of facility references
     */
    public List<String> facilityRefs() { return facilityRefs; }

    /**
     * Returns the localized names of the group.
     *
     * @return the localized names by language code
     */
    public Map<String, String> nameLocalized() { return nameLocalized; }

    /**
     * Returns custom attributes for this facility group.
     *
     * @return the custom attributes map, or null if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing CreateFacilityGroupRequest instances.
     *
     * @return a new Builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing CreateFacilityGroupRequest.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String tenantFacilityGroupId;
        private List<String> facilityRefs;
        private Map<String, String> nameLocalized;
        private Map<String, Object> customAttributes;

        /**
         * Sets the tenant-scoped identifier for this facility group.
         *
         * @param tenantFacilityGroupId the tenant facility group ID
         * @return this builder instance
         */
        public Builder tenantFacilityGroupId(String tenantFacilityGroupId) { this.tenantFacilityGroupId = tenantFacilityGroupId; return this; }

        /**
         * Sets the facility references in this group.
         *
         * @param facilityRefs the list of facility references
         * @return this builder instance
         */
        public Builder facilityRefs(List<String> facilityRefs) { this.facilityRefs = facilityRefs; return this; }

        /**
         * Sets the localized names of the group.
         *
         * @param nameLocalized the localized names by language code
         * @return this builder instance
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) { this.nameLocalized = nameLocalized; return this; }

        /**
         * Sets custom attributes for this facility group.
         *
         * @param customAttributes the custom attributes map
         * @return this builder instance
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds a new CreateFacilityGroupRequest with the configured values.
         *
         * @return a new CreateFacilityGroupRequest instance
         */
        public CreateFacilityGroupRequest build() { return new CreateFacilityGroupRequest(this); }
    }
}
