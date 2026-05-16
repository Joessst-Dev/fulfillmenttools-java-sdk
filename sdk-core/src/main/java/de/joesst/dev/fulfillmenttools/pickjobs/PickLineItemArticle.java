package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a pick line item.
 *
 * <p>Maps to the {@code PickLineItemArticle} schema (which extends {@code LineItemArticle})
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId  The tenant-specific article reference number.
 * @param title            The display title of the article.
 * @param imageUrl         Optional URL of the product image; no authentication required.
 * @param weight           Optional weight in grams.
 * @param titleLocalized   Localized translations for the title, keyed by locale
 *                         (e.g. {@code en_US}).
 * @param attributes       Optional article attributes for display and process customization.
 * @param customAttributes Deprecated: use {@code customAttributes} on the line item instead.
 *                         Retained for backward compatibility.
 */
public record PickLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        List<ArticleAttribute> attributes,
        CustomAttributes customAttributes
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
        private List<ArticleAttribute> attributes;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder attributes(List<ArticleAttribute> attributes) { this.attributes = attributes; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public PickLineItemArticle build() {
            return new PickLineItemArticle(tenantArticleId, title, imageUrl, weight, titleLocalized, attributes, customAttributes);
        }
    }
}
