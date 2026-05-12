package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class OrderSearchQuery {

    private final Map<String, Object> status;
    private final Map<String, Object> tenantOrderId;
    private final Map<String, Object> orderDate;
    private final Map<String, Object> consumer;
    private final Map<String, Object> deliveryPreferences;
    private final Map<String, Object> orderLineItems;
    private final Map<String, Object> paymentInfo;
    private final Map<String, Object> tags;
    private final List<OrderSearchQuery> and;
    private final List<OrderSearchQuery> or;

    private OrderSearchQuery(Builder b) {
        this.status = b.status;
        this.tenantOrderId = b.tenantOrderId;
        this.orderDate = b.orderDate;
        this.consumer = b.consumer;
        this.deliveryPreferences = b.deliveryPreferences;
        this.orderLineItems = b.orderLineItems;
        this.paymentInfo = b.paymentInfo;
        this.tags = b.tags;
        this.and = b.and;
        this.or = b.or;
    }

    public Map<String, Object> status() { return status; }
    public Map<String, Object> tenantOrderId() { return tenantOrderId; }
    public Map<String, Object> orderDate() { return orderDate; }
    public Map<String, Object> consumer() { return consumer; }
    public Map<String, Object> deliveryPreferences() { return deliveryPreferences; }
    public Map<String, Object> orderLineItems() { return orderLineItems; }
    public Map<String, Object> paymentInfo() { return paymentInfo; }
    public Map<String, Object> tags() { return tags; }
    public List<OrderSearchQuery> and() { return and; }
    public List<OrderSearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> status;
        private Map<String, Object> tenantOrderId;
        private Map<String, Object> orderDate;
        private Map<String, Object> consumer;
        private Map<String, Object> deliveryPreferences;
        private Map<String, Object> orderLineItems;
        private Map<String, Object> paymentInfo;
        private Map<String, Object> tags;
        private List<OrderSearchQuery> and;
        private List<OrderSearchQuery> or;

        // --- status ---
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }
        public Builder statusNotEq(String value) { status = Map.of("notEq", value); return this; }

        // --- tenantOrderId ---
        public Builder tenantOrderIdEq(String value) { tenantOrderId = Map.of("eq", value); return this; }
        public Builder tenantOrderIdLike(String pattern) { tenantOrderId = Map.of("like", pattern); return this; }
        public Builder tenantOrderIdIn(String... values) { tenantOrderId = Map.of("in", List.of(values)); return this; }
        public Builder tenantOrderIdIn(List<String> values) { tenantOrderId = Map.of("in", values); return this; }

        // --- orderDate ---
        public Builder orderDateGte(Instant value) { orderDate = Map.of("gte", value.toString()); return this; }
        public Builder orderDateLte(Instant value) { orderDate = Map.of("lte", value.toString()); return this; }
        public Builder orderDateBetween(Instant from, Instant to) {
            orderDate = Map.of("gte", from.toString(), "lte", to.toString());
            return this;
        }

        // --- consumer ---
        public Builder consumerIdEq(String consumerId) { consumer = Map.of("consumerId", Map.of("eq", consumerId)); return this; }

        // --- deliveryPreferences (pass-through nested filter) ---
        public Builder deliveryPreferences(Map<String, Object> filter) { deliveryPreferences = filter; return this; }

        // --- orderLineItems (pass-through nested list filter) ---
        public Builder orderLineItems(Map<String, Object> filter) { orderLineItems = filter; return this; }

        // --- paymentInfo (pass-through nested filter) ---
        public Builder paymentInfo(Map<String, Object> filter) { paymentInfo = filter; return this; }

        // --- tags (pass-through nested list filter) ---
        public Builder tags(Map<String, Object> filter) { tags = filter; return this; }

        // --- logical combinators ---
        public Builder and(OrderSearchQuery... queries) { and = Arrays.asList(queries); return this; }
        public Builder and(List<OrderSearchQuery> queries) { and = queries; return this; }
        public Builder or(OrderSearchQuery... queries) { or = Arrays.asList(queries); return this; }
        public Builder or(List<OrderSearchQuery> queries) { or = queries; return this; }

        public OrderSearchQuery build() { return new OrderSearchQuery(this); }
    }
}
