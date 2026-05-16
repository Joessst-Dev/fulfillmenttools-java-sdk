package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Request object for updating an existing facility connection.
 *
 * <p>{@code version} and {@code target} are the only required parameters. All other
 * fields are optional and will be omitted from the request body when {@code null}.
 *
 * <p>Example usage:
 * <pre>{@code
 * var request = UpdateFacilityConnectionRequest.builder()
 *     .version(3)
 *     .target(ConnectionTarget.Customer.of())
 *     .carrierKey("FedEx")
 *     .build();
 * }</pre>
 */
public final class UpdateFacilityConnectionRequest {

    private final Integer version;
    private final ConnectionTarget target;
    private final String carrierKey;
    private final String carrierName;
    private final List<ConnectionContext> context;
    private final List<DeliveryCost> fallbackCosts;
    private final List<NonDeliveryDaysPerCountry> nonDeliveryDays;
    private final List<PackagingUnitsByContext> packagingUnitsByContexts;
    private final CutoffTimes cutoffTimes;
    private final CarrierTransitTime fallbackTransitTime;
    private final CustomAttributes customAttributes;

    private UpdateFacilityConnectionRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.target = Objects.requireNonNull(builder.target, "target must not be null");
        this.carrierKey = builder.carrierKey;
        this.carrierName = builder.carrierName;
        this.context = builder.context;
        this.fallbackCosts = builder.fallbackCosts;
        this.nonDeliveryDays = builder.nonDeliveryDays;
        this.packagingUnitsByContexts = builder.packagingUnitsByContexts;
        this.cutoffTimes = builder.cutoffTimes;
        this.fallbackTransitTime = builder.fallbackTransitTime;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the optimistic-locking version; never {@code null}.
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns the typed connection target; never {@code null}.
     * @return the target
     */
    public ConnectionTarget target() { return target; }

    /**
     * Returns the optional carrier key.
     * @return the carrier key, or {@code null} if not set
     */
    public String carrierKey() { return carrierKey; }

    /**
     * Returns the optional human-readable carrier name.
     * @return the carrier name, or {@code null} if not set
     */
    public String carrierName() { return carrierName; }

    /**
     * Returns the optional scoping contexts.
     * @return the contexts, or {@code null} if not set
     */
    public List<ConnectionContext> context() { return context; }

    /**
     * Returns the optional fallback delivery costs.
     * @return the delivery costs, or {@code null} if not set
     */
    public List<DeliveryCost> fallbackCosts() { return fallbackCosts; }

    /**
     * Returns the optional non-delivery day configuration.
     * @return the non-delivery days, or {@code null} if not set
     */
    public List<NonDeliveryDaysPerCountry> nonDeliveryDays() { return nonDeliveryDays; }

    /**
     * Returns the optional context-dependent packaging unit mappings.
     * @return the packaging units by context, or {@code null} if not set
     */
    public List<PackagingUnitsByContext> packagingUnitsByContexts() { return packagingUnitsByContexts; }

    /**
     * Returns the optional cutoff schedule.
     * @return the cutoff times, or {@code null} if not set
     */
    public CutoffTimes cutoffTimes() { return cutoffTimes; }

    /**
     * Returns the optional fallback transit time.
     * @return the transit time, or {@code null} if not set
     */
    public CarrierTransitTime fallbackTransitTime() { return fallbackTransitTime; }

    /**
     * Returns the optional free-form custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns a new builder for constructing an {@code UpdateFacilityConnectionRequest}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code UpdateFacilityConnectionRequest}. */
    public static final class Builder {

        private Integer version;
        private ConnectionTarget target;
        private String carrierKey;
        private String carrierName;
        private List<ConnectionContext> context;
        private List<DeliveryCost> fallbackCosts;
        private List<NonDeliveryDaysPerCountry> nonDeliveryDays;
        private List<PackagingUnitsByContext> packagingUnitsByContexts;
        private CutoffTimes cutoffTimes;
        private CarrierTransitTime fallbackTransitTime;
        private CustomAttributes customAttributes;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the optimistic-locking version (required).
         * @param version the version
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the target of the connection (required).
         * @param target the target
         * @return this builder
         */
        public Builder target(ConnectionTarget target) { this.target = target; return this; }

        /**
         * Sets the carrier key.
         * @param carrierKey the carrier key
         * @return this builder
         */
        public Builder carrierKey(String carrierKey) { this.carrierKey = carrierKey; return this; }

        /**
         * Sets the human-readable carrier name.
         * @param carrierName the carrier name
         * @return this builder
         */
        public Builder carrierName(String carrierName) { this.carrierName = carrierName; return this; }

        /**
         * Sets the scoping contexts.
         * @param context the contexts
         * @return this builder
         */
        public Builder context(List<ConnectionContext> context) { this.context = context; return this; }

        /**
         * Sets the fallback delivery costs.
         * @param fallbackCosts the delivery costs
         * @return this builder
         */
        public Builder fallbackCosts(List<DeliveryCost> fallbackCosts) { this.fallbackCosts = fallbackCosts; return this; }

        /**
         * Sets the non-delivery day configuration.
         * @param nonDeliveryDays the non-delivery days
         * @return this builder
         */
        public Builder nonDeliveryDays(List<NonDeliveryDaysPerCountry> nonDeliveryDays) { this.nonDeliveryDays = nonDeliveryDays; return this; }

        /**
         * Sets the context-dependent packaging unit mappings.
         * @param packagingUnitsByContexts the packaging units by context
         * @return this builder
         */
        public Builder packagingUnitsByContexts(List<PackagingUnitsByContext> packagingUnitsByContexts) { this.packagingUnitsByContexts = packagingUnitsByContexts; return this; }

        /**
         * Sets the cutoff schedule.
         * @param cutoffTimes the cutoff times
         * @return this builder
         */
        public Builder cutoffTimes(CutoffTimes cutoffTimes) { this.cutoffTimes = cutoffTimes; return this; }

        /**
         * Sets the fallback transit time.
         * @param fallbackTransitTime the transit time
         * @return this builder
         */
        public Builder fallbackTransitTime(CarrierTransitTime fallbackTransitTime) { this.fallbackTransitTime = fallbackTransitTime; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new {@code UpdateFacilityConnectionRequest}.
         * @return the built request
         * @throws NullPointerException if version or target has not been set
         */
        public UpdateFacilityConnectionRequest build() { return new UpdateFacilityConnectionRequest(this); }
    }
}
