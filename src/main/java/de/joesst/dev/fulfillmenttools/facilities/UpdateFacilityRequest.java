package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;

public final class UpdateFacilityRequest {

    private final String name;
    private final String tenantFacilityId;
    private final String status;
    private final String type;
    private final String locationType;
    private final FacilityAddress address;
    private final FacilityContact contact;
    private final List<String> pickingMethods;
    private final PickingTimes pickingTimes;
    private final List<ClosingDay> closingDays;
    private final ScanningRuleConfiguration scanningRule;
    private final Boolean capacityEnabled;
    private final Integer capacityPlanningTimeframe;
    private final Integer fulfillmentProcessBuffer;
    private final List<FacilityOperativeCost> operativeCosts;
    private final List<TagReference> tags;
    private final Map<String, Object> customAttributes;

    private UpdateFacilityRequest(Builder builder) {
        this.name = builder.name;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.status = builder.status;
        this.type = builder.type;
        this.locationType = builder.locationType;
        this.address = builder.address;
        this.contact = builder.contact;
        this.pickingMethods = builder.pickingMethods;
        this.pickingTimes = builder.pickingTimes;
        this.closingDays = builder.closingDays;
        this.scanningRule = builder.scanningRule;
        this.capacityEnabled = builder.capacityEnabled;
        this.capacityPlanningTimeframe = builder.capacityPlanningTimeframe;
        this.fulfillmentProcessBuffer = builder.fulfillmentProcessBuffer;
        this.operativeCosts = builder.operativeCosts;
        this.tags = builder.tags;
        this.customAttributes = builder.customAttributes;
    }

    public String name() { return name; }
    public String tenantFacilityId() { return tenantFacilityId; }
    public String status() { return status; }
    public String type() { return type; }
    public String locationType() { return locationType; }
    public FacilityAddress address() { return address; }
    public FacilityContact contact() { return contact; }
    public List<String> pickingMethods() { return pickingMethods; }
    public PickingTimes pickingTimes() { return pickingTimes; }
    public List<ClosingDay> closingDays() { return closingDays; }
    public ScanningRuleConfiguration scanningRule() { return scanningRule; }
    public Boolean capacityEnabled() { return capacityEnabled; }
    public Integer capacityPlanningTimeframe() { return capacityPlanningTimeframe; }
    public Integer fulfillmentProcessBuffer() { return fulfillmentProcessBuffer; }
    public List<FacilityOperativeCost> operativeCosts() { return operativeCosts; }
    public List<TagReference> tags() { return tags; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String tenantFacilityId;
        private String status;
        private String type;
        private String locationType;
        private FacilityAddress address;
        private FacilityContact contact;
        private List<String> pickingMethods;
        private PickingTimes pickingTimes;
        private List<ClosingDay> closingDays;
        private ScanningRuleConfiguration scanningRule;
        private Boolean capacityEnabled;
        private Integer capacityPlanningTimeframe;
        private Integer fulfillmentProcessBuffer;
        private List<FacilityOperativeCost> operativeCosts;
        private List<TagReference> tags;
        private Map<String, Object> customAttributes;

        public Builder name(String name) { this.name = name; return this; }
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder locationType(String locationType) { this.locationType = locationType; return this; }
        public Builder address(FacilityAddress address) { this.address = address; return this; }
        public Builder contact(FacilityContact contact) { this.contact = contact; return this; }
        public Builder pickingMethods(List<String> pickingMethods) { this.pickingMethods = pickingMethods; return this; }
        public Builder pickingTimes(PickingTimes pickingTimes) { this.pickingTimes = pickingTimes; return this; }
        public Builder closingDays(List<ClosingDay> closingDays) { this.closingDays = closingDays; return this; }
        public Builder scanningRule(ScanningRuleConfiguration scanningRule) { this.scanningRule = scanningRule; return this; }
        public Builder capacityEnabled(Boolean capacityEnabled) { this.capacityEnabled = capacityEnabled; return this; }
        public Builder capacityPlanningTimeframe(Integer capacityPlanningTimeframe) { this.capacityPlanningTimeframe = capacityPlanningTimeframe; return this; }
        public Builder fulfillmentProcessBuffer(Integer fulfillmentProcessBuffer) { this.fulfillmentProcessBuffer = fulfillmentProcessBuffer; return this; }
        public Builder operativeCosts(List<FacilityOperativeCost> operativeCosts) { this.operativeCosts = operativeCosts; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdateFacilityRequest build() { return new UpdateFacilityRequest(this); }
    }
}
