package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A time of day represented as hour and minute components.
 *
 * @param hour hour value (0-23)
 * @param minute minute value (0-59)
 */
public record TimeStamp(Integer hour, Integer minute) {

    /**
     * Returns a builder for constructing a {@link TimeStamp}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link TimeStamp}.
     */
    public static final class Builder {

        private Integer hour;
        private Integer minute;

        private Builder() {}

        /**
         * @param hour hour value (0-23)
         * @return this builder
         */
        public Builder hour(Integer hour) {
            this.hour = hour;
            return this;
        }

        /**
         * @param minute minute value (0-59)
         * @return this builder
         */
        public Builder minute(Integer minute) {
            this.minute = minute;
            return this;
        }

        /**
         * Builds a {@link TimeStamp}.
         *
         * @return a new instance
         */
        public TimeStamp build() {
            return new TimeStamp(hour, minute);
        }
    }
}
