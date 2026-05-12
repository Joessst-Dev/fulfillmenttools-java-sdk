package de.joesst.dev.fulfillmenttools.orders;

import java.util.Objects;

public final class OrderSearchRequest {

    private final OrderSearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;

    private OrderSearchRequest(Builder builder) {
        this.query = Objects.requireNonNull(builder.query, "query must not be null");
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
    }

    public OrderSearchQuery query() { return query; }
    public Integer size() { return size; }
    public String after() { return after; }
    public String before() { return before; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private OrderSearchQuery query;
        private Integer size;
        private String after;
        private String before;

        public Builder query(OrderSearchQuery query) { this.query = query; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder after(String after) { this.after = after; return this; }
        public Builder before(String before) { this.before = before; return this; }

        public OrderSearchRequest build() { return new OrderSearchRequest(this); }
    }
}
