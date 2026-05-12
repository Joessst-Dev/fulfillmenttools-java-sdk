package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class OrderForSourcingOptionsRequest {

    private final ConsumerAddressesForSourcingOptions consumer;
    private final List<OrderLineItemForCreation> orderLineItems;
    private final String tenantOrderId;
    private final Instant orderDate;
    private final DeliveryPreferences deliveryPreferences;
    private final String status;
    private final Map<String, Object> customAttributes;
    private final List<Map<String, Object>> customServices;
    private final Map<String, Object> paymentInfo;
    private final Map<String, Object> promisesOptions;
    private final List<Map<String, Object>> statusReasons;
    private final List<Map<String, Object>> stickers;
    private final List<Map<String, Object>> tags;

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

    public ConsumerAddressesForSourcingOptions consumer() { return consumer; }
    public List<OrderLineItemForCreation> orderLineItems() { return orderLineItems; }
    public String tenantOrderId() { return tenantOrderId; }
    public Instant orderDate() { return orderDate; }
    public DeliveryPreferences deliveryPreferences() { return deliveryPreferences; }
    public String status() { return status; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public List<Map<String, Object>> customServices() { return customServices; }
    public Map<String, Object> paymentInfo() { return paymentInfo; }
    public Map<String, Object> promisesOptions() { return promisesOptions; }
    public List<Map<String, Object>> statusReasons() { return statusReasons; }
    public List<Map<String, Object>> stickers() { return stickers; }
    public List<Map<String, Object>> tags() { return tags; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private ConsumerAddressesForSourcingOptions consumer;
        private List<OrderLineItemForCreation> orderLineItems;
        private String tenantOrderId;
        private Instant orderDate;
        private DeliveryPreferences deliveryPreferences;
        private String status;
        private Map<String, Object> customAttributes;
        private List<Map<String, Object>> customServices;
        private Map<String, Object> paymentInfo;
        private Map<String, Object> promisesOptions;
        private List<Map<String, Object>> statusReasons;
        private List<Map<String, Object>> stickers;
        private List<Map<String, Object>> tags;

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

        public Builder customServices(List<Map<String, Object>> customServices) {
            this.customServices = customServices;
            return this;
        }

        public Builder paymentInfo(Map<String, Object> paymentInfo) {
            this.paymentInfo = paymentInfo;
            return this;
        }

        public Builder promisesOptions(Map<String, Object> promisesOptions) {
            this.promisesOptions = promisesOptions;
            return this;
        }

        public Builder statusReasons(List<Map<String, Object>> statusReasons) {
            this.statusReasons = statusReasons;
            return this;
        }

        public Builder stickers(List<Map<String, Object>> stickers) {
            this.stickers = stickers;
            return this;
        }

        public Builder tags(List<Map<String, Object>> tags) {
            this.tags = tags;
            return this;
        }

        public OrderForSourcingOptionsRequest build() { return new OrderForSourcingOptionsRequest(this); }
    }
}
