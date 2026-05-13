package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Tolerance configuration for quantity deviations during picking.
 *
 * <p>Maps to the {@code MeasurementValidation} schema in the fulfillmenttools OpenAPI spec.
 * All tolerance values are expressed as percentages (0–100 for short-pick tolerances;
 * no upper bound for over-pick tolerances).
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param overPickHardTolerancePercentage  Allowed hard over-pick deviation (no upper bound).
 * @param overPickSoftTolerancePercentage  Allowed soft over-pick deviation (no upper bound).
 * @param shortPickHardTolerancePercentage Allowed hard short-pick deviation (0–100).
 * @param shortPickSoftTolerancePercentage Allowed soft short-pick deviation (0–100).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MeasurementValidation(
        Double overPickHardTolerancePercentage,
        Double overPickSoftTolerancePercentage,
        Double shortPickHardTolerancePercentage,
        Double shortPickSoftTolerancePercentage
) {

    /**
     * Returns a builder for constructing a {@code MeasurementValidation}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link MeasurementValidation}.
     */
    public static final class Builder {

        private Double overPickHardTolerancePercentage;
        private Double overPickSoftTolerancePercentage;
        private Double shortPickHardTolerancePercentage;
        private Double shortPickSoftTolerancePercentage;

        private Builder() {}

        /**
         * Sets the allowed hard over-pick deviation percentage.
         * @param overPickHardTolerancePercentage the hard over-pick tolerance
         * @return this builder
         */
        public Builder overPickHardTolerancePercentage(Double overPickHardTolerancePercentage) {
            this.overPickHardTolerancePercentage = overPickHardTolerancePercentage;
            return this;
        }

        /**
         * Sets the allowed soft over-pick deviation percentage.
         * @param overPickSoftTolerancePercentage the soft over-pick tolerance
         * @return this builder
         */
        public Builder overPickSoftTolerancePercentage(Double overPickSoftTolerancePercentage) {
            this.overPickSoftTolerancePercentage = overPickSoftTolerancePercentage;
            return this;
        }

        /**
         * Sets the allowed hard short-pick deviation percentage (0–100).
         * @param shortPickHardTolerancePercentage the hard short-pick tolerance
         * @return this builder
         */
        public Builder shortPickHardTolerancePercentage(Double shortPickHardTolerancePercentage) {
            this.shortPickHardTolerancePercentage = shortPickHardTolerancePercentage;
            return this;
        }

        /**
         * Sets the allowed soft short-pick deviation percentage (0–100).
         * @param shortPickSoftTolerancePercentage the soft short-pick tolerance
         * @return this builder
         */
        public Builder shortPickSoftTolerancePercentage(Double shortPickSoftTolerancePercentage) {
            this.shortPickSoftTolerancePercentage = shortPickSoftTolerancePercentage;
            return this;
        }

        /**
         * Builds a {@link MeasurementValidation}.
         *
         * @return a new instance.
         */
        public MeasurementValidation build() {
            return new MeasurementValidation(
                    overPickHardTolerancePercentage, overPickSoftTolerancePercentage,
                    shortPickHardTolerancePercentage, shortPickSoftTolerancePercentage);
        }
    }
}
