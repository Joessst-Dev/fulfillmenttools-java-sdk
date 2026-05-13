package de.joesst.dev.fulfillmenttools.orders;

/**
 * Represents a line item for order update operations.
 */
public final class OrderLineItemForUpdate {

    private final String id;
    private final Integer quantity;
    private final OrderLineItemArticleForUpdate article;

    private OrderLineItemForUpdate(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.article = builder.article;
    }

    /**
     * Returns the line item ID.
     *
     * @return the line item ID, or null if not set
     */
    public String id() { return id; }

    /**
     * Returns the quantity.
     *
     * @return the quantity, or null if not set
     */
    public Integer quantity() { return quantity; }

    /**
     * Returns the article information.
     *
     * @return the article, or null if not set
     */
    public OrderLineItemArticleForUpdate article() { return article; }

    /**
     * Creates a new builder for constructing an {@code OrderLineItemForUpdate}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderLineItemForUpdate}.
     */
    public static final class Builder {
        /** Creates a new Builder. */
        public Builder() {}

        private String id;
        private Integer quantity;
        private OrderLineItemArticleForUpdate article;

        /**
         * Sets the line item ID.
         *
         * @param id the ID
         * @return this builder
         */
        public Builder id(String id) { this.id = id; return this; }

        /**
         * Sets the quantity.
         *
         * @param quantity the quantity
         * @return this builder
         */
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }

        /**
         * Sets the article information.
         *
         * @param article the article
         * @return this builder
         */
        public Builder article(OrderLineItemArticleForUpdate article) { this.article = article; return this; }

        /**
         * Builds the {@link OrderLineItemForUpdate}.
         *
         * @return a new line item
         */
        public OrderLineItemForUpdate build() { return new OrderLineItemForUpdate(this); }
    }
}
