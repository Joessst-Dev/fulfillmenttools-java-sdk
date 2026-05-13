package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a pack line item.
 *
 * <p>Maps to the {@code PackLineItemArticle} schema in the fulfillmenttools OpenAPI spec,
 * which is an extension of {@code AbstractArticle}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId Tenant-side article identifier (e.g. {@code "4711"}).
 * @param title           Display title of the article.
 * @param imageUrl        URL to an image of the article (no authentication required).
 * @param weight          Weight of the article in grams.
 * @param titleLocalized  Localized translations for the article title, keyed by locale
 *                        (e.g. {@code "en_US"}).
 * @param customAttributes Free-form custom attributes (not used in fulfillment processes).
 * @param attributes      Structured attributes providing additional metadata for the article
 *                        (e.g. subtitle, sales price, dimensions). Up to 40 items.
 */
public record PackLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        Map<String, Object> customAttributes,
        List<ArticleAttribute> attributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TenantArticleId tenantArticleId;
        private String title;
        private String imageUrl;
        private Double weight;
        private Map<String, String> titleLocalized;
        private Map<String, Object> customAttributes;
        private List<ArticleAttribute> attributes;

        private Builder() {}

        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder attributes(List<ArticleAttribute> attributes) { this.attributes = attributes; return this; }

        public PackLineItemArticle build() {
            return new PackLineItemArticle(tenantArticleId, title, imageUrl, weight, titleLocalized, customAttributes, attributes);
        }
    }
}
