package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Maps a set of packaging units to the delivery context in which they should be used.
 *
 * @param context        one or more context constraints that must all match for this mapping to apply
 * @param packagingUnits the packaging units available when the context matches
 */
public record PackagingUnitsByContext(List<PackagingUnitContext> context, List<PackagingUnit> packagingUnits) {

    /**
     * Returns a builder for constructing a {@link PackagingUnitsByContext}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PackagingUnitsByContext}.
     */
    public static final class Builder {

        private List<PackagingUnitContext> context;
        private List<PackagingUnit> packagingUnits;

        private Builder() {}

        /**
         * @param context one or more context constraints that must all match for this mapping to apply
         * @return this builder
         */
        public Builder context(List<PackagingUnitContext> context) {
            this.context = context;
            return this;
        }

        /**
         * @param packagingUnits the packaging units available when the context matches
         * @return this builder
         */
        public Builder packagingUnits(List<PackagingUnit> packagingUnits) {
            this.packagingUnits = packagingUnits;
            return this;
        }

        /**
         * Builds a {@link PackagingUnitsByContext}.
         *
         * @return a new instance
         */
        public PackagingUnitsByContext build() {
            return new PackagingUnitsByContext(context, packagingUnits);
        }
    }
}
