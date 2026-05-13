package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.List;

/**
 * An allowed substitute article for an order line item.
 *
 * <p>Maps to the {@code Substitute} schema in the fulfillmenttools OpenAPI spec.
 * If the original article cannot be picked, the picker may select one of the configured
 * substitutes. Substitutes are ranked by {@code priority} (lower = more preferred).
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId           The article identifier in the tenant system.
 * @param title                     The display title of the substitute article.
 * @param imageUrl                  Optional URL of the product image.
 * @param priority                  Rank among substitutes; lower value means more preferred.
 * @param measurementUnitKey        Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param scannableCodes            Optional list of scannable codes identifying this article.
 * @param measurementValidation     Optional pick-quantity tolerance configuration.
 * @param attributes                Optional article attributes for display/customization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Substitute(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double priority,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        List<String> scannableCodes,
        MeasurementValidation measurementValidation,
        List<ArticleAttribute> attributes
) {

    /**
     * Returns a builder for constructing a {@code Substitute}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Substitute}.
     */
    public static final class Builder {

        private TenantArticleId tenantArticleId;
        private String title;
        private String imageUrl;
        private Double priority;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private List<String> scannableCodes;
        private MeasurementValidation measurementValidation;
        private List<ArticleAttribute> attributes;

        private Builder() {}

        /**
         * Sets the article identifier in the tenant system.
         * @param tenantArticleId the tenant article identifier
         * @return this builder
         */
        public Builder tenantArticleId(TenantArticleId tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        /**
         * Sets the display title of the substitute article.
         * @param title the display title
         * @return this builder
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the optional URL of the product image.
         * @param imageUrl the image URL
         * @return this builder
         */
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * Sets the rank among substitutes; lower value means more preferred.
         * @param priority the priority value
         * @return this builder
         */
        public Builder priority(Double priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Sets the optional unit of measurement identifier.
         * @param measurementUnitKey the measurement unit key
         * @return this builder
         */
        public Builder measurementUnitKey(String measurementUnitKey) {
            this.measurementUnitKey = measurementUnitKey;
            return this;
        }

        /**
         * Sets the optional secondary unit of measurement identifier.
         * @param secondaryMeasurementUnitKey the secondary measurement unit key
         * @return this builder
         */
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) {
            this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey;
            return this;
        }

        /**
         * Sets the optional list of scannable codes identifying this article.
         * @param scannableCodes the scannable codes
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        /**
         * Sets the optional pick-quantity tolerance configuration.
         * @param measurementValidation the measurement validation config
         * @return this builder
         */
        public Builder measurementValidation(MeasurementValidation measurementValidation) {
            this.measurementValidation = measurementValidation;
            return this;
        }

        /**
         * Sets the optional article attributes for display and customization.
         * @param attributes the article attributes
         * @return this builder
         */
        public Builder attributes(List<ArticleAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Builds a {@link Substitute}.
         *
         * @return a new instance.
         */
        public Substitute build() {
            return new Substitute(
                    tenantArticleId, title, imageUrl, priority,
                    measurementUnitKey, secondaryMeasurementUnitKey,
                    scannableCodes, measurementValidation, attributes);
        }
    }
}
