package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;
import java.util.Map;

public final class ConsumerAddressesForSourcingOptions {

    private final List<ConsumerAddressForSourcingOptions> addresses;
    private final String consumerId;
    private final String facilityRef;
    private final String tenantFacilityId;
    private final Map<String, Object> customAttributes;

    private ConsumerAddressesForSourcingOptions(Builder builder) {
        this.addresses = builder.addresses;
        this.consumerId = builder.consumerId;
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.customAttributes = builder.customAttributes;
    }

    public List<ConsumerAddressForSourcingOptions> addresses() { return addresses; }
    public String consumerId() { return consumerId; }
    public String facilityRef() { return facilityRef; }
    public String tenantFacilityId() { return tenantFacilityId; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private List<ConsumerAddressForSourcingOptions> addresses;
        private String consumerId;
        private String facilityRef;
        private String tenantFacilityId;
        private Map<String, Object> customAttributes;

        public Builder addresses(List<ConsumerAddressForSourcingOptions> addresses) { this.addresses = addresses; return this; }
        public Builder consumerId(String consumerId) { this.consumerId = consumerId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public ConsumerAddressesForSourcingOptions build() { return new ConsumerAddressesForSourcingOptions(this); }
    }
}
