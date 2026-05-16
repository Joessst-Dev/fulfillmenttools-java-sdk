package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

/**
 * Represents consumer information for order creation.
 */
public final class OrderForCreationConsumer {

    private final List<ConsumerAddress> addresses;
    private final String consumerId;
    private final String email;
    private final FacilityId facilityRef;
    private final TenantFacilityId tenantFacilityId;
    private final CustomAttributes customAttributes;

    private OrderForCreationConsumer(Builder builder) {
        this.addresses = builder.addresses;
        this.consumerId = builder.consumerId;
        this.email = builder.email;
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the consumer's addresses.
     *
     * @return the addresses list, or null if not set
     */
    public List<ConsumerAddress> addresses() { return addresses; }

    /**
     * Returns the consumer ID.
     *
     * @return the consumer ID, or null if not set
     */
    public String consumerId() { return consumerId; }

    /**
     * Returns the consumer's email address.
     *
     * @return the email, or null if not set
     */
    public String email() { return email; }

    /**
     * Returns the facility reference.
     *
     * @return the facility reference, or null if not set
     */
    public FacilityId facilityRef() { return facilityRef; }

    /**
     * Returns the tenant facility ID.
     *
     * @return the tenant facility ID, or null if not set
     */
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }

    /**
     * Returns custom attributes for this consumer.
     *
     * @return the custom attributes map, or null if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing an {@code OrderForCreationConsumer}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderForCreationConsumer}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private List<ConsumerAddress> addresses;
        private String consumerId;
        private String email;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private CustomAttributes customAttributes;

        /**
         * Sets the consumer's addresses.
         *
         * @param addresses the addresses list
         * @return this builder
         */
        public Builder addresses(List<ConsumerAddress> addresses) { this.addresses = addresses; return this; }

        /**
         * Sets the consumer ID.
         *
         * @param consumerId the consumer ID
         * @return this builder
         */
        public Builder consumerId(String consumerId) { this.consumerId = consumerId; return this; }

        /**
         * Sets the consumer's email address.
         *
         * @param email the email
         * @return this builder
         */
        public Builder email(String email) { this.email = email; return this; }

        /**
         * Sets the facility reference.
         *
         * @param facilityRef the facility reference
         * @return this builder
         */
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the tenant facility ID.
         *
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /**
         * Sets custom attributes for this consumer.
         *
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link OrderForCreationConsumer}.
         *
         * @return a new consumer
         */
        public OrderForCreationConsumer build() { return new OrderForCreationConsumer(this); }
    }
}
