package de.joesst.dev.fulfillmenttools.facilities;

import java.time.Instant;

/**
 * Represents a period when a facility is closed and unavailable for fulfillment operations.
 *
 * @param date the date or start date of the closing period
 * @param reason human-readable reason for the closure
 * @param recurrence optional recurrence pattern (e.g. "WEEKLY", "MONTHLY", "YEARLY")
 */
public record ClosingDay(Instant date, String reason, String recurrence) {

    /**
     * Returns a builder for constructing a {@link ClosingDay}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ClosingDay}.
     */
    public static final class Builder {

        private Instant date;
        private String reason;
        private String recurrence;

        private Builder() {}

        /**
         * @param date the date or start date of the closing period
         * @return this builder
         */
        public Builder date(Instant date) {
            this.date = date;
            return this;
        }

        /**
         * @param reason human-readable reason for the closure
         * @return this builder
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * @param recurrence optional recurrence pattern (e.g. "WEEKLY", "MONTHLY", "YEARLY")
         * @return this builder
         */
        public Builder recurrence(String recurrence) {
            this.recurrence = recurrence;
            return this;
        }

        /**
         * Builds a {@link ClosingDay}.
         *
         * @return a new instance
         */
        public ClosingDay build() {
            return new ClosingDay(date, reason, recurrence);
        }
    }
}
