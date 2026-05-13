package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Complete cutoff schedule for a facility connection, combining regular weekday patterns
 * with date-specific overrides.
 *
 * @param weekdays  per-weekday cutoff configurations
 * @param overwrites date-specific overrides that take precedence over the weekday schedule
 */
public record CutoffTimes(List<CutoffTimesWeekDay> weekdays, List<CutoffTimesOverwrite> overwrites) {

    /**
     * Returns a builder for constructing a {@link CutoffTimes}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CutoffTimes}.
     */
    public static final class Builder {

        private List<CutoffTimesWeekDay> weekdays;
        private List<CutoffTimesOverwrite> overwrites;

        private Builder() {}

        /**
         * @param weekdays per-weekday cutoff configurations
         * @return this builder
         */
        public Builder weekdays(List<CutoffTimesWeekDay> weekdays) {
            this.weekdays = weekdays;
            return this;
        }

        /**
         * @param overwrites date-specific overrides that take precedence over the weekday schedule
         * @return this builder
         */
        public Builder overwrites(List<CutoffTimesOverwrite> overwrites) {
            this.overwrites = overwrites;
            return this;
        }

        /**
         * Builds a {@link CutoffTimes}.
         *
         * @return a new instance
         */
        public CutoffTimes build() {
            return new CutoffTimes(weekdays, overwrites);
        }
    }
}
