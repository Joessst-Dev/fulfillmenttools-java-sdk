package de.joesst.dev.fulfillmenttools.orders;

import java.util.Objects;

public final class OrderLineItemArticleForCreation {

    private final String tenantArticleId;
    private final String title;
    private final String imageUrl;

    private OrderLineItemArticleForCreation(Builder builder) {
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
    }

    public String tenantArticleId() { return tenantArticleId; }
    public String title() { return title; }
    public String imageUrl() { return imageUrl; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String tenantArticleId;
        private String title;
        private String imageUrl;

        public Builder tenantArticleId(String tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public OrderLineItemArticleForCreation build() { return new OrderLineItemArticleForCreation(this); }
    }
}
