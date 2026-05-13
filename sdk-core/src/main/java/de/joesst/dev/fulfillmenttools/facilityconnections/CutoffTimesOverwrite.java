package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.time.LocalDate;
import java.util.List;

/**
 * Overrides the regular cutoff schedule for a specific calendar date.
 *
 * @param date                 the calendar date (YYYY-MM-DD) for which the override applies
 * @param cutoffConfigurations cutoff points to use on this date instead of the regular schedule
 */
public record CutoffTimesOverwrite(LocalDate date, List<CutoffConfiguration> cutoffConfigurations) {

    /**
     * Returns a builder for constructing a {@link CutoffTimesOverwrite}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CutoffTimesOverwrite}.
     */
    public static final class Builder {

        private LocalDate date;
        private List<CutoffConfiguration> cutoffConfigurations;

        private Builder() {}

        /**
         * @param date the calendar date (YYYY-MM-DD) for which the override applies
         * @return this builder
         */
        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        /**
         * @param cutoffConfigurations cutoff points to use on this date instead of the regular schedule
         * @return this builder
         */
        public Builder cutoffConfigurations(List<CutoffConfiguration> cutoffConfigurations) {
            this.cutoffConfigurations = cutoffConfigurations;
            return this;
        }

        /**
         * Builds a {@link CutoffTimesOverwrite}.
         *
         * @return a new instance
         */
        public CutoffTimesOverwrite build() {
            return new CutoffTimesOverwrite(date, cutoffConfigurations);
        }
    }
}
