package de.joesst.dev.fulfillmenttools.carriers;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Physical dimensions of a parcel classification.
 *
 * <p>All fields are optional; use {@link JsonInclude} to suppress nulls in
 * serialisation so that absent dimensions are not sent to the API.
 *
 * <p>Thread-safety: records are immutable and therefore unconditionally
 * thread-safe.
 *
 * @param customWeight carrier-specific weight override in grams
 * @param height       parcel height in centimetres
 * @param length       parcel length in centimetres
 * @param weight       parcel weight in grams
 * @param width        parcel width in centimetres
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParcelDimensions(
        Double customWeight,
        Double height,
        Double length,
        Double weight,
        Double width
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Double customWeight;
        private Double height;
        private Double length;
        private Double weight;
        private Double width;

        public Builder customWeight(Double customWeight) { this.customWeight = customWeight; return this; }
        public Builder height(Double height) { this.height = height; return this; }
        public Builder length(Double length) { this.length = length; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder width(Double width) { this.width = width; return this; }

        public ParcelDimensions build() {
            return new ParcelDimensions(customWeight, height, length, weight, width);
        }
    }
}
