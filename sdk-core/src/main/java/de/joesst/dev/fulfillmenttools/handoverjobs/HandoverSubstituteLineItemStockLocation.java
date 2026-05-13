package de.joesst.dev.fulfillmenttools.handoverjobs;

/**
 * A partial stock location contributing articles for a substitute line item in a handover job.
 *
 * <p>Maps to the {@code SubstituteLineItemPartialStockLocation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantPartialStockId Tenant-specific identifier for this partial stock entry.
 * @param stockRef             Reference to the stock entry.
 * @param quantity             Number of units to be picked from this location.
 * @param picked               Number of units that have been picked from this location.
 * @param ratingScore          Optional rating score used for location selection.
 * @param sequenceScore        Optional sequence score for routing optimization.
 * @param locationRef          The id of the physical storage location.
 */
public record HandoverSubstituteLineItemStockLocation(
        String tenantPartialStockId,
        String stockRef,
        Double quantity,
        Double picked,
        Double ratingScore,
        Double sequenceScore,
        String locationRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String tenantPartialStockId;
        private String stockRef;
        private Double quantity;
        private Double picked;
        private Double ratingScore;
        private Double sequenceScore;
        private String locationRef;

        private Builder() {}

        public Builder tenantPartialStockId(String tenantPartialStockId) { this.tenantPartialStockId = tenantPartialStockId; return this; }
        public Builder stockRef(String stockRef) { this.stockRef = stockRef; return this; }
        public Builder quantity(Double quantity) { this.quantity = quantity; return this; }
        public Builder picked(Double picked) { this.picked = picked; return this; }
        public Builder ratingScore(Double ratingScore) { this.ratingScore = ratingScore; return this; }
        public Builder sequenceScore(Double sequenceScore) { this.sequenceScore = sequenceScore; return this; }
        public Builder locationRef(String locationRef) { this.locationRef = locationRef; return this; }

        public HandoverSubstituteLineItemStockLocation build() {
            return new HandoverSubstituteLineItemStockLocation(tenantPartialStockId, stockRef, quantity, picked, ratingScore, sequenceScore, locationRef);
        }
    }
}
