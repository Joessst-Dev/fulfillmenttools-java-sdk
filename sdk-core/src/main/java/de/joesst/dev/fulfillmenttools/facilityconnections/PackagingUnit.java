package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Describes a single packaging unit (box, envelope, pallet, etc.) that can be used
 * by a carrier connection.
 *
 * @param name                  human-readable name of this packaging unit; required
 * @param priority              selection priority — lower values are preferred; required
 * @param packagingUnitRef      optional carrier-side reference identifier
 * @param tenantPackagingUnitId optional tenant-defined identifier
 * @param maxItemQuantity       optional maximum number of items that fit in this unit
 * @param dimensions            optional physical dimensions and weight limit
 * @param prices                optional list of delivery costs associated with this unit
 * @param transitTime           optional carrier transit time for shipments using this unit
 * @param volumeBufferInPercent optional percentage of volume reserved as a buffer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackagingUnit(
        String name,
        Integer priority,
        String packagingUnitRef,
        String tenantPackagingUnitId,
        Integer maxItemQuantity,
        PackagingUnitDimensions dimensions,
        List<DeliveryCost> prices,
        CarrierTransitTime transitTime,
        Integer volumeBufferInPercent
) {

    /**
     * Returns a builder for constructing a {@link PackagingUnit}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PackagingUnit}.
     */
    public static final class Builder {

        private String name;
        private Integer priority;
        private String packagingUnitRef;
        private String tenantPackagingUnitId;
        private Integer maxItemQuantity;
        private PackagingUnitDimensions dimensions;
        private List<DeliveryCost> prices;
        private CarrierTransitTime transitTime;
        private Integer volumeBufferInPercent;

        private Builder() {}

        /**
         * @param name human-readable name of this packaging unit
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @param priority selection priority — lower values are preferred
         * @return this builder
         */
        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        /**
         * @param packagingUnitRef optional carrier-side reference identifier
         * @return this builder
         */
        public Builder packagingUnitRef(String packagingUnitRef) {
            this.packagingUnitRef = packagingUnitRef;
            return this;
        }

        /**
         * @param tenantPackagingUnitId optional tenant-defined identifier
         * @return this builder
         */
        public Builder tenantPackagingUnitId(String tenantPackagingUnitId) {
            this.tenantPackagingUnitId = tenantPackagingUnitId;
            return this;
        }

        /**
         * @param maxItemQuantity optional maximum number of items that fit in this unit
         * @return this builder
         */
        public Builder maxItemQuantity(Integer maxItemQuantity) {
            this.maxItemQuantity = maxItemQuantity;
            return this;
        }

        /**
         * @param dimensions optional physical dimensions and weight limit
         * @return this builder
         */
        public Builder dimensions(PackagingUnitDimensions dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        /**
         * @param prices optional list of delivery costs associated with this unit
         * @return this builder
         */
        public Builder prices(List<DeliveryCost> prices) {
            this.prices = prices;
            return this;
        }

        /**
         * @param transitTime optional carrier transit time for shipments using this unit
         * @return this builder
         */
        public Builder transitTime(CarrierTransitTime transitTime) {
            this.transitTime = transitTime;
            return this;
        }

        /**
         * @param volumeBufferInPercent optional percentage of volume reserved as a buffer
         * @return this builder
         */
        public Builder volumeBufferInPercent(Integer volumeBufferInPercent) {
            this.volumeBufferInPercent = volumeBufferInPercent;
            return this;
        }

        /**
         * Builds a {@link PackagingUnit}.
         *
         * @return a new instance
         */
        public PackagingUnit build() {
            return new PackagingUnit(
                    name, priority, packagingUnitRef, tenantPackagingUnitId, maxItemQuantity,
                    dimensions, prices, transitTime, volumeBufferInPercent);
        }
    }
}
