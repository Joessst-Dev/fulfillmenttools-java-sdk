package de.joesst.dev.fulfillmenttools.facilitygroups;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request to create a new facility group.
 */
public final class CreateFacilityGroupRequest {

    private final String tenantFacilityGroupId;
    private final List<FacilityId> facilityRefs;
    private final Map<String, String> nameLocalized;
    private final CustomAttributes customAttributes;

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
    public List<FacilityId> facilityRefs() { return facilityRefs; }

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
    public CustomAttributes customAttributes() { return customAttributes; }

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

        private Builder() {}

        private String tenantFacilityGroupId;
        private List<FacilityId> facilityRefs;
        private Map<String, String> nameLocalized;
        private CustomAttributes customAttributes;

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
        public Builder facilityRefs(List<FacilityId> facilityRefs) { this.facilityRefs = facilityRefs; return this; }

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
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds a new CreateFacilityGroupRequest with the configured values.
         *
         * @return a new CreateFacilityGroupRequest instance
         */
        public CreateFacilityGroupRequest build() { return new CreateFacilityGroupRequest(this); }
    }
}
