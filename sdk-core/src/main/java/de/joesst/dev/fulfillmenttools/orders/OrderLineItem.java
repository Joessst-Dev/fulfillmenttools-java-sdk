package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;

/**
 * A read-side order line item containing article and fulfillment information.
 *
 * <p>Maps to the {@code OrderLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          The platform-generated line item identifier.
 * @param quantity                    The ordered quantity.
 * @param article                     The article details.
 * @param measurementUnitKey          Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param secondaryQuantity           Optional secondary quantity.
 * @param scannableCodes              Optional list of scannable codes for the article.
 * @param allowedSubstitutes          Optional list of allowed substitute articles.
 * @param measurementValidation       Optional tolerance configuration for quantity deviations.
 * @param tags                        Optional tags for categorization.
 * @param customAttributes            Free-form custom attributes.
 */
public record OrderLineItem(
        String id,
        Integer quantity,
        OrderLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        List<String> scannableCodes,
        List<Substitute> allowedSubstitutes,
        MeasurementValidation measurementValidation,
        List<TagReference> tags,
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing an {@code OrderLineItem}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OrderLineItem}.
     */
    public static final class Builder {

        private String id;
        private Integer quantity;
        private OrderLineItemArticle article;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private Integer secondaryQuantity;
        private List<String> scannableCodes;
        private List<Substitute> allowedSubstitutes;
        private MeasurementValidation measurementValidation;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * Sets the platform-generated line item identifier.
         * @param id the line item identifier
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the ordered quantity.
         * @param quantity the quantity
         * @return this builder
         */
        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the article details.
         * @param article the article
         * @return this builder
         */
        public Builder article(OrderLineItemArticle article) {
            this.article = article;
            return this;
        }

        /**
         * Sets the unit of measurement identifier.
         * @param measurementUnitKey the measurement unit key
         * @return this builder
         */
        public Builder measurementUnitKey(String measurementUnitKey) {
            this.measurementUnitKey = measurementUnitKey;
            return this;
        }

        /**
         * Sets the secondary unit of measurement identifier.
         * @param secondaryMeasurementUnitKey the secondary measurement unit key
         * @return this builder
         */
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) {
            this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey;
            return this;
        }

        /**
         * Sets the secondary quantity.
         * @param secondaryQuantity the secondary quantity
         * @return this builder
         */
        public Builder secondaryQuantity(Integer secondaryQuantity) {
            this.secondaryQuantity = secondaryQuantity;
            return this;
        }

        /**
         * Sets the list of scannable codes for the article.
         * @param scannableCodes the scannable codes
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        /**
         * Sets the list of allowed substitute articles.
         * @param allowedSubstitutes the allowed substitutes
         * @return this builder
         */
        public Builder allowedSubstitutes(List<Substitute> allowedSubstitutes) {
            this.allowedSubstitutes = allowedSubstitutes;
            return this;
        }

        /**
         * Sets the tolerance configuration for quantity deviations.
         * @param measurementValidation the measurement validation config
         * @return this builder
         */
        public Builder measurementValidation(MeasurementValidation measurementValidation) {
            this.measurementValidation = measurementValidation;
            return this;
        }

        /**
         * Sets tags for categorization.
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
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
         * Builds an {@link OrderLineItem}.
         *
         * @return a new instance.
         */
        public OrderLineItem build() {
            return new OrderLineItem(
                    id, quantity, article, measurementUnitKey, secondaryMeasurementUnitKey,
                    secondaryQuantity, scannableCodes, allowedSubstitutes,
                    measurementValidation, tags, customAttributes);
        }
    }
}
