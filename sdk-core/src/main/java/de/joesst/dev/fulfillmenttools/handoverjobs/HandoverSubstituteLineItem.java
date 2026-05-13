package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.time.Instant;
import java.util.List;

/**
 * A substitute line item within a handover job, representing an alternative article
 * offered when the original item cannot be handed over.
 *
 * <p>Maps to the {@code HandoverSubstituteLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Unique identifier of this substitute line item. Required.
 * @param quantity             Number of units provided for handover. Required; minimum 1.
 * @param handedOverQuantity   Number of units that have actually been handed over; minimum 0.
 * @param priority             Ranking among substitutes; lower value means more preferred.
 * @param pickedAt             Timestamp when this line was picked.
 * @param article              The substitute article. Required.
 * @param partialStockLocations Stock locations from which articles were sourced.
 * @param refused              List of refusal records for units not accepted.
 * @param scannableCodes       Scannable codes identifying the substitute article.
 */
public record HandoverSubstituteLineItem(
        String id,
        Integer quantity,
        Integer handedOverQuantity,
        Double priority,
        Instant pickedAt,
        HandoverSubstituteLineItemArticle article,
        List<HandoverSubstituteLineItemStockLocation> partialStockLocations,
        List<HandoverRefusedItem> refused,
        List<String> scannableCodes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private Integer quantity;
        private Integer handedOverQuantity;
        private Double priority;
        private Instant pickedAt;
        private HandoverSubstituteLineItemArticle article;
        private List<HandoverSubstituteLineItemStockLocation> partialStockLocations;
        private List<HandoverRefusedItem> refused;
        private List<String> scannableCodes;

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder handedOverQuantity(Integer handedOverQuantity) { this.handedOverQuantity = handedOverQuantity; return this; }
        public Builder priority(Double priority) { this.priority = priority; return this; }
        public Builder pickedAt(Instant pickedAt) { this.pickedAt = pickedAt; return this; }
        public Builder article(HandoverSubstituteLineItemArticle article) { this.article = article; return this; }
        public Builder partialStockLocations(List<HandoverSubstituteLineItemStockLocation> partialStockLocations) { this.partialStockLocations = partialStockLocations; return this; }
        public Builder refused(List<HandoverRefusedItem> refused) { this.refused = refused; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        public HandoverSubstituteLineItem build() {
            return new HandoverSubstituteLineItem(id, quantity, handedOverQuantity, priority, pickedAt, article, partialStockLocations, refused, scannableCodes);
        }
    }
}
