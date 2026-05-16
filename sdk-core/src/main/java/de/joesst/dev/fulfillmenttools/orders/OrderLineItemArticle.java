package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * An article on a read-side order line item.
 *
 * <p>Maps to the {@code OrderLineItemArticle} schema (extends {@code AbstractArticle}) in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId The tenant's article reference number.
 * @param title           The article display title.
 * @param imageUrl        Optional URL of the product image.
 * @param weight          Optional article weight in grams.
 * @param titleLocalized  Localized translations for the title, keyed by locale (e.g.
 *                        {@code en_US}).
 * @param attributes      Optional article attributes used for display and customization.
 * @param customAttributes Free-form custom attributes (deprecated on article level; prefer
 *                         {@code customAttributes} on the line item).
 */
public record OrderLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        List<ArticleAttribute> attributes,
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing an {@code OrderLineItemArticle}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OrderLineItemArticle}.
     */
    public static final class Builder {

        private TenantArticleId tenantArticleId;
        private String title;
        private String imageUrl;
        private Double weight;
        private Map<String, String> titleLocalized;
        private List<ArticleAttribute> attributes;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * Sets the tenant's article reference number.
         * @param tenantArticleId the tenant article identifier
         * @return this builder
         */
        public Builder tenantArticleId(TenantArticleId tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        /**
         * Sets the article display title.
         * @param title the display title
         * @return this builder
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the URL of the product image.
         * @param imageUrl the image URL
         * @return this builder
         */
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * Sets the article weight in grams.
         * @param weight the weight in grams
         * @return this builder
         */
        public Builder weight(Double weight) {
            this.weight = weight;
            return this;
        }

        /**
         * Sets the localized translations for the title, keyed by locale.
         * @param titleLocalized map of locale to localized title
         * @return this builder
         */
        public Builder titleLocalized(Map<String, String> titleLocalized) {
            this.titleLocalized = titleLocalized;
            return this;
        }

        /**
         * Sets the article attributes used for display and customization.
         * @param attributes the article attributes
         * @return this builder
         */
        public Builder attributes(List<ArticleAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Sets free-form custom attributes.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds an {@link OrderLineItemArticle}.
         *
         * @return a new instance.
         */
        public OrderLineItemArticle build() {
            return new OrderLineItemArticle(
                    tenantArticleId, title, imageUrl, weight, titleLocalized,
                    attributes, customAttributes);
        }
    }
}
