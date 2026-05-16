package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

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

    private final List<FacilityId> originFacilityRefs;
    private final String status;
    private final List<ConsumerAddress> consumerAddresses;
    private final List<ReturnJobLineItemForCreation> returnableLineItems;
    private final List<ReturnJobLineItemForCreation> notReturnableLineItems;
    private final List<String> scannableCodes;
    private final String shortId;
    private final TenantOrderId tenantOrderId;
    private final CustomAttributes customAttributes;

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

    /**
     * Returns the origin facility IDs.
     * @return the origin facility IDs; never {@code null}
     */
    public List<FacilityId> originFacilityRefs() { return originFacilityRefs; }

    /**
     * Returns the return job status.
     * @return the status (e.g. {@code OPEN}); never {@code null}
     */
    public String status() { return status; }

    /**
     * Returns the consumer addresses for this return job.
     * @return the consumer addresses, or {@code null} if not set
     */
    public List<ConsumerAddress> consumerAddresses() { return consumerAddresses; }

    /**
     * Returns the returnable line items.
     * @return the returnable line items, or {@code null} if not set
     */
    public List<ReturnJobLineItemForCreation> returnableLineItems() { return returnableLineItems; }

    /**
     * Returns the non-returnable line items.
     * @return the non-returnable line items, or {@code null} if not set
     */
    public List<ReturnJobLineItemForCreation> notReturnableLineItems() { return notReturnableLineItems; }

    /**
     * Returns the scannable codes for this return job.
     * @return the scannable codes, or {@code null} if not set
     */
    public List<String> scannableCodes() { return scannableCodes; }

    /**
     * Returns the short identifier for customer assignment.
     * @return the short ID, or {@code null} if not set
     */
    public String shortId() { return shortId; }

    /**
     * Returns the tenant order identifier.
     * @return the tenant order ID, or {@code null} if not set
     */
    public TenantOrderId tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing a {@link CreateReturnRequest}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateReturnRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private List<FacilityId> originFacilityRefs;
        private String status;
        private List<ConsumerAddress> consumerAddresses;
        private List<ReturnJobLineItemForCreation> returnableLineItems;
        private List<ReturnJobLineItemForCreation> notReturnableLineItems;
        private List<String> scannableCodes;
        private String shortId;
        private TenantOrderId tenantOrderId;
        private CustomAttributes customAttributes;

        /**
         * Sets the origin facility IDs (required).
         * @param originFacilityRefs the origin facility IDs
         * @return this builder
         */
        public Builder originFacilityRefs(List<FacilityId> originFacilityRefs) { this.originFacilityRefs = originFacilityRefs; return this; }

        /**
         * Sets the return job status (required).
         * @param status the return job status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets the consumer addresses for this return job.
         * @param consumerAddresses the consumer addresses
         * @return this builder
         */
        public Builder consumerAddresses(List<ConsumerAddress> consumerAddresses) { this.consumerAddresses = consumerAddresses; return this; }

        /**
         * Sets the returnable line items.
         * @param returnableLineItems typed line items that can be returned
         * @return this builder
         */
        public Builder returnableLineItems(List<ReturnJobLineItemForCreation> returnableLineItems) { this.returnableLineItems = returnableLineItems; return this; }

        /**
         * Sets the non-returnable line items.
         * @param notReturnableLineItems typed line items that cannot be returned
         * @return this builder
         */
        public Builder notReturnableLineItems(List<ReturnJobLineItemForCreation> notReturnableLineItems) { this.notReturnableLineItems = notReturnableLineItems; return this; }

        /**
         * Sets the scannable codes for this return job.
         * @param scannableCodes the scannable codes
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        /**
         * Sets the short identifier for customer assignment.
         * @param shortId the short ID
         * @return this builder
         */
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }

        /**
         * Sets the tenant order identifier.
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new {@link CreateReturnRequest}.
         * @return a new request instance
         * @throws NullPointerException if any required field is not set
         */
        public CreateReturnRequest build() { return new CreateReturnRequest(this); }
    }
}
