package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A single cutoff point within a day, optionally bounded by a capacity limit.
 *
 * @param time     cutoff time in HH:mm format
 * @param capacity optional maximum order capacity for this cutoff window; {@code null} means unlimited
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CutoffConfiguration(String time, Double capacity) {

    /**
     * Returns a builder for constructing a {@link CutoffConfiguration}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CutoffConfiguration}.
     */
    public static final class Builder {

        private String time;
        private Double capacity;

        private Builder() {}

        /**
         * @param time cutoff time in HH:mm format
         * @return this builder
         */
        public Builder time(String time) {
            this.time = time;
            return this;
        }

        /**
         * @param capacity optional maximum order capacity for this cutoff window
         * @return this builder
         */
        public Builder capacity(Double capacity) {
            this.capacity = capacity;
            return this;
        }

        /**
         * Builds a {@link CutoffConfiguration}.
         *
         * @return a new instance
         */
        public CutoffConfiguration build() {
            return new CutoffConfiguration(time, capacity);
        }
    }
}
