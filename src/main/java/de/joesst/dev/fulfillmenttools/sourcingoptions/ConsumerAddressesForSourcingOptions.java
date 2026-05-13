package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;
import java.util.Map;

/**
 * A collection of consumer addresses for use in sourcing options requests.
 *
 * <p>Groups delivery and billing addresses for a consumer along with facility and consumer references.
 */
public final class ConsumerAddressesForSourcingOptions {

    private final List<ConsumerAddressForSourcingOptions> addresses;
    private final String consumerId;
    private final FacilityId facilityRef;
    private final TenantFacilityId tenantFacilityId;
    private final Map<String, Object> customAttributes;

    private ConsumerAddressesForSourcingOptions(Builder builder) {
        this.addresses = builder.addresses;
        this.consumerId = builder.consumerId;
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the list of consumer addresses.
     * @return the addresses, or {@code null} if not set
     */
    public List<ConsumerAddressForSourcingOptions> addresses() { return addresses; }

    /**
     * Returns the consumer identifier.
     * @return the consumer ID, or {@code null} if not set
     */
    public String consumerId() { return consumerId; }

    /**
     * Returns the facility reference.
     * @return the facility ref, or {@code null} if not set
     */
    public FacilityId facilityRef() { return facilityRef; }

    /**
     * Returns the tenant-scoped facility identifier.
     * @return the tenant facility ID, or {@code null} if not set
     */
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes map, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing instances.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link ConsumerAddressesForSourcingOptions}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private List<ConsumerAddressForSourcingOptions> addresses;
        private String consumerId;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private Map<String, Object> customAttributes;

        /**
         * Sets the list of consumer addresses.
         * @param addresses the addresses
         * @return this builder
         */
        public Builder addresses(List<ConsumerAddressForSourcingOptions> addresses) { this.addresses = addresses; return this; }

        /**
         * Sets the consumer identifier.
         * @param consumerId the consumer ID
         * @return this builder
         */
        public Builder consumerId(String consumerId) { this.consumerId = consumerId; return this; }

        /**
         * Sets the facility reference.
         * @param facilityRef the facility ref
         * @return this builder
         */
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the tenant-scoped facility identifier.
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new instance.
         * @return a new {@link ConsumerAddressesForSourcingOptions}
         */
        public ConsumerAddressesForSourcingOptions build() { return new ConsumerAddressesForSourcingOptions(this); }
    }
}
