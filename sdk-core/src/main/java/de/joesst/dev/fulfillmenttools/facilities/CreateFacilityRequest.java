package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Request payload for creating a new facility.
 *
 * <p>Use {@link #builder()} to construct instances fluently.
 * The {@code name} field is required; all other fields are optional.
 *
 * <p>Example:
 * <pre>{@code
 * var request = CreateFacilityRequest.builder()
 *     .name("Berlin Warehouse")
 *     .type("MANAGED_FACILITY")
 *     .status("ONLINE")
 *     .address(facilityAddress)
 *     .build();
 * }</pre>
 */
public final class CreateFacilityRequest {

    private final String name;
    private final TenantFacilityId tenantFacilityId;
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
    private final CustomAttributes customAttributes;

    private CreateFacilityRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
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
     * @return the name; never {@code null}
     */
    public String name() { return name; }

    /**
     * Returns the tenant-scoped facility identifier.
     * @return the tenant facility ID, or {@code null} if not set
     */
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }

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
     * Returns the facility contact.
     * @return the contact, or {@code null} if not set
     */
    public FacilityContact contact() { return contact; }

    /**
     * Returns the picking methods.
     * @return the picking methods, or {@code null} if not set
     */
    public List<String> pickingMethods() { return pickingMethods; }

    /**
     * Returns the picking times configuration.
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
     * @return capacity enabled flag, or {@code null} if not set
     */
    public Boolean capacityEnabled() { return capacityEnabled; }

    /**
     * Returns the capacity planning timeframe in days.
     * @return the capacity planning timeframe, or {@code null} if not set
     */
    public Integer capacityPlanningTimeframe() { return capacityPlanningTimeframe; }

    /**
     * Returns the fulfillment process buffer in minutes.
     * @return the process buffer, or {@code null} if not set
     */
    public Integer fulfillmentProcessBuffer() { return fulfillmentProcessBuffer; }

    /**
     * Returns the operative costs.
     * @return the operative costs, or {@code null} if not set
     */
    public List<FacilityOperativeCost> operativeCosts() { return operativeCosts; }

    /**
     * Returns the tags.
     * @return the tags, or {@code null} if not set
     */
    public List<TagReference> tags() { return tags; }

    /**
     * Returns the custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing a {@link CreateFacilityRequest}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing {@link CreateFacilityRequest} instances.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String name;
        private TenantFacilityId tenantFacilityId;
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
        private CustomAttributes customAttributes;

        /**
         * Sets the facility name (required).
         * @param name the facility name
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Sets the tenant-scoped facility identifier.
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /**
         * Sets the facility status.
         * @param status the facility status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets the facility type.
         * @param type the facility type
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
         * @param address the facility address
         * @return this builder
         */
        public Builder address(FacilityAddress address) { this.address = address; return this; }

        /**
         * Sets the facility contact.
         * @param contact the facility contact
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
         * Sets the picking times configuration.
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
         * @param capacityEnabled the capacity enabled flag
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
         * @param fulfillmentProcessBuffer the process buffer
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
         * Sets the tags.
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        /**
         * Sets the custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new {@link CreateFacilityRequest}.
         * @return a new request instance
         * @throws NullPointerException if name is not set
         */
        public CreateFacilityRequest build() { return new CreateFacilityRequest(this); }
    }
}
