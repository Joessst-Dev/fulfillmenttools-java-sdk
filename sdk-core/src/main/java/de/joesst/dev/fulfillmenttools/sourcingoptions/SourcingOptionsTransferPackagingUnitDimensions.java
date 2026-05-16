package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Physical dimensions of a packaging unit.
 *
 * <p>Maps to the {@code SourcingOptionsTransferPackagingUnitDimensions} schema in the
 * fulfillmenttools OpenAPI specification.
 *
 * @param height          height in configured unit
 * @param length          length in configured unit
 * @param width           width in configured unit
 * @param maxWeightInGram maximum allowed weight in grams
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsTransferPackagingUnitDimensions(
        Double height,
        Double length,
        Double width,
        Double maxWeightInGram
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Double height;
        private Double length;
        private Double width;
        private Double maxWeightInGram;

        public Builder height(Double height) { this.height = height; return this; }
        public Builder length(Double length) { this.length = length; return this; }
        public Builder width(Double width) { this.width = width; return this; }
        public Builder maxWeightInGram(Double maxWeightInGram) { this.maxWeightInGram = maxWeightInGram; return this; }

        public SourcingOptionsTransferPackagingUnitDimensions build() {
            return new SourcingOptionsTransferPackagingUnitDimensions(height, length, width, maxWeightInGram);
        }
    }
}
