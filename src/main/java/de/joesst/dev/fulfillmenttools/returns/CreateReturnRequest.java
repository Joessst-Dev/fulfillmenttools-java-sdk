package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateReturnRequest {

    private final List<String> originFacilityRefs;
    private final String status;
    private final List<ConsumerAddress> consumerAddresses;
    private final List<Map<String, Object>> returnableLineItems;
    private final List<Map<String, Object>> notReturnableLineItems;
    private final List<String> scannableCodes;
    private final String shortId;
    private final String tenantOrderId;
    private final Map<String, Object> customAttributes;

    private CreateReturnRequest(Builder builder) {
        this.originFacilityRefs = Objects.requireNonNull(builder.originFacilityRefs, "originFacilityRefs must not be null");
        this.status = Objects.requireNonNull(builder.status, "status must not be null");
        this.consumerAddresses = builder.consumerAddresses;
        this.returnableLineItems = builder.returnableLineItems;
        this.notReturnableLineItems = builder.notReturnableLineItems;
        this.scannableCodes = builder.scannableCodes;
        this.shortId = builder.shortId;
        this.tenantOrderId = builder.tenantOrderId;
        this.customAttributes = builder.customAttributes;
    }

    public List<String> originFacilityRefs() { return originFacilityRefs; }
    public String status() { return status; }
    public List<ConsumerAddress> consumerAddresses() { return consumerAddresses; }
    public List<Map<String, Object>> returnableLineItems() { return returnableLineItems; }
    public List<Map<String, Object>> notReturnableLineItems() { return notReturnableLineItems; }
    public List<String> scannableCodes() { return scannableCodes; }
    public String shortId() { return shortId; }
    public String tenantOrderId() { return tenantOrderId; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private List<String> originFacilityRefs;
        private String status;
        private List<ConsumerAddress> consumerAddresses;
        private List<Map<String, Object>> returnableLineItems;
        private List<Map<String, Object>> notReturnableLineItems;
        private List<String> scannableCodes;
        private String shortId;
        private String tenantOrderId;
        private Map<String, Object> customAttributes;

        public Builder originFacilityRefs(List<String> originFacilityRefs) { this.originFacilityRefs = originFacilityRefs; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder consumerAddresses(List<ConsumerAddress> consumerAddresses) { this.consumerAddresses = consumerAddresses; return this; }
        public Builder returnableLineItems(List<Map<String, Object>> returnableLineItems) { this.returnableLineItems = returnableLineItems; return this; }
        public Builder notReturnableLineItems(List<Map<String, Object>> notReturnableLineItems) { this.notReturnableLineItems = notReturnableLineItems; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public CreateReturnRequest build() { return new CreateReturnRequest(this); }
    }
}
