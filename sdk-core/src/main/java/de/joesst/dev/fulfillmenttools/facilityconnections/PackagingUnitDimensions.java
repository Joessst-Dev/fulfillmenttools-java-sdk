package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Physical dimensions and weight limit for a packaging unit.
 *
 * @param length          length in centimetres
 * @param width           width in centimetres
 * @param height          height in centimetres
 * @param maxWeightInGram maximum allowed weight in grams
 */
public record PackagingUnitDimensions(Double length, Double width, Double height, Double maxWeightInGram) {

    /**
     * Returns a builder for constructing a {@link PackagingUnitDimensions}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PackagingUnitDimensions}.
     */
    public static final class Builder {

        private Double length;
        private Double width;
        private Double height;
        private Double maxWeightInGram;

        private Builder() {}

        /**
         * @param length length in centimetres
         * @return this builder
         */
        public Builder length(Double length) {
            this.length = length;
            return this;
        }

        /**
         * @param width width in centimetres
         * @return this builder
         */
        public Builder width(Double width) {
            this.width = width;
            return this;
        }

        /**
         * @param height height in centimetres
         * @return this builder
         */
        public Builder height(Double height) {
            this.height = height;
            return this;
        }

        /**
         * @param maxWeightInGram maximum allowed weight in grams
         * @return this builder
         */
        public Builder maxWeightInGram(Double maxWeightInGram) {
            this.maxWeightInGram = maxWeightInGram;
            return this;
        }

        /**
         * Builds a {@link PackagingUnitDimensions}.
         *
         * @return a new instance
         */
        public PackagingUnitDimensions build() {
            return new PackagingUnitDimensions(length, width, height, maxWeightInGram);
        }
    }
}
