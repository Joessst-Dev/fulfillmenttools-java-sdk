package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateOrderRequest {

    private final Instant orderDate;
    private final List<OrderLineItemForCreation> orderLineItems;
    private final OrderForCreationConsumer consumer;
    private final String tenantOrderId;
    private final Map<String, Object> deliveryPreferences;
    private final OrderPaymentInfoForCreation paymentInfo;
    private final List<TagReference> tags;
    private final List<Map<String, Object>> stickers;
    private final List<Map<String, Object>> statusReasons;
    private final Map<String, Object> source;
    private final Map<String, Object> customAttributes;

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

    public Instant orderDate() { return orderDate; }
    public List<OrderLineItemForCreation> orderLineItems() { return orderLineItems; }
    public OrderForCreationConsumer consumer() { return consumer; }
    public String tenantOrderId() { return tenantOrderId; }
    public Map<String, Object> deliveryPreferences() { return deliveryPreferences; }
    public OrderPaymentInfoForCreation paymentInfo() { return paymentInfo; }
    public List<TagReference> tags() { return tags; }
    public List<Map<String, Object>> stickers() { return stickers; }
    public List<Map<String, Object>> statusReasons() { return statusReasons; }
    public Map<String, Object> source() { return source; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Instant orderDate;
        private List<OrderLineItemForCreation> orderLineItems;
        private OrderForCreationConsumer consumer;
        private String tenantOrderId;
        private Map<String, Object> deliveryPreferences;
        private OrderPaymentInfoForCreation paymentInfo;
        private List<TagReference> tags;
        private List<Map<String, Object>> stickers;
        private List<Map<String, Object>> statusReasons;
        private Map<String, Object> source;
        private Map<String, Object> customAttributes;

        public Builder orderDate(Instant orderDate) { this.orderDate = orderDate; return this; }
        public Builder orderLineItems(List<OrderLineItemForCreation> orderLineItems) { this.orderLineItems = orderLineItems; return this; }
        public Builder consumer(OrderForCreationConsumer consumer) { this.consumer = consumer; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder deliveryPreferences(Map<String, Object> deliveryPreferences) { this.deliveryPreferences = deliveryPreferences; return this; }
        public Builder paymentInfo(OrderPaymentInfoForCreation paymentInfo) { this.paymentInfo = paymentInfo; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder stickers(List<Map<String, Object>> stickers) { this.stickers = stickers; return this; }
        public Builder statusReasons(List<Map<String, Object>> statusReasons) { this.statusReasons = statusReasons; return this; }
        public Builder source(Map<String, Object> source) { this.source = source; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public CreateOrderRequest build() { return new CreateOrderRequest(this); }
    }
}
