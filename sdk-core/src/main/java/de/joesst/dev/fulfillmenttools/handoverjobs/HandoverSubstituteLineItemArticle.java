package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a substitute line item in a handover job.
 *
 * <p>Maps to the {@code SubstituteLineItemArticle} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code AbstractArticle} with an optional {@code attributes} list.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId  Tenant-specific article reference number. Required.
 * @param title            Display title of the substitute article. Required.
 * @param imageUrl         Optional URL of the product image; no authentication required.
 * @param weight           Optional weight in grams.
 * @param titleLocalized   Localized translations for the title, keyed by locale
 *                         (e.g. {@code en_US}).
 * @param customAttributes Free-form custom attributes attached to the article.
 * @param attributes       Optional article attributes for display and process customization.
 *                         Maximum 40 items.
 */
public record HandoverSubstituteLineItemArticle(
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

        public HandoverSubstituteLineItemArticle build() {
            return new HandoverSubstituteLineItemArticle(tenantArticleId, title, imageUrl, weight, titleLocalized, customAttributes, attributes);
        }
    }
}
