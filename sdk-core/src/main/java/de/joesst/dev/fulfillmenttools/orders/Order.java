package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A fulfillmenttools order as returned by the read API.
 *
 * <p>Maps to the {@code Order} schema in the fulfillmenttools OpenAPI spec, which extends
 * {@code OrderForCreation} and {@code VersionedResource}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               The platform-generated {@link OrderId}.
 * @param version          The optimistic-locking version.
 * @param created          The timestamp when this order was created.
 * @param lastModified     The timestamp of the last modification.
 * @param tenantOrderId    The tenant's external {@link TenantOrderId} order reference number.
 * @param status           The current order status (e.g. {@code OPEN}, {@code LOCKED}).
 * @param processId        The {@link ProcessId} of the global process related to this order.
 * @param schemaVersion    The schema version of this order document.
 * @param orderDate        The date this order was created in the source system.
 * @param orderLineItems   The line items on this order.
 * @param consumer         The consumer this order is for.
 * @param deliveryPreferences How and where this order should be delivered.
 * @param paymentInfo      Payment information for this order.
 * @param cancelationReason The reason this order was cancelled, if applicable.
 * @param customServices   Optional references to custom services on this order.
 * @param updateDetails    Log of updates made to this order.
 * @param tags             Tags attached to this order.
 * @param stickers         Visual stickers attached to this order.
 * @param statusReasons    Reasons explaining the current order status.
 * @param source           The external system that created this order.
 * @param customAttributes Free-form custom attributes.
 * @param anonymized       Whether GDPR-related data has been anonymized.
 */
public record Order(
        OrderId id,
        Integer version,
        Instant created,
        Instant lastModified,
        TenantOrderId tenantOrderId,
        String status,
        ProcessId processId,
        Double schemaVersion,
        Instant orderDate,
        List<OrderLineItem> orderLineItems,
        OrderForCreationConsumer consumer,
        DeliveryPreferences deliveryPreferences,
        OrderPaymentInfo paymentInfo,
        CancelationReason cancelationReason,
        List<Map<String, Object>> customServices,
        List<OrderUpdateDetail> updateDetails,
        List<TagReference> tags,
        List<Sticker> stickers,
        List<OrderStatusReason> statusReasons,
        OrderSource source,
        CustomAttributes customAttributes,
        Boolean anonymized
) {

    /**
     * Returns a builder for constructing an {@code Order}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Order}.
     */
    public static final class Builder {

        private OrderId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private TenantOrderId tenantOrderId;
        private String status;
        private ProcessId processId;
        private Double schemaVersion;
        private Instant orderDate;
        private List<OrderLineItem> orderLineItems;
        private OrderForCreationConsumer consumer;
        private DeliveryPreferences deliveryPreferences;
        private OrderPaymentInfo paymentInfo;
        private CancelationReason cancelationReason;
        private List<Map<String, Object>> customServices;
        private List<OrderUpdateDetail> updateDetails;
        private List<TagReference> tags;
        private List<Sticker> stickers;
        private List<OrderStatusReason> statusReasons;
        private OrderSource source;
        private CustomAttributes customAttributes;
        private Boolean anonymized;

        private Builder() {}

        /**
         * Sets the platform-generated order identifier.
         * @param id the order identifier
         * @return this builder
         */
        public Builder id(OrderId id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the optimistic-locking version.
         * @param version the version number
         * @return this builder
         */
        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        /**
         * Sets the timestamp when this order was created.
         * @param created the creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the timestamp of the last modification.
         * @param lastModified the last-modified timestamp
         * @return this builder
         */
        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        /**
         * Sets the tenant's external order reference number.
         * @param tenantOrderId the tenant order identifier
         * @return this builder
         */
        public Builder tenantOrderId(TenantOrderId tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        /**
         * Sets the current order status.
         * @param status the order status (e.g. {@code OPEN}, {@code LOCKED})
         * @return this builder
         */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the process identifier of the global process related to this order.
         * @param processId the process identifier
         * @return this builder
         */
        public Builder processId(ProcessId processId) {
            this.processId = processId;
            return this;
        }

        /**
         * Sets the schema version of this order document.
         * @param schemaVersion the schema version
         * @return this builder
         */
        public Builder schemaVersion(Double schemaVersion) {
            this.schemaVersion = schemaVersion;
            return this;
        }

        /**
         * Sets the date this order was created in the source system.
         * @param orderDate the order date
         * @return this builder
         */
        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        /**
         * Sets the line items on this order.
         * @param orderLineItems the order line items
         * @return this builder
         */
        public Builder orderLineItems(List<OrderLineItem> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        /**
         * Sets the consumer this order is for.
         * @param consumer the consumer
         * @return this builder
         */
        public Builder consumer(OrderForCreationConsumer consumer) {
            this.consumer = consumer;
            return this;
        }

        /**
         * Sets how and where this order should be delivered.
         * @param deliveryPreferences the delivery preferences
         * @return this builder
         */
        public Builder deliveryPreferences(DeliveryPreferences deliveryPreferences) {
            this.deliveryPreferences = deliveryPreferences;
            return this;
        }

        /**
         * Sets the payment information for this order.
         * @param paymentInfo the payment info
         * @return this builder
         */
        public Builder paymentInfo(OrderPaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
            return this;
        }

        /**
         * Sets the reason this order was cancelled, if applicable.
         * @param cancelationReason the cancellation reason
         * @return this builder
         */
        public Builder cancelationReason(CancelationReason cancelationReason) {
            this.cancelationReason = cancelationReason;
            return this;
        }

        /**
         * Sets optional references to custom services on this order.
         * @param customServices the custom service references
         * @return this builder
         */
        public Builder customServices(List<Map<String, Object>> customServices) {
            this.customServices = customServices;
            return this;
        }

        /**
         * Sets the log of updates made to this order.
         * @param updateDetails the update details
         * @return this builder
         */
        public Builder updateDetails(List<OrderUpdateDetail> updateDetails) {
            this.updateDetails = updateDetails;
            return this;
        }

        /**
         * Sets the tags attached to this order.
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Sets the visual stickers attached to this order.
         * @param stickers the stickers
         * @return this builder
         */
        public Builder stickers(List<Sticker> stickers) {
            this.stickers = stickers;
            return this;
        }

        /**
         * Sets the reasons explaining the current order status.
         * @param statusReasons the status reasons
         * @return this builder
         */
        public Builder statusReasons(List<OrderStatusReason> statusReasons) {
            this.statusReasons = statusReasons;
            return this;
        }

        /**
         * Sets the external system that created this order.
         * @param source the order source
         * @return this builder
         */
        public Builder source(OrderSource source) {
            this.source = source;
            return this;
        }

        /**
         * Sets free-form custom attributes.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Sets whether GDPR-related data has been anonymized.
         * @param anonymized {@code true} if the order is anonymized
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) {
            this.anonymized = anonymized;
            return this;
        }

        /**
         * Builds an {@link Order}.
         *
         * @return a new instance.
         */
        public Order build() {
            return new Order(
                    id, version, created, lastModified, tenantOrderId, status,
                    processId, schemaVersion, orderDate, orderLineItems, consumer,
                    deliveryPreferences, paymentInfo, cancelationReason, customServices,
                    updateDetails, tags, stickers, statusReasons, source,
                    customAttributes, anonymized);
        }
    }
}
