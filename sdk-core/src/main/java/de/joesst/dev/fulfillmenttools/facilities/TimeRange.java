package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A time interval with an optional capacity limit.
 *
 * @param start start time of the range
 * @param end end time of the range
 * @param capacity optional capacity limit or utilization for this time range
 */
public record TimeRange(TimeStamp start, TimeStamp end, Double capacity) {

    /**
     * Returns a builder for constructing a {@link TimeRange}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link TimeRange}.
     */
    public static final class Builder {

        private TimeStamp start;
        private TimeStamp end;
        private Double capacity;

        private Builder() {}

        /**
         * @param start start time of the range
         * @return this builder
         */
        public Builder start(TimeStamp start) {
            this.start = start;
            return this;
        }

        /**
         * @param end end time of the range
         * @return this builder
         */
        public Builder end(TimeStamp end) {
            this.end = end;
            return this;
        }

        /**
         * @param capacity optional capacity limit or utilization for this time range
         * @return this builder
         */
        public Builder capacity(Double capacity) {
            this.capacity = capacity;
            return this;
        }

        /**
         * Builds a {@link TimeRange}.
         *
         * @return a new instance
         */
        public TimeRange build() {
            return new TimeRange(start, end, capacity);
        }
    }
}
