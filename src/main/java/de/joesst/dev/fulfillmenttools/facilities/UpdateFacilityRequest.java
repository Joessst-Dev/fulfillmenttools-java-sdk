package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;

/**
 * Request payload for updating an existing facility with partial changes.
 *
 * <p>Use {@link #builder()} to construct instances fluently.
 * All fields are optional; only specified fields will be updated.
 *
 * <p>Example:
 * <pre>{@code
 * var request = UpdateFacilityRequest.builder()
 *     .status("SUSPENDED")
 *     .contact(updatedContact)
 *     .build();
 * }</pre>
 */
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

    /** Returns the facility name. @return the name */
    public String name() { return name; }

    /** Returns the tenant facility ID. @return the tenant facility ID */
    public String tenantFacilityId() { return tenantFacilityId; }

    /** Returns the facility status. @return the status */
    public String status() { return status; }

    /** Returns the facility type. @return the type */
    public String type() { return type; }

    /** Returns the location type. @return the location type */
    public String locationType() { return locationType; }

    /** Returns the facility address. @return the address */
    public FacilityAddress address() { return address; }

    /** Returns the facility contact information. @return the contact */
    public FacilityContact contact() { return contact; }

    /** Returns the picking methods. @return the picking methods */
    public List<String> pickingMethods() { return pickingMethods; }

    /** Returns the picking times. @return the picking times */
    public PickingTimes pickingTimes() { return pickingTimes; }

    /** Returns the closing days. @return the closing days */
    public List<ClosingDay> closingDays() { return closingDays; }

    /** Returns the scanning rule configuration. @return the scanning rule */
    public ScanningRuleConfiguration scanningRule() { return scanningRule; }

    /** Returns whether capacity is enabled. @return whether capacity is enabled */
    public Boolean capacityEnabled() { return capacityEnabled; }

    /** Returns the capacity planning timeframe. @return the capacity planning timeframe */
    public Integer capacityPlanningTimeframe() { return capacityPlanningTimeframe; }

    /** Returns the fulfillment process buffer. @return the fulfillment process buffer */
    public Integer fulfillmentProcessBuffer() { return fulfillmentProcessBuffer; }

    /** Returns the operative costs. @return the operative costs */
    public List<FacilityOperativeCost> operativeCosts() { return operativeCosts; }

    /** Returns the tags. @return the tags */
    public List<TagReference> tags() { return tags; }

    /** Returns the custom attributes. @return the custom attributes */
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    /** Builder for UpdateFacilityRequest. */
    public static final class Builder {

        /** The facility name. */
        private String name;

        /** The tenant facility ID. */
        private String tenantFacilityId;

        /** The facility status. */
        private String status;

        /** The facility type. */
        private String type;

        /** The location type. */
        private String locationType;

        /** The facility address. */
        private FacilityAddress address;

        /** The facility contact information. */
        private FacilityContact contact;

        /** The picking methods. */
        private List<String> pickingMethods;

        /** The picking times. */
        private PickingTimes pickingTimes;

        /** The closing days. */
        private List<ClosingDay> closingDays;

        /** The scanning rule configuration. */
        private ScanningRuleConfiguration scanningRule;

        /** Whether capacity is enabled. */
        private Boolean capacityEnabled;

        /** The capacity planning timeframe. */
        private Integer capacityPlanningTimeframe;

        /** The fulfillment process buffer. */
        private Integer fulfillmentProcessBuffer;

        /** The operative costs. */
        private List<FacilityOperativeCost> operativeCosts;

        /** The tags. */
        private List<TagReference> tags;

        /** The custom attributes. */
        private Map<String, Object> customAttributes;

        /** Creates a new Builder. */
        public Builder() {}

        /** Sets the facility name. @param name the name. @return this builder */
        public Builder name(String name) { this.name = name; return this; }

        /** Sets the tenant facility ID. @param tenantFacilityId the tenant facility ID. @return this builder */
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /** Sets the facility status. @param status the status. @return this builder */
        public Builder status(String status) { this.status = status; return this; }

        /** Sets the facility type. @param type the type. @return this builder */
        public Builder type(String type) { this.type = type; return this; }

        /** Sets the location type. @param locationType the location type. @return this builder */
        public Builder locationType(String locationType) { this.locationType = locationType; return this; }

        /** Sets the facility address. @param address the address. @return this builder */
        public Builder address(FacilityAddress address) { this.address = address; return this; }

        /** Sets the facility contact information. @param contact the contact. @return this builder */
        public Builder contact(FacilityContact contact) { this.contact = contact; return this; }

        /** Sets the picking methods. @param pickingMethods the picking methods. @return this builder */
        public Builder pickingMethods(List<String> pickingMethods) { this.pickingMethods = pickingMethods; return this; }

        /** Sets the picking times. @param pickingTimes the picking times. @return this builder */
        public Builder pickingTimes(PickingTimes pickingTimes) { this.pickingTimes = pickingTimes; return this; }

        /** Sets the closing days. @param closingDays the closing days. @return this builder */
        public Builder closingDays(List<ClosingDay> closingDays) { this.closingDays = closingDays; return this; }

        /** Sets the scanning rule configuration. @param scanningRule the scanning rule. @return this builder */
        public Builder scanningRule(ScanningRuleConfiguration scanningRule) { this.scanningRule = scanningRule; return this; }

        /** Sets whether capacity is enabled. @param capacityEnabled whether capacity is enabled. @return this builder */
        public Builder capacityEnabled(Boolean capacityEnabled) { this.capacityEnabled = capacityEnabled; return this; }

        /** Sets the capacity planning timeframe. @param capacityPlanningTimeframe the capacity planning timeframe. @return this builder */
        public Builder capacityPlanningTimeframe(Integer capacityPlanningTimeframe) { this.capacityPlanningTimeframe = capacityPlanningTimeframe; return this; }

        /** Sets the fulfillment process buffer. @param fulfillmentProcessBuffer the fulfillment process buffer. @return this builder */
        public Builder fulfillmentProcessBuffer(Integer fulfillmentProcessBuffer) { this.fulfillmentProcessBuffer = fulfillmentProcessBuffer; return this; }

        /** Sets the operative costs. @param operativeCosts the operative costs. @return this builder */
        public Builder operativeCosts(List<FacilityOperativeCost> operativeCosts) { this.operativeCosts = operativeCosts; return this; }

        /** Sets the tags. @param tags the tags. @return this builder */
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        /** Sets the custom attributes. @param customAttributes the custom attributes. @return this builder */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /** Builds and returns a new UpdateFacilityRequest. @return the built request */
        public UpdateFacilityRequest build() { return new UpdateFacilityRequest(this); }
    }
}
