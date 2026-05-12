package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request object for creating a new item return job.
 *
 * <p>Maps to the {@code ItemReturnJobForCreation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Use {@link #builder()} to construct an instance. {@code originFacilityRefs} and
 * {@code status} are required.
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class CreateReturnRequest {

    private final List<String> originFacilityRefs;
    private final String status;
    private final List<ConsumerAddress> consumerAddresses;
    private final List<ReturnJobLineItemForCreation> returnableLineItems;
    private final List<ReturnJobLineItemForCreation> notReturnableLineItems;
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

    /** @return The origin facility references. Required. */
    public List<String> originFacilityRefs() { return originFacilityRefs; }
    /** @return The return job status (e.g. {@code OPEN}). Required. */
    public String status() { return status; }
    /** @return Optional consumer addresses for this return job. */
    public List<ConsumerAddress> consumerAddresses() { return consumerAddresses; }
    /** @return Typed line items that are returnable. */
    public List<ReturnJobLineItemForCreation> returnableLineItems() { return returnableLineItems; }
    /** @return Typed line items that are not returnable. */
    public List<ReturnJobLineItemForCreation> notReturnableLineItems() { return notReturnableLineItems; }
    /** @return Optional scannable codes for this return job. */
    public List<String> scannableCodes() { return scannableCodes; }
    /** @return Optional short identifier for customer assignment. */
    public String shortId() { return shortId; }
    /** @return Optional tenant order identifier. */
    public String tenantOrderId() { return tenantOrderId; }
    /** @return Free-form custom attributes. */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /** @return A new {@link Builder} instance. */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateReturnRequest}.
     */
    public static final class Builder {

        private List<String> originFacilityRefs;
        private String status;
        private List<ConsumerAddress> consumerAddresses;
        private List<ReturnJobLineItemForCreation> returnableLineItems;
        private List<ReturnJobLineItemForCreation> notReturnableLineItems;
        private List<String> scannableCodes;
        private String shortId;
        private String tenantOrderId;
        private Map<String, Object> customAttributes;

        /** @param originFacilityRefs The origin facility references. Required. */
        public Builder originFacilityRefs(List<String> originFacilityRefs) { this.originFacilityRefs = originFacilityRefs; return this; }
        /** @param status The return job status. Required. */
        public Builder status(String status) { this.status = status; return this; }
        /** @param consumerAddresses Consumer addresses for this return job. */
        public Builder consumerAddresses(List<ConsumerAddress> consumerAddresses) { this.consumerAddresses = consumerAddresses; return this; }
        /** @param returnableLineItems Typed line items that can be returned. */
        public Builder returnableLineItems(List<ReturnJobLineItemForCreation> returnableLineItems) { this.returnableLineItems = returnableLineItems; return this; }
        /** @param notReturnableLineItems Typed line items that cannot be returned. */
        public Builder notReturnableLineItems(List<ReturnJobLineItemForCreation> notReturnableLineItems) { this.notReturnableLineItems = notReturnableLineItems; return this; }
        /** @param scannableCodes Scannable codes for this return job. */
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        /** @param shortId Short identifier for customer assignment. */
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        /** @param tenantOrderId Tenant order identifier. */
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        /** @param customAttributes Free-form custom attributes. */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /** @return A new {@link CreateReturnRequest} with the configured values. */
        public CreateReturnRequest build() { return new CreateReturnRequest(this); }
    }
}
