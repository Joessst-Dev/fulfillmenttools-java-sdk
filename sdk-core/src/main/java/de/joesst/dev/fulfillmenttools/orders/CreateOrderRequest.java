package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Request object for creating a new fulfillmenttools order.
 *
 * <p>Maps to the {@code OrderForCreation} schema in the fulfillmenttools OpenAPI spec.
 * {@code orderDate}, {@code orderLineItems}, and {@code consumer} are required.
 *
 * <p>Thread-safety: immutable; safe for concurrent use.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CreateOrderRequest {

    private final Instant orderDate;
    private final List<OrderLineItemForCreation> orderLineItems;
    private final OrderForCreationConsumer consumer;
    private final TenantOrderId tenantOrderId;
    private final DeliveryPreferences deliveryPreferences;
    private final OrderPaymentInfoForCreation paymentInfo;
    private final List<TagReference> tags;
    private final List<Sticker> stickers;
    private final List<OrderStatusReason> statusReasons;
    private final OrderSource source;
    private final CustomAttributes customAttributes;

    private CreateOrderRequest(Builder builder) {
        this.orderDate = Objects.requireNonNull(builder.orderDate, "orderDate must not be null");
        this.orderLineItems = Objects.requireNonNull(builder.orderLineItems, "orderLineItems must not be null");
        this.consumer = Objects.requireNonNull(builder.consumer, "consumer must not be null");
        this.tenantOrderId = builder.tenantOrderId;
        this.deliveryPreferences = builder.deliveryPreferences;
        this.paymentInfo = builder.paymentInfo;
        this.tags = builder.tags;
        this.stickers = builder.stickers;
        this.statusReasons = builder.statusReasons;
        this.source = builder.source;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the order date.
     * @return the order date; never {@code null}
     */
    public Instant orderDate() { return orderDate; }

    /**
     * Returns the order line items.
     * @return the order line items; never {@code null}
     */
    public List<OrderLineItemForCreation> orderLineItems() { return orderLineItems; }

    /**
     * Returns the consumer.
     * @return the consumer; never {@code null}
     */
    public OrderForCreationConsumer consumer() { return consumer; }

    /**
     * Returns the tenant order identifier.
     * @return the tenant order ID, or {@code null} if not set
     */
    public TenantOrderId tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the delivery preferences.
     * @return the delivery preferences, or {@code null} if not set
     */
    public DeliveryPreferences deliveryPreferences() { return deliveryPreferences; }

    /**
     * Returns the payment info.
     * @return the payment info, or {@code null} if not set
     */
    public OrderPaymentInfoForCreation paymentInfo() { return paymentInfo; }

    /**
     * Returns the tags.
     * @return the tags, or {@code null} if not set
     */
    public List<TagReference> tags() { return tags; }

    /**
     * Returns the stickers.
     * @return the stickers, or {@code null} if not set
     */
    public List<Sticker> stickers() { return stickers; }

    /**
     * Returns the status reasons.
     * @return the status reasons, or {@code null} if not set
     */
    public List<OrderStatusReason> statusReasons() { return statusReasons; }

    /**
     * Returns the order source.
     * @return the order source, or {@code null} if not set
     */
    public OrderSource source() { return source; }

    /**
     * Returns the custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing a {@link CreateOrderRequest}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing {@link CreateOrderRequest} instances.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private Instant orderDate;
        private List<OrderLineItemForCreation> orderLineItems;
        private OrderForCreationConsumer consumer;
        private TenantOrderId tenantOrderId;
        private DeliveryPreferences deliveryPreferences;
        private OrderPaymentInfoForCreation paymentInfo;
        private List<TagReference> tags;
        private List<Sticker> stickers;
        private List<OrderStatusReason> statusReasons;
        private OrderSource source;
        private CustomAttributes customAttributes;

        /**
         * Sets the order date (required).
         * @param orderDate the order date
         * @return this builder
         */
        public Builder orderDate(Instant orderDate) { this.orderDate = orderDate; return this; }

        /**
         * Sets the order line items (required).
         * @param orderLineItems the line items
         * @return this builder
         */
        public Builder orderLineItems(List<OrderLineItemForCreation> orderLineItems) { this.orderLineItems = orderLineItems; return this; }

        /**
         * Sets the consumer (required).
         * @param consumer the consumer
         * @return this builder
         */
        public Builder consumer(OrderForCreationConsumer consumer) { this.consumer = consumer; return this; }

        /**
         * Sets the tenant order identifier.
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Sets the delivery preferences.
         * @param deliveryPreferences the delivery preferences
         * @return this builder
         */
        public Builder deliveryPreferences(DeliveryPreferences deliveryPreferences) { this.deliveryPreferences = deliveryPreferences; return this; }

        /**
         * Sets the payment info.
         * @param paymentInfo the payment info
         * @return this builder
         */
        public Builder paymentInfo(OrderPaymentInfoForCreation paymentInfo) { this.paymentInfo = paymentInfo; return this; }

        /**
         * Sets the tags.
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        /**
         * Sets the stickers.
         * @param stickers the stickers
         * @return this builder
         */
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }

        /**
         * Sets the status reasons.
         * @param statusReasons the status reasons
         * @return this builder
         */
        public Builder statusReasons(List<OrderStatusReason> statusReasons) { this.statusReasons = statusReasons; return this; }

        /**
         * Sets the order source.
         * @param source the order source
         * @return this builder
         */
        public Builder source(OrderSource source) { this.source = source; return this; }

        /**
         * Sets the custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new {@link CreateOrderRequest}.
         * @return a new request instance
         * @throws NullPointerException if any required field is not set
         */
        public CreateOrderRequest build() { return new CreateOrderRequest(this); }
    }
}
