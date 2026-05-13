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

    /**
     * Returns the facility name.
     * @return the name, or {@code null} if not set
     */
    public String name() { return name; }

    /**
     * Returns the tenant facility ID.
     * @return the tenant facility ID, or {@code null} if not set
     */
    public String tenantFacilityId() { return tenantFacilityId; }

    /**
     * Returns the facility status.
     * @return the status, or {@code null} if not set
     */
    public String status() { return status; }

    /**
     * Returns the facility type.
     * @return the type, or {@code null} if not set
     */
    public String type() { return type; }

    /**
     * Returns the location type.
     * @return the location type, or {@code null} if not set
     */
    public String locationType() { return locationType; }

    /**
     * Returns the facility address.
     * @return the address, or {@code null} if not set
     */
    public FacilityAddress address() { return address; }

    /**
     * Returns the facility contact information.
     * @return the contact, or {@code null} if not set
     */
    public FacilityContact contact() { return contact; }

    /**
     * Returns the picking methods.
     * @return the picking methods, or {@code null} if not set
     */
    public List<String> pickingMethods() { return pickingMethods; }

    /**
     * Returns the picking times.
     * @return the picking times, or {@code null} if not set
     */
    public PickingTimes pickingTimes() { return pickingTimes; }

    /**
     * Returns the closing days.
     * @return the closing days, or {@code null} if not set
     */
    public List<ClosingDay> closingDays() { return closingDays; }

    /**
     * Returns the scanning rule configuration.
     * @return the scanning rule, or {@code null} if not set
     */
    public ScanningRuleConfiguration scanningRule() { return scanningRule; }

    /**
     * Returns whether capacity management is enabled.
     * @return whether capacity is enabled, or {@code null} if not set
     */
    public Boolean capacityEnabled() { return capacityEnabled; }

    /**
     * Returns the capacity planning timeframe in days.
     * @return the capacity planning timeframe, or {@code null} if not set
     */
    public Integer capacityPlanningTimeframe() { return capacityPlanningTimeframe; }

    /**
     * Returns the fulfillment process buffer in minutes.
     * @return the fulfillment process buffer, or {@code null} if not set
     */
    public Integer fulfillmentProcessBuffer() { return fulfillmentProcessBuffer; }

    /**
     * Returns the operative costs.
     * @return the operative costs, or {@code null} if not set
     */
    public List<FacilityOperativeCost> operativeCosts() { return operativeCosts; }

    /**
     * Returns the tags attached to the facility.
     * @return the tags, or {@code null} if not set
     */
    public List<TagReference> tags() { return tags; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns a new builder for constructing an {@code UpdateFacilityRequest}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code UpdateFacilityRequest}. */
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

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the facility name.
         * @param name the name
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Sets the tenant facility ID.
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /**
         * Sets the facility status.
         * @param status the status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets the facility type.
         * @param type the type
         * @return this builder
         */
        public Builder type(String type) { this.type = type; return this; }

        /**
         * Sets the location type.
         * @param locationType the location type
         * @return this builder
         */
        public Builder locationType(String locationType) { this.locationType = locationType; return this; }

        /**
         * Sets the facility address.
         * @param address the address
         * @return this builder
         */
        public Builder address(FacilityAddress address) { this.address = address; return this; }

        /**
         * Sets the facility contact information.
         * @param contact the contact
         * @return this builder
         */
        public Builder contact(FacilityContact contact) { this.contact = contact; return this; }

        /**
         * Sets the picking methods.
         * @param pickingMethods the picking methods
         * @return this builder
         */
        public Builder pickingMethods(List<String> pickingMethods) { this.pickingMethods = pickingMethods; return this; }

        /**
         * Sets the picking times.
         * @param pickingTimes the picking times
         * @return this builder
         */
        public Builder pickingTimes(PickingTimes pickingTimes) { this.pickingTimes = pickingTimes; return this; }

        /**
         * Sets the closing days.
         * @param closingDays the closing days
         * @return this builder
         */
        public Builder closingDays(List<ClosingDay> closingDays) { this.closingDays = closingDays; return this; }

        /**
         * Sets the scanning rule configuration.
         * @param scanningRule the scanning rule
         * @return this builder
         */
        public Builder scanningRule(ScanningRuleConfiguration scanningRule) { this.scanningRule = scanningRule; return this; }

        /**
         * Sets whether capacity management is enabled.
         * @param capacityEnabled whether capacity is enabled
         * @return this builder
         */
        public Builder capacityEnabled(Boolean capacityEnabled) { this.capacityEnabled = capacityEnabled; return this; }

        /**
         * Sets the capacity planning timeframe in days.
         * @param capacityPlanningTimeframe the capacity planning timeframe
         * @return this builder
         */
        public Builder capacityPlanningTimeframe(Integer capacityPlanningTimeframe) { this.capacityPlanningTimeframe = capacityPlanningTimeframe; return this; }

        /**
         * Sets the fulfillment process buffer in minutes.
         * @param fulfillmentProcessBuffer the fulfillment process buffer
         * @return this builder
         */
        public Builder fulfillmentProcessBuffer(Integer fulfillmentProcessBuffer) { this.fulfillmentProcessBuffer = fulfillmentProcessBuffer; return this; }

        /**
         * Sets the operative costs.
         * @param operativeCosts the operative costs
         * @return this builder
         */
        public Builder operativeCosts(List<FacilityOperativeCost> operativeCosts) { this.operativeCosts = operativeCosts; return this; }

        /**
         * Sets the tags attached to the facility.
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new {@code UpdateFacilityRequest}.
         * @return the built request
         */
        public UpdateFacilityRequest build() { return new UpdateFacilityRequest(this); }
    }
}
