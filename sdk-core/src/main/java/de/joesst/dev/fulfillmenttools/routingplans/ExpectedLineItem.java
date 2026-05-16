package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticle;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * An expected line item on a routing plan, extending the base order line item with
 * transfer and facility context.
 *
 * <p>Maps to the {@code ExpectedLineItem} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code OrderLineItem}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          The platform-generated line item identifier.
 * @param quantity                    The ordered quantity.
 * @param article                     The article details.
 * @param measurementUnitKey          Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param secondaryQuantity           Optional secondary quantity.
 * @param scannableCodes              Optional scannable codes for the article.
 * @param allowedSubstitutes          Optional allowed substitute articles.
 * @param measurementValidation       Optional tolerance configuration for quantity deviations.
 * @param tags                        Optional categorization tags.
 * @param customAttributes            Free-form custom attributes.
 * @param facilityRef                 The facility this line item is expected at (required).
 * @param transferId                  The transfer this line item is part of (required).
 */
public record ExpectedLineItem(
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
        CustomAttributes customAttributes,
        FacilityId facilityRef,
        String transferId
) {

    public static Builder builder() {
        return new Builder();
    }

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
        private FacilityId facilityRef;
        private String transferId;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder article(OrderLineItemArticle article) { this.article = article; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) { this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey; return this; }
        public Builder secondaryQuantity(Integer secondaryQuantity) { this.secondaryQuantity = secondaryQuantity; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder allowedSubstitutes(List<Substitute> allowedSubstitutes) { this.allowedSubstitutes = allowedSubstitutes; return this; }
        public Builder measurementValidation(MeasurementValidation measurementValidation) { this.measurementValidation = measurementValidation; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder transferId(String transferId) { this.transferId = transferId; return this; }

        public ExpectedLineItem build() {
            return new ExpectedLineItem(id, quantity, article, measurementUnitKey, secondaryMeasurementUnitKey, secondaryQuantity, scannableCodes, allowedSubstitutes, measurementValidation, tags, customAttributes, facilityRef, transferId);
        }
    }
}
