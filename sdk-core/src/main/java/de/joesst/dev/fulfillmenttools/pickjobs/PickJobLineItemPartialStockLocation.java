package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * Describes a partial stock location from which articles can be picked for a pick line item.
 *
 * <p>Maps to the {@code PickJobLineItemPartialStockLocation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantPartialStockId Tenant-specific identifier for this partial stock entry.
 * @param stockRef             Reference to the stock entry.
 * @param available            Number of units available at this location.
 * @param quantity             Number of units that should be picked from this location.
 * @param picked               Number of units that have been picked from this location.
 * @param ratingScore          Optional rating score used for location selection.
 * @param sequenceScore        Optional sequence score for routing optimization.
 * @param stockEmptied         Whether the stock at this location was fully depleted.
 * @param location             The physical location details.
 * @param stockProperties      Optional additional stock properties (free-form string map).
 * @param zoneName             Name of the zone this location belongs to.
 * @param zoneRef              Reference to the zone this location belongs to.
 */
public record PickJobLineItemPartialStockLocation(
        String tenantPartialStockId,
        String stockRef,
        Integer available,
        Integer quantity,
        Integer picked,
        Double ratingScore,
        Double sequenceScore,
        Boolean stockEmptied,
        PickJobStockLocation location,
        Map<String, String> stockProperties,
        String zoneName,
        String zoneRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String tenantPartialStockId;
        private String stockRef;
        private Integer available;
        private Integer quantity;
        private Integer picked;
        private Double ratingScore;
        private Double sequenceScore;
        private Boolean stockEmptied;
        private PickJobStockLocation location;
        private Map<String, String> stockProperties;
        private String zoneName;
        private String zoneRef;

        private Builder() {}

        public Builder tenantPartialStockId(String tenantPartialStockId) { this.tenantPartialStockId = tenantPartialStockId; return this; }
        public Builder stockRef(String stockRef) { this.stockRef = stockRef; return this; }
        public Builder available(Integer available) { this.available = available; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder picked(Integer picked) { this.picked = picked; return this; }
        public Builder ratingScore(Double ratingScore) { this.ratingScore = ratingScore; return this; }
        public Builder sequenceScore(Double sequenceScore) { this.sequenceScore = sequenceScore; return this; }
        public Builder stockEmptied(Boolean stockEmptied) { this.stockEmptied = stockEmptied; return this; }
        public Builder location(PickJobStockLocation location) { this.location = location; return this; }
        public Builder stockProperties(Map<String, String> stockProperties) { this.stockProperties = stockProperties; return this; }
        public Builder zoneName(String zoneName) { this.zoneName = zoneName; return this; }
        public Builder zoneRef(String zoneRef) { this.zoneRef = zoneRef; return this; }

        public PickJobLineItemPartialStockLocation build() {
            return new PickJobLineItemPartialStockLocation(tenantPartialStockId, stockRef, available, quantity, picked, ratingScore, sequenceScore, stockEmptied, location, stockProperties, zoneName, zoneRef);
        }
    }
}
