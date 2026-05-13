package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration that controls whether rerouting applies to already-started pick jobs.
 *
 * @param active                       whether rerouting started jobs is enabled (required)
 * @param minimumStartedTimeInMinutes  minimum time (in minutes) a job must be in a started status
 *                                     before it is eligible for rerouting (minimum 60)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RerouteStartedJobsConfiguration(
        Boolean active,
        Double minimumStartedTimeInMinutes
) {

    /**
     * Returns a builder for constructing a {@code RerouteStartedJobsConfiguration}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RerouteStartedJobsConfiguration}.
     */
    public static final class Builder {

        private Boolean active;
        private Double minimumStartedTimeInMinutes;

        private Builder() {}

        /**
         * Sets whether rerouting started jobs is enabled.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the minimum time in minutes a job must be in a started status before rerouting.
         * @param minimumStartedTimeInMinutes minimum started time (minimum 60)
         * @return this builder
         */
        public Builder minimumStartedTimeInMinutes(Double minimumStartedTimeInMinutes) {
            this.minimumStartedTimeInMinutes = minimumStartedTimeInMinutes;
            return this;
        }

        /**
         * Builds a {@link RerouteStartedJobsConfiguration}.
         *
         * @return a new instance.
         */
        public RerouteStartedJobsConfiguration build() {
            return new RerouteStartedJobsConfiguration(active, minimumStartedTimeInMinutes);
        }
    }
}
