package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.Objects;

/**
 * Controls the level of optimization resources the sourcing options engine should invest.
 *
 * <p>Higher values indicate more computation time is acceptable; lower values prioritize speed.
 * Use the builder to construct instances with the required level field.
 */
public final class ResourceInvestment {

    private final Double level;

    private ResourceInvestment(Builder builder) {
        this.level = Objects.requireNonNull(builder.level, "level must not be null");
    }

    /**
     * Returns the resource investment level.
     *
     * @return the resource investment level
     */
    public Double level() { return level; }

    /**
     * Returns a new builder for constructing {@code ResourceInvestment} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code ResourceInvestment}.
     *
     * <p>The level field is required; it controls optimization resources to invest.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Double level;

        /**
         * Sets the resource investment level.
         *
         * @param level the resource investment level (required)
         * @return this builder
         */
        public Builder level(Double level) {
            this.level = level;
            return this;
        }

        /**
         * Builds the {@code ResourceInvestment}.
         *
         * @return the constructed instance
         * @throws NullPointerException if level has not been set
         */
        public ResourceInvestment build() { return new ResourceInvestment(this); }
    }
}
