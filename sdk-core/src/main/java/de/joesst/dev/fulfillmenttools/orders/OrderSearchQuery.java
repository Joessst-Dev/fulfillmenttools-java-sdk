package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents a search query for filtering orders.
 *
 * Use the builder to construct queries with various filter criteria.
 */
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

    /**
     * Returns the status filter.
     *
     * @return the status filter map, or null if not set
     */
    public Map<String, Object> status() { return status; }

    /**
     * Returns the tenant order ID filter.
     *
     * @return the tenant order ID filter map, or null if not set
     */
    public Map<String, Object> tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the order date filter.
     *
     * @return the order date filter map, or null if not set
     */
    public Map<String, Object> orderDate() { return orderDate; }

    /**
     * Returns the consumer filter.
     *
     * @return the consumer filter map, or null if not set
     */
    public Map<String, Object> consumer() { return consumer; }

    /**
     * Returns the delivery preferences filter.
     *
     * @return the delivery preferences filter map, or null if not set
     */
    public Map<String, Object> deliveryPreferences() { return deliveryPreferences; }

    /**
     * Returns the order line items filter.
     *
     * @return the order line items filter map, or null if not set
     */
    public Map<String, Object> orderLineItems() { return orderLineItems; }

    /**
     * Returns the payment info filter.
     *
     * @return the payment info filter map, or null if not set
     */
    public Map<String, Object> paymentInfo() { return paymentInfo; }

    /**
     * Returns the tags filter.
     *
     * @return the tags filter map, or null if not set
     */
    public Map<String, Object> tags() { return tags; }

    /**
     * Returns the AND combined queries.
     *
     * @return the AND queries list, or null if not set
     */
    public List<OrderSearchQuery> and() { return and; }

    /**
     * Returns the OR combined queries.
     *
     * @return the OR queries list, or null if not set
     */
    public List<OrderSearchQuery> or() { return or; }

    /**
     * Creates a new builder for constructing an {@code OrderSearchQuery}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderSearchQuery}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

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

        /**
         * Filters by status equal to the given value.
         *
         * @param value the status value
         * @return this builder
         */
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }

        /**
         * Filters by status in the given values.
         *
         * @param values the status values
         * @return this builder
         */
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by status in the given values.
         *
         * @param values the status values
         * @return this builder
         */
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }

        /**
         * Filters by status not equal to the given value.
         *
         * @param value the status value
         * @return this builder
         */
        public Builder statusNotEq(String value) { status = Map.of("notEq", value); return this; }

        /**
         * Filters by tenant order ID equal to the given value.
         *
         * @param value the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderIdEq(String value) { tenantOrderId = Map.of("eq", value); return this; }

        /**
         * Filters by tenant order ID matching the given pattern.
         *
         * @param pattern the pattern
         * @return this builder
         */
        public Builder tenantOrderIdLike(String pattern) { tenantOrderId = Map.of("like", pattern); return this; }

        /**
         * Filters by tenant order ID in the given values.
         *
         * @param values the tenant order IDs
         * @return this builder
         */
        public Builder tenantOrderIdIn(String... values) { tenantOrderId = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by tenant order ID in the given values.
         *
         * @param values the tenant order IDs
         * @return this builder
         */
        public Builder tenantOrderIdIn(List<String> values) { tenantOrderId = Map.of("in", values); return this; }

        /**
         * Filters by order date greater than or equal to the given instant.
         *
         * @param value the instant
         * @return this builder
         */
        public Builder orderDateGte(Instant value) { orderDate = Map.of("gte", value.toString()); return this; }

        /**
         * Filters by order date less than or equal to the given instant.
         *
         * @param value the instant
         * @return this builder
         */
        public Builder orderDateLte(Instant value) { orderDate = Map.of("lte", value.toString()); return this; }

        /**
         * Filters by order date between the given instants.
         *
         * @param from the start instant
         * @param to the end instant
         * @return this builder
         */
        public Builder orderDateBetween(Instant from, Instant to) {
            orderDate = Map.of("gte", from.toString(), "lte", to.toString());
            return this;
        }

        /**
         * Filters by consumer ID equal to the given value.
         *
         * @param consumerId the consumer ID
         * @return this builder
         */
        public Builder consumerIdEq(String consumerId) { consumer = Map.of("consumerId", Map.of("eq", consumerId)); return this; }

        /**
         * Filters by delivery preferences.
         *
         * @param filter the filter map
         * @return this builder
         */
        public Builder deliveryPreferences(Map<String, Object> filter) { deliveryPreferences = filter; return this; }

        /**
         * Filters by order line items.
         *
         * @param filter the filter map
         * @return this builder
         */
        public Builder orderLineItems(Map<String, Object> filter) { orderLineItems = filter; return this; }

        /**
         * Filters by payment info.
         *
         * @param filter the filter map
         * @return this builder
         */
        public Builder paymentInfo(Map<String, Object> filter) { paymentInfo = filter; return this; }

        /**
         * Filters by tags.
         *
         * @param filter the filter map
         * @return this builder
         */
        public Builder tags(Map<String, Object> filter) { tags = filter; return this; }

        /**
         * Combines multiple queries with AND logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder and(OrderSearchQuery... queries) { and = Arrays.asList(queries); return this; }

        /**
         * Combines multiple queries with AND logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder and(List<OrderSearchQuery> queries) { and = queries; return this; }

        /**
         * Combines multiple queries with OR logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder or(OrderSearchQuery... queries) { or = Arrays.asList(queries); return this; }

        /**
         * Combines multiple queries with OR logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder or(List<OrderSearchQuery> queries) { or = queries; return this; }

        /**
         * Builds the {@link OrderSearchQuery}.
         *
         * @return a new query
         */
        public OrderSearchQuery build() { return new OrderSearchQuery(this); }
    }
}
