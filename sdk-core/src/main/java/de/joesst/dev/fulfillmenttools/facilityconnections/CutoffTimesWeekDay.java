package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Associates a day of the week with its list of cutoff configurations.
 *
 * @param day                  the day of the week
 * @param cutoffConfigurations ordered list of cutoff points for this day
 */
public record CutoffTimesWeekDay(WeekDay day, List<CutoffConfiguration> cutoffConfigurations) {

    /**
     * Returns a builder for constructing a {@link CutoffTimesWeekDay}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CutoffTimesWeekDay}.
     */
    public static final class Builder {

        private WeekDay day;
        private List<CutoffConfiguration> cutoffConfigurations;

        private Builder() {}

        /**
         * @param day the day of the week
         * @return this builder
         */
        public Builder day(WeekDay day) {
            this.day = day;
            return this;
        }

        /**
         * @param cutoffConfigurations ordered list of cutoff points for this day
         * @return this builder
         */
        public Builder cutoffConfigurations(List<CutoffConfiguration> cutoffConfigurations) {
            this.cutoffConfigurations = cutoffConfigurations;
            return this;
        }

        /**
         * Builds a {@link CutoffTimesWeekDay}.
         *
         * @return a new instance
         */
        public CutoffTimesWeekDay build() {
            return new CutoffTimesWeekDay(day, cutoffConfigurations);
        }
    }
}
