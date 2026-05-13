package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration for automatic time-triggered rerouting of unfulfilled orders.
 *
 * @param active                             whether this reroute configuration is active
 * @param rerouteAfterMinutes                minutes after which an automated reroute executes
 *                                           (default 1440, minimum 5)
 * @param leadTimeBeforeTimeTriggeredReroute minutes before a time-triggered reroute that a
 *                                           notification is sent; must be less than
 *                                           {@code rerouteAfterMinutes}
 * @param rerouteTargetTimeHours             default 48
 * @param rerouteStartedJobs                 optional configuration for rerouting started jobs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RerouteConfiguration(
        Boolean active,
        Double rerouteAfterMinutes,
        Double leadTimeBeforeTimeTriggeredReroute,
        Double rerouteTargetTimeHours,
        RerouteStartedJobsConfiguration rerouteStartedJobs
) {

    /**
     * Returns a builder for constructing a {@code RerouteConfiguration}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RerouteConfiguration}.
     */
    public static final class Builder {

        private Boolean active;
        private Double rerouteAfterMinutes;
        private Double leadTimeBeforeTimeTriggeredReroute;
        private Double rerouteTargetTimeHours;
        private RerouteStartedJobsConfiguration rerouteStartedJobs;

        private Builder() {}

        /**
         * Sets whether this reroute configuration is active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the minutes after which an automated reroute executes.
         * @param rerouteAfterMinutes minutes until reroute (minimum 5, default 1440)
         * @return this builder
         */
        public Builder rerouteAfterMinutes(Double rerouteAfterMinutes) {
            this.rerouteAfterMinutes = rerouteAfterMinutes;
            return this;
        }

        /**
         * Sets the minutes before a time-triggered reroute that a notification is sent.
         * @param leadTimeBeforeTimeTriggeredReroute lead time in minutes
         * @return this builder
         */
        public Builder leadTimeBeforeTimeTriggeredReroute(Double leadTimeBeforeTimeTriggeredReroute) {
            this.leadTimeBeforeTimeTriggeredReroute = leadTimeBeforeTimeTriggeredReroute;
            return this;
        }

        /**
         * Sets the reroute target time in hours.
         * @param rerouteTargetTimeHours target time in hours (default 48)
         * @return this builder
         */
        public Builder rerouteTargetTimeHours(Double rerouteTargetTimeHours) {
            this.rerouteTargetTimeHours = rerouteTargetTimeHours;
            return this;
        }

        /**
         * Sets the configuration for rerouting started jobs.
         * @param rerouteStartedJobs the started-jobs reroute configuration
         * @return this builder
         */
        public Builder rerouteStartedJobs(RerouteStartedJobsConfiguration rerouteStartedJobs) {
            this.rerouteStartedJobs = rerouteStartedJobs;
            return this;
        }

        /**
         * Builds a {@link RerouteConfiguration}.
         *
         * @return a new instance.
         */
        public RerouteConfiguration build() {
            return new RerouteConfiguration(
                    active, rerouteAfterMinutes, leadTimeBeforeTimeTriggeredReroute,
                    rerouteTargetTimeHours, rerouteStartedJobs);
        }
    }
}
