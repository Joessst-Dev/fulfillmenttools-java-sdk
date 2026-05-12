package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;
import java.util.Objects;

public final class OrderLineItemArticleForCreation {

    private final String tenantArticleId;
    private final String title;
    private final String imageUrl;
    private final Double weight;
    private final Map<String, Object> titleLocalized;
    private final Map<String, Object> customAttributes;

    private OrderLineItemArticleForCreation(Builder builder) {
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.weight = builder.weight;
        this.titleLocalized = builder.titleLocalized;
        this.customAttributes = builder.customAttributes;
    }

    public String tenantArticleId() { return tenantArticleId; }
    public String title() { return title; }
    public String imageUrl() { return imageUrl; }
    public Double weight() { return weight; }
    public Map<String, Object> titleLocalized() { return titleLocalized; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String tenantArticleId;
        private String title;
        private String imageUrl;
        private Double weight;
        private Map<String, Object> titleLocalized;
        private Map<String, Object> customAttributes;

        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder titleLocalized(Map<String, Object> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public OrderLineItemArticleForCreation build() { return new OrderLineItemArticleForCreation(this); }
    }
}
