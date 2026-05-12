package de.joesst.dev.fulfillmenttools.orders;

public final class OrderLineItemArticleForUpdate {

    private final String tenantArticleId;
    private final String title;
    private final String imageUrl;

    private OrderLineItemArticleForUpdate(Builder builder) {
        this.tenantArticleId = builder.tenantArticleId;
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

        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }

        public OrderLineItemArticleForUpdate build() { return new OrderLineItemArticleForUpdate(this); }
    }
}
