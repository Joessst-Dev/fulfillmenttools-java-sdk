package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderPaymentInfoForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderStatusReason;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * An order submitted to the sourcing options engine for fulfillment evaluation.
 *
 * <p>Maps to the {@code OrderForSourcingOptionsRequest} schema in the fulfillmenttools
 * OpenAPI spec. The {@code consumer} field is required.
 *
 * <p>Thread-safety: immutable; safe for concurrent use.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrderForSourcingOptionsRequest {

    private final ConsumerAddressesForSourcingOptions consumer;
    private final List<OrderLineItemForCreation> orderLineItems;
    private final TenantOrderId tenantOrderId;
    private final Instant orderDate;
    private final DeliveryPreferences deliveryPreferences;
    private final String status;
    private final CustomAttributes customAttributes;
    private final List<SourcingOptionsCustomService> customServices;
    private final OrderPaymentInfoForCreation paymentInfo;
    private final SourcingOptionsPromisesOptions promisesOptions;
    private final List<OrderStatusReason> statusReasons;
    private final List<Sticker> stickers;
    private final List<TagReference> tags;

    private OrderForSourcingOptionsRequest(Builder builder) {
        this.consumer = Objects.requireNonNull(builder.consumer, "consumer must not be null");
        this.orderLineItems = builder.orderLineItems;
        this.tenantOrderId = builder.tenantOrderId;
        this.orderDate = builder.orderDate;
        this.deliveryPreferences = builder.deliveryPreferences;
        this.status = builder.status;
        this.customAttributes = builder.customAttributes;
        this.customServices = builder.customServices;
        this.paymentInfo = builder.paymentInfo;
        this.promisesOptions = builder.promisesOptions;
        this.statusReasons = builder.statusReasons;
        this.stickers = builder.stickers;
        this.tags = builder.tags;
    }

    /**
     * The consumer this order is for. Required.
     *
     * @return the consumer address
     */
    public ConsumerAddressesForSourcingOptions consumer() { return consumer; }

    /**
     * The line items on this order.
     *
     * @return the order line items, or null if not set
     */
    public List<OrderLineItemForCreation> orderLineItems() { return orderLineItems; }

    /**
     * The tenant's external order reference number.
     *
     * @return the tenant order ID, or null if not set
     */
    public TenantOrderId tenantOrderId() { return tenantOrderId; }

    /**
     * The date this order was created in the source system.
     *
     * @return the order date, or null if not set
     */
    public Instant orderDate() { return orderDate; }

    /**
     * How and where this order should be delivered.
     *
     * @return the delivery preferences, or null if not set
     */
    public DeliveryPreferences deliveryPreferences() { return deliveryPreferences; }

    /**
     * Current order status (e.g. {@code OPEN}).
     *
     * @return the status, or null if not set
     */
    public String status() { return status; }

    /**
     * Free-form custom attributes.
     *
     * @return the custom attributes, or null if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Custom services attached to this order.
     *
     * @return the custom services, or null if not set
     */
    public List<SourcingOptionsCustomService> customServices() { return customServices; }

    /**
     * Payment information for this order.
     *
     * @return the payment info, or null if not set
     */
    public OrderPaymentInfoForCreation paymentInfo() { return paymentInfo; }

    /**
     * Options controlling delivery promise calculation.
     *
     * @return the promises options, or null if not set
     */
    public SourcingOptionsPromisesOptions promisesOptions() { return promisesOptions; }

    /**
     * Reasons explaining the current order status.
     *
     * @return the status reasons, or null if not set
     */
    public List<OrderStatusReason> statusReasons() { return statusReasons; }

    /**
     * Visual stickers attached to this order.
     *
     * @return the stickers, or null if not set
     */
    public List<Sticker> stickers() { return stickers; }

    /**
     * Tag references attached to this order.
     *
     * @return the tags, or null if not set
     */
    public List<TagReference> tags() { return tags; }

    /**
     * Returns a new builder for constructing {@code OrderForSourcingOptionsRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderForSourcingOptionsRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private ConsumerAddressesForSourcingOptions consumer;
        private List<OrderLineItemForCreation> orderLineItems;
        private TenantOrderId tenantOrderId;
        private Instant orderDate;
        private DeliveryPreferences deliveryPreferences;
        private String status;
        private CustomAttributes customAttributes;
        private List<SourcingOptionsCustomService> customServices;
        private OrderPaymentInfoForCreation paymentInfo;
        private SourcingOptionsPromisesOptions promisesOptions;
        private List<OrderStatusReason> statusReasons;
        private List<Sticker> stickers;
        private List<TagReference> tags;

        /**
         * Sets the consumer this order is for.
         *
         * @param consumer the consumer address
         * @return this builder
         */
        public Builder consumer(ConsumerAddressesForSourcingOptions consumer) {
            this.consumer = consumer;
            return this;
        }

        /**
         * Sets the line items on this order.
         *
         * @param orderLineItems the order line items
         * @return this builder
         */
        public Builder orderLineItems(List<OrderLineItemForCreation> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        /**
         * Sets the tenant's external order reference number.
         *
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(TenantOrderId tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        /**
         * Sets the date this order was created in the source system.
         *
         * @param orderDate the order date
         * @return this builder
         */
        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        /**
         * Sets how and where this order should be delivered.
         *
         * @param deliveryPreferences the delivery preferences
         * @return this builder
         */
        public Builder deliveryPreferences(DeliveryPreferences deliveryPreferences) {
            this.deliveryPreferences = deliveryPreferences;
            return this;
        }

        /**
         * Sets the current order status (e.g. {@code OPEN}).
         *
         * @param status the status
         * @return this builder
         */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * Sets free-form custom attributes.
         *
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Sets custom services attached to this order.
         *
         * @param customServices the custom services
         * @return this builder
         */
        public Builder customServices(List<SourcingOptionsCustomService> customServices) {
            this.customServices = customServices;
            return this;
        }

        /**
         * Sets payment information for this order.
         *
         * @param paymentInfo the payment info
         * @return this builder
         */
        public Builder paymentInfo(OrderPaymentInfoForCreation paymentInfo) {
            this.paymentInfo = paymentInfo;
            return this;
        }

        /**
         * Sets options controlling delivery promise calculation.
         *
         * @param promisesOptions the promises options
         * @return this builder
         */
        public Builder promisesOptions(SourcingOptionsPromisesOptions promisesOptions) {
            this.promisesOptions = promisesOptions;
            return this;
        }

        /**
         * Sets reasons explaining the current order status.
         *
         * @param statusReasons the status reasons
         * @return this builder
         */
        public Builder statusReasons(List<OrderStatusReason> statusReasons) {
            this.statusReasons = statusReasons;
            return this;
        }

        /**
         * Sets visual stickers attached to this order.
         *
         * @param stickers the stickers
         * @return this builder
         */
        public Builder stickers(List<Sticker> stickers) {
            this.stickers = stickers;
            return this;
        }

        /**
         * Sets tag references attached to this order.
         *
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Builds the {@link OrderForSourcingOptionsRequest}.
         *
         * @return a new {@code OrderForSourcingOptionsRequest}
         * @throws NullPointerException if consumer has not been set
         */
        public OrderForSourcingOptionsRequest build() { return new OrderForSourcingOptionsRequest(this); }
    }
}
