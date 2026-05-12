package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request object for creating a new facility connection.
 *
 * <p>The {@code target} field is the only required parameter. All other fields are optional
 * and will be omitted from the request body when {@code null}.
 *
 * <p>Example usage:
 * <pre>{@code
 * var request = CreateFacilityConnectionRequest.builder()
 *     .target(ConnectionTarget.ManagedFacility.of("fac-456"))
 *     .carrierKey("DHL")
 *     .build();
 * }</pre>
 */
public final class CreateFacilityConnectionRequest {

    private final ConnectionTarget target;
    private final String carrierKey;
    private final String carrierName;
    private final List<ConnectionContext> context;
    private final List<DeliveryCost> fallbackCosts;
    private final List<NonDeliveryDaysPerCountry> nonDeliveryDays;
    private final List<PackagingUnitsByContext> packagingUnitsByContexts;
    private final CutoffTimes cutoffTimes;
    private final CarrierTransitTime fallbackTransitTime;
    private final Map<String, Object> customAttributes;

    private CreateFacilityConnectionRequest(Builder builder) {
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

    /** Returns the typed connection target; never {@code null}. */
    public ConnectionTarget target() { return target; }

    /** Returns the optional carrier key. */
    public String carrierKey() { return carrierKey; }

    /** Returns the optional human-readable carrier name. */
    public String carrierName() { return carrierName; }

    /** Returns the optional scoping contexts. */
    public List<ConnectionContext> context() { return context; }

    /** Returns the optional fallback delivery costs. */
    public List<DeliveryCost> fallbackCosts() { return fallbackCosts; }

    /** Returns the optional non-delivery day configuration. */
    public List<NonDeliveryDaysPerCountry> nonDeliveryDays() { return nonDeliveryDays; }

    /** Returns the optional context-dependent packaging unit mappings. */
    public List<PackagingUnitsByContext> packagingUnitsByContexts() { return packagingUnitsByContexts; }

    /** Returns the optional cutoff schedule. */
    public CutoffTimes cutoffTimes() { return cutoffTimes; }

    /** Returns the optional fallback transit time. */
    public CarrierTransitTime fallbackTransitTime() { return fallbackTransitTime; }

    /** Returns the optional free-form custom attributes. */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /** Returns a new builder for constructing a {@code CreateFacilityConnectionRequest}. */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateFacilityConnectionRequest}.
     */
    public static final class Builder {
        private ConnectionTarget target;
        private String carrierKey;
        private String carrierName;
        private List<ConnectionContext> context;
        private List<DeliveryCost> fallbackCosts;
        private List<NonDeliveryDaysPerCountry> nonDeliveryDays;
        private List<PackagingUnitsByContext> packagingUnitsByContexts;
        private CutoffTimes cutoffTimes;
        private CarrierTransitTime fallbackTransitTime;
        private Map<String, Object> customAttributes;

        /** Sets the target of the connection; required. */
        public Builder target(ConnectionTarget target) { this.target = target; return this; }

        /** Sets the carrier key. */
        public Builder carrierKey(String carrierKey) { this.carrierKey = carrierKey; return this; }

        /** Sets the human-readable carrier name. */
        public Builder carrierName(String carrierName) { this.carrierName = carrierName; return this; }

        /** Sets the scoping contexts. */
        public Builder context(List<ConnectionContext> context) { this.context = context; return this; }

        /** Sets the fallback delivery costs. */
        public Builder fallbackCosts(List<DeliveryCost> fallbackCosts) { this.fallbackCosts = fallbackCosts; return this; }

        /** Sets the non-delivery day configuration. */
        public Builder nonDeliveryDays(List<NonDeliveryDaysPerCountry> nonDeliveryDays) { this.nonDeliveryDays = nonDeliveryDays; return this; }

        /** Sets the context-dependent packaging unit mappings. */
        public Builder packagingUnitsByContexts(List<PackagingUnitsByContext> packagingUnitsByContexts) { this.packagingUnitsByContexts = packagingUnitsByContexts; return this; }

        /** Sets the cutoff schedule. */
        public Builder cutoffTimes(CutoffTimes cutoffTimes) { this.cutoffTimes = cutoffTimes; return this; }

        /** Sets the fallback transit time. */
        public Builder fallbackTransitTime(CarrierTransitTime fallbackTransitTime) { this.fallbackTransitTime = fallbackTransitTime; return this; }

        /** Sets the free-form custom attributes. */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new {@code CreateFacilityConnectionRequest}.
         *
         * @throws NullPointerException if {@code target} has not been set
         */
        public CreateFacilityConnectionRequest build() { return new CreateFacilityConnectionRequest(this); }
    }
}
