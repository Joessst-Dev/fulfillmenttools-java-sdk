package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * A reference to an article with a quantity, used in custom service context.
 *
 * <p>Maps to the {@code ArticleItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleRef The tenant's article reference (required).
 * @param quantity         The quantity of this article (required, minimum 1).
 */
public record ArticleItem(
        String tenantArticleRef,
        Double quantity
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String tenantArticleRef;
        private Double quantity;

        private Builder() {}

        public Builder tenantArticleRef(String tenantArticleRef) { this.tenantArticleRef = tenantArticleRef; return this; }
        public Builder quantity(Double quantity) { this.quantity = quantity; return this; }

        public ArticleItem build() {
            return new ArticleItem(tenantArticleRef, quantity);
        }
    }
}
