package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A packaging unit configuration used in a transfer, describing the physical container
 * and its associated costs.
 *
 * <p>Maps to the {@code SourcingOptionsTransferPackagingUnit} schema in the fulfillmenttools
 * OpenAPI specification.
 *
 * @param name                  the packaging unit name (e.g. {@code Small Box})
 * @param priority              the priority; lower values are preferred
 * @param packagingUnitRef      reference to the packaging unit configuration
 * @param tenantPackagingUnitId the tenant-specific packaging unit identifier
 * @param maxItemQuantity       maximum number of items that can be packed
 * @param volumeBufferInPercent volume buffer percentage
 * @param dimensions            physical dimensions
 * @param transitTime           transit time bounds for this packaging unit
 * @param prices                delivery cost entries for this packaging unit
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsTransferPackagingUnit(
        String name,
        Integer priority,
        String packagingUnitRef,
        String tenantPackagingUnitId,
        Integer maxItemQuantity,
        Double volumeBufferInPercent,
        SourcingOptionsTransferPackagingUnitDimensions dimensions,
        SourcingOptionsTransferTransitTime transitTime,
        List<SourcingOptionsTransferDeliveryCost> prices
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String name;
        private Integer priority;
        private String packagingUnitRef;
        private String tenantPackagingUnitId;
        private Integer maxItemQuantity;
        private Double volumeBufferInPercent;
        private SourcingOptionsTransferPackagingUnitDimensions dimensions;
        private SourcingOptionsTransferTransitTime transitTime;
        private List<SourcingOptionsTransferDeliveryCost> prices;

        public Builder name(String name) { this.name = name; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder packagingUnitRef(String packagingUnitRef) { this.packagingUnitRef = packagingUnitRef; return this; }
        public Builder tenantPackagingUnitId(String tenantPackagingUnitId) { this.tenantPackagingUnitId = tenantPackagingUnitId; return this; }
        public Builder maxItemQuantity(Integer maxItemQuantity) { this.maxItemQuantity = maxItemQuantity; return this; }
        public Builder volumeBufferInPercent(Double volumeBufferInPercent) { this.volumeBufferInPercent = volumeBufferInPercent; return this; }
        public Builder dimensions(SourcingOptionsTransferPackagingUnitDimensions dimensions) { this.dimensions = dimensions; return this; }
        public Builder transitTime(SourcingOptionsTransferTransitTime transitTime) { this.transitTime = transitTime; return this; }
        public Builder prices(List<SourcingOptionsTransferDeliveryCost> prices) { this.prices = prices; return this; }

        public SourcingOptionsTransferPackagingUnit build() {
            return new SourcingOptionsTransferPackagingUnit(name, priority, packagingUnitRef, tenantPackagingUnitId, maxItemQuantity, volumeBufferInPercent, dimensions, transitTime, prices);
        }
    }
}
