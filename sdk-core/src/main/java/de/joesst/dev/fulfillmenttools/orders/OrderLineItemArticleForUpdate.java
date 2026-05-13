package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

/**
 * Represents an article in a line item for order update operations.
 */
public final class OrderLineItemArticleForUpdate {

    private final TenantArticleId tenantArticleId;
    private final String title;
    private final String imageUrl;

    private OrderLineItemArticleForUpdate(Builder builder) {
        this.tenantArticleId = builder.tenantArticleId;
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
    }

    /**
     * Returns the tenant article ID.
     *
     * @return the tenant article ID, or null if not set
     */
    public TenantArticleId tenantArticleId() { return tenantArticleId; }

    /**
     * Returns the article title.
     *
     * @return the title, or null if not set
     */
    public String title() { return title; }

    /**
     * Returns the article image URL.
     *
     * @return the image URL, or null if not set
     */
    public String imageUrl() { return imageUrl; }

    /**
     * Creates a new builder for constructing an {@code OrderLineItemArticleForUpdate}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderLineItemArticleForUpdate}.
     */
    public static final class Builder {
        /** Creates a new Builder. */
        public Builder() {}

        private TenantArticleId tenantArticleId;
        private String title;
        private String imageUrl;

        /**
         * Sets the tenant article ID.
         *
         * @param tenantArticleId the article ID
         * @return this builder
         */
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }

        /**
         * Sets the article title.
         *
         * @param title the title
         * @return this builder
         */
        public Builder title(String title) { this.title = title; return this; }

        /**
         * Sets the article image URL.
         *
         * @param imageUrl the image URL
         * @return this builder
         */
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }

        /**
         * Builds the {@link OrderLineItemArticleForUpdate}.
         *
         * @return a new article
         */
        public OrderLineItemArticleForUpdate build() { return new OrderLineItemArticleForUpdate(this); }
    }
}
