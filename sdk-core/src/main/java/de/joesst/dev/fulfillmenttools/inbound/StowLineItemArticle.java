package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a stow line item.
 *
 * <p>Maps to the {@code StowLineItemArticle} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId   Tenant-specific article identifier that the stow line item refers to.
 * @param title             Display title of the article.
 * @param imageUrl          Optional URL to a product image. No authentication must be required
 *                          to fetch the image.
 * @param measurementUnitKey Identifier for the article's unit of measurement.
 * @param customAttributes  Free-form custom attributes.
 * @param titleLocalized    Localized translations for the article title, keyed by locale
 *                          (e.g. {@code en_US}).
 * @param scannableCodes    Barcodes or other scannable codes related to the article.
 */
public record StowLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        String measurementUnitKey,
        Map<String, Object> customAttributes,
        Map<String, String> titleLocalized,
        List<String> scannableCodes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TenantArticleId tenantArticleId;
        private String title;
        private String imageUrl;
        private String measurementUnitKey;
        private Map<String, Object> customAttributes;
        private Map<String, String> titleLocalized;
        private List<String> scannableCodes;

        private Builder() {}

        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        public StowLineItemArticle build() {
            return new StowLineItemArticle(tenantArticleId, title, imageUrl, measurementUnitKey, customAttributes, titleLocalized, scannableCodes);
        }
    }
}
