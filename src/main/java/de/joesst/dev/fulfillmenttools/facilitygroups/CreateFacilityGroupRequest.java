package de.joesst.dev.fulfillmenttools.facilitygroups;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateFacilityGroupRequest {

    private final String tenantFacilityGroupId;
    private final List<String> facilityRefs;
    private final Map<String, Object> nameLocalized;
    private final Map<String, Object> customAttributes;

    private CreateFacilityGroupRequest(Builder builder) {
        this.tenantFacilityGroupId = Objects.requireNonNull(builder.tenantFacilityGroupId, "tenantFacilityGroupId must not be null");
        this.facilityRefs = Objects.requireNonNull(builder.facilityRefs, "facilityRefs must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.customAttributes = builder.customAttributes;
    }

    public String tenantFacilityGroupId() { return tenantFacilityGroupId; }
    public List<String> facilityRefs() { return facilityRefs; }
    public Map<String, Object> nameLocalized() { return nameLocalized; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String tenantFacilityGroupId;
        private List<String> facilityRefs;
        private Map<String, Object> nameLocalized;
        private Map<String, Object> customAttributes;

        public Builder tenantFacilityGroupId(String tenantFacilityGroupId) { this.tenantFacilityGroupId = tenantFacilityGroupId; return this; }
        public Builder facilityRefs(List<String> facilityRefs) { this.facilityRefs = facilityRefs; return this; }
        public Builder nameLocalized(Map<String, Object> nameLocalized) { this.nameLocalized = nameLocalized; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public CreateFacilityGroupRequest build() { return new CreateFacilityGroupRequest(this); }
    }
}
