package de.joesst.dev.fulfillmenttools.orders;

public final class OrderLineItemForUpdate {

    private final String id;
    private final Integer quantity;
    private final OrderLineItemArticleForUpdate article;

    private OrderLineItemForUpdate(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.article = builder.article;
    }

    public String id() { return id; }
    public Integer quantity() { return quantity; }
    public OrderLineItemArticleForUpdate article() { return article; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String id;
        private Integer quantity;
        private OrderLineItemArticleForUpdate article;

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder article(OrderLineItemArticleForUpdate article) { this.article = article; return this; }

        public OrderLineItemForUpdate build() { return new OrderLineItemForUpdate(this); }
    }
}
