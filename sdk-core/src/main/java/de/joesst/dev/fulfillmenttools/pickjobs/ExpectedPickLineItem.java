package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.Sticker;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * An expected pick line item representing an article that is anticipated to be picked
 * but has not yet been assigned to an active pick line.
 *
 * <p>Maps to the {@code ExpectedPickLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier, generated during creation.
 * @param transferId                  Reference to the transfer this item belongs to.
 * @param quantity                    Number of units to be picked (minimum 1).
 * @param article                     The article to be picked.
 * @param measurementUnitKey          Identifier for the unit of measurement.
 * @param secondaryMeasurementUnitKey Identifier for the secondary unit of measurement.
 * @param secondaryQuantity           Quantity in secondary measurement units.
 * @param scannableCodes              Codes that identify the article for scanning.
 * @param measurementValidation       Tolerance configuration for quantity deviations.
 * @param partialStockLocations       Candidate stock locations for picking.
 * @param allowedSubstitutes          Substitute articles allowed if the primary is unavailable.
 * @param stickers                    Visual stickers for display in the platform UI.
 * @param tags                        Tag references attached to this item.
 * @param customAttributes            Free-form custom attributes.
 */
public record ExpectedPickLineItem(
        String id,
        String transferId,
        Integer quantity,
        PickLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        List<String> scannableCodes,
        MeasurementValidation measurementValidation,
        List<PickJobLineItemPartialStockLocation> partialStockLocations,
        List<Substitute> allowedSubstitutes,
        List<Sticker> stickers,
        List<TagReference> tags,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String transferId;
        private Integer quantity;
        private PickLineItemArticle article;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private Integer secondaryQuantity;
        private List<String> scannableCodes;
        private MeasurementValidation measurementValidation;
        private List<PickJobLineItemPartialStockLocation> partialStockLocations;
        private List<Substitute> allowedSubstitutes;
        private List<Sticker> stickers;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder transferId(String transferId) { this.transferId = transferId; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder article(PickLineItemArticle article) { this.article = article; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) { this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey; return this; }
        public Builder secondaryQuantity(Integer secondaryQuantity) { this.secondaryQuantity = secondaryQuantity; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder measurementValidation(MeasurementValidation measurementValidation) { this.measurementValidation = measurementValidation; return this; }
        public Builder partialStockLocations(List<PickJobLineItemPartialStockLocation> partialStockLocations) { this.partialStockLocations = partialStockLocations; return this; }
        public Builder allowedSubstitutes(List<Substitute> allowedSubstitutes) { this.allowedSubstitutes = allowedSubstitutes; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public ExpectedPickLineItem build() {
            return new ExpectedPickLineItem(id, transferId, quantity, article, measurementUnitKey, secondaryMeasurementUnitKey, secondaryQuantity, scannableCodes, measurementValidation, partialStockLocations, allowedSubstitutes, stickers, tags, customAttributes);
        }
    }
}
