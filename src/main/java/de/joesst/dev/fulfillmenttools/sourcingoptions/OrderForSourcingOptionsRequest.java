package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderPaymentInfoForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderStatusReason;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final String tenantOrderId;
    private final Instant orderDate;
    private final DeliveryPreferences deliveryPreferences;
    private final String status;
    private final Map<String, Object> customAttributes;
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

    /** The consumer this order is for. Required. */
    public ConsumerAddressesForSourcingOptions consumer() { return consumer; }
    /** The line items on this order. */
    public List<OrderLineItemForCreation> orderLineItems() { return orderLineItems; }
    /** The tenant's external order reference number. */
    public String tenantOrderId() { return tenantOrderId; }
    /** The date this order was created in the source system. */
    public Instant orderDate() { return orderDate; }
    /** How and where this order should be delivered. */
    public DeliveryPreferences deliveryPreferences() { return deliveryPreferences; }
    /** Current order status (e.g. {@code OPEN}). */
    public String status() { return status; }
    /** Free-form custom attributes. */
    public Map<String, Object> customAttributes() { return customAttributes; }
    /** Custom services attached to this order. */
    public List<SourcingOptionsCustomService> customServices() { return customServices; }
    /** Payment information for this order. */
    public OrderPaymentInfoForCreation paymentInfo() { return paymentInfo; }
    /** Options controlling delivery promise calculation. */
    public SourcingOptionsPromisesOptions promisesOptions() { return promisesOptions; }
    /** Reasons explaining the current order status. */
    public List<OrderStatusReason> statusReasons() { return statusReasons; }
    /** Visual stickers attached to this order. */
    public List<Sticker> stickers() { return stickers; }
    /** Tag references attached to this order. */
    public List<TagReference> tags() { return tags; }

    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderForSourcingOptionsRequest}.
     */
    public static final class Builder {

        private ConsumerAddressesForSourcingOptions consumer;
        private List<OrderLineItemForCreation> orderLineItems;
        private String tenantOrderId;
        private Instant orderDate;
        private DeliveryPreferences deliveryPreferences;
        private String status;
        private Map<String, Object> customAttributes;
        private List<SourcingOptionsCustomService> customServices;
        private OrderPaymentInfoForCreation paymentInfo;
        private SourcingOptionsPromisesOptions promisesOptions;
        private List<OrderStatusReason> statusReasons;
        private List<Sticker> stickers;
        private List<TagReference> tags;

        public Builder consumer(ConsumerAddressesForSourcingOptions consumer) {
            this.consumer = consumer;
            return this;
        }

        public Builder orderLineItems(List<OrderLineItemForCreation> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder deliveryPreferences(DeliveryPreferences deliveryPreferences) {
            this.deliveryPreferences = deliveryPreferences;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Builder customServices(List<SourcingOptionsCustomService> customServices) {
            this.customServices = customServices;
            return this;
        }

        public Builder paymentInfo(OrderPaymentInfoForCreation paymentInfo) {
            this.paymentInfo = paymentInfo;
            return this;
        }

        public Builder promisesOptions(SourcingOptionsPromisesOptions promisesOptions) {
            this.promisesOptions = promisesOptions;
            return this;
        }

        public Builder statusReasons(List<OrderStatusReason> statusReasons) {
            this.statusReasons = statusReasons;
            return this;
        }

        public Builder stickers(List<Sticker> stickers) {
            this.stickers = stickers;
            return this;
        }

        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
            return this;
        }

        public OrderForSourcingOptionsRequest build() { return new OrderForSourcingOptionsRequest(this); }
    }
}
