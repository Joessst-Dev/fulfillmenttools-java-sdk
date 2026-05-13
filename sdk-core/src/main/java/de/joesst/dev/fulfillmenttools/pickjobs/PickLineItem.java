package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.Sticker;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A single pick line item within a pick job, representing a quantity of a specific article
 * to be picked.
 *
 * <p>Maps to the {@code PickLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier of this pick line item.
 * @param quantity                    Number of units that have been ordered.
 * @param picked                      Number of units that have been picked.
 * @param status                      Current status of the pick line item.
 * @param article                     The article to be picked.
 * @param measurementUnitKey          Identifier for the primary unit of measurement.
 * @param secondaryMeasurementUnitKey Identifier for the secondary unit of measurement.
 * @param secondaryQuantity           Quantity in the secondary unit of measurement.
 * @param secondaryPicked             Picked quantity in the secondary unit of measurement.
 * @param globalLineItemId            Cross-entity line item identifier.
 * @param originId                    Id of the originating line item before any split.
 * @param pickJobLineItemRef          Reference used for line items in pick runs.
 * @param pickedAt                    Timestamp when this line was picked.
 * @param stockEmptied                Whether the stock at the pick location was depleted.
 * @param scannableCodes              Codes that can be scanned to identify the article.
 * @param partialStockLocations       Stock locations from which the article should be picked.
 * @param recordableAttributes        Customizable attributes that can be recorded during picking.
 * @param measurementValidation       Tolerance configuration for quantity deviations.
 * @param shortPickReason             Reason provided when fewer units were picked than requested.
 * @param allowedSubstitutes          Articles that may substitute the primary if unavailable.
 * @param stickers                    Visual stickers attached to this line item.
 * @param tags                        Tag references attached to this line item.
 * @param customAttributes            Free-form custom attributes.
 */
public record PickLineItem(
        String id,
        Integer quantity,
        Integer picked,
        String status,
        PickLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        Integer secondaryPicked,
        String globalLineItemId,
        String originId,
        String pickJobLineItemRef,
        Instant pickedAt,
        Boolean stockEmptied,
        List<String> scannableCodes,
        List<PickJobLineItemPartialStockLocation> partialStockLocations,
        List<RecordableAttribute> recordableAttributes,
        MeasurementValidation measurementValidation,
        PickLineShortPickReason shortPickReason,
        List<Substitute> allowedSubstitutes,
        List<Sticker> stickers,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Integer quantity;
        private Integer picked;
        private String status;
        private PickLineItemArticle article;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private Integer secondaryQuantity;
        private Integer secondaryPicked;
        private String globalLineItemId;
        private String originId;
        private String pickJobLineItemRef;
        private Instant pickedAt;
        private Boolean stockEmptied;
        private List<String> scannableCodes;
        private List<PickJobLineItemPartialStockLocation> partialStockLocations;
        private List<RecordableAttribute> recordableAttributes;
        private MeasurementValidation measurementValidation;
        private PickLineShortPickReason shortPickReason;
        private List<Substitute> allowedSubstitutes;
        private List<Sticker> stickers;
        private List<TagReference> tags;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder picked(Integer picked) { this.picked = picked; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder article(PickLineItemArticle article) { this.article = article; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) { this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey; return this; }
        public Builder secondaryQuantity(Integer secondaryQuantity) { this.secondaryQuantity = secondaryQuantity; return this; }
        public Builder secondaryPicked(Integer secondaryPicked) { this.secondaryPicked = secondaryPicked; return this; }
        public Builder globalLineItemId(String globalLineItemId) { this.globalLineItemId = globalLineItemId; return this; }
        public Builder originId(String originId) { this.originId = originId; return this; }
        public Builder pickJobLineItemRef(String pickJobLineItemRef) { this.pickJobLineItemRef = pickJobLineItemRef; return this; }
        public Builder pickedAt(Instant pickedAt) { this.pickedAt = pickedAt; return this; }
        public Builder stockEmptied(Boolean stockEmptied) { this.stockEmptied = stockEmptied; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder partialStockLocations(List<PickJobLineItemPartialStockLocation> partialStockLocations) { this.partialStockLocations = partialStockLocations; return this; }
        public Builder recordableAttributes(List<RecordableAttribute> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }
        public Builder measurementValidation(MeasurementValidation measurementValidation) { this.measurementValidation = measurementValidation; return this; }
        public Builder shortPickReason(PickLineShortPickReason shortPickReason) { this.shortPickReason = shortPickReason; return this; }
        public Builder allowedSubstitutes(List<Substitute> allowedSubstitutes) { this.allowedSubstitutes = allowedSubstitutes; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public PickLineItem build() {
            return new PickLineItem(id, quantity, picked, status, article, measurementUnitKey, secondaryMeasurementUnitKey, secondaryQuantity, secondaryPicked, globalLineItemId, originId, pickJobLineItemRef, pickedAt, stockEmptied, scannableCodes, partialStockLocations, recordableAttributes, measurementValidation, shortPickReason, allowedSubstitutes, stickers, tags, customAttributes);
        }
    }
}
