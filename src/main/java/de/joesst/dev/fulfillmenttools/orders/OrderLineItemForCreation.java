package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;
import java.util.Objects;

public final class OrderLineItemForCreation {

    private final OrderLineItemArticleForCreation article;
    private final Integer quantity;
    private final String measurementUnitKey;
    private final Map<String, Object> customAttributes;

    private OrderLineItemForCreation(Builder builder) {
        this.article = Objects.requireNonNull(builder.article, "article must not be null");
        this.quantity = Objects.requireNonNull(builder.quantity, "quantity must not be null");
        this.measurementUnitKey = builder.measurementUnitKey;
        this.customAttributes = builder.customAttributes;
    }

    public OrderLineItemArticleForCreation article() { return article; }
    public Integer quantity() { return quantity; }
    public String measurementUnitKey() { return measurementUnitKey; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private OrderLineItemArticleForCreation article;
        private Integer quantity;
        private String measurementUnitKey;
        private Map<String, Object> customAttributes;

        public Builder article(OrderLineItemArticleForCreation article) {
            this.article = article;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder measurementUnitKey(String measurementUnitKey) {
            this.measurementUnitKey = measurementUnitKey;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public OrderLineItemForCreation build() { return new OrderLineItemForCreation(this); }
    }
}
