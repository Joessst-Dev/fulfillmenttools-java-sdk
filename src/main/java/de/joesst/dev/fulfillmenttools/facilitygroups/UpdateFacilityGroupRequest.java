package de.joesst.dev.fulfillmenttools.facilitygroups;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class UpdateFacilityGroupRequest {

    private final Integer version;
    private final String tenantFacilityGroupId;
    private final List<String> facilityRefs;
    private final Map<String, String> nameLocalized;
    private final Map<String, Object> customAttributes;

    private UpdateFacilityGroupRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.tenantFacilityGroupId = builder.tenantFacilityGroupId;
        this.facilityRefs = builder.facilityRefs;
        this.nameLocalized = builder.nameLocalized;
        this.customAttributes = builder.customAttributes;
    }

    public Integer version() { return version; }
    public String tenantFacilityGroupId() { return tenantFacilityGroupId; }
    public List<String> facilityRefs() { return facilityRefs; }
    public Map<String, String> nameLocalized() { return nameLocalized; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer version;
        private String tenantFacilityGroupId;
        private List<String> facilityRefs;
        private Map<String, String> nameLocalized;
        private Map<String, Object> customAttributes;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder tenantFacilityGroupId(String tenantFacilityGroupId) { this.tenantFacilityGroupId = tenantFacilityGroupId; return this; }
        public Builder facilityRefs(List<String> facilityRefs) { this.facilityRefs = facilityRefs; return this; }
        public Builder nameLocalized(Map<String, String> nameLocalized) { this.nameLocalized = nameLocalized; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdateFacilityGroupRequest build() { return new UpdateFacilityGroupRequest(this); }
    }
}
