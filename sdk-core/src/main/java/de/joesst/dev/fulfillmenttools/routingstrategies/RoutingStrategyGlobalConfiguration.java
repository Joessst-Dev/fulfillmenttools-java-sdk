package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Global configuration for a routing strategy that applies across all nodes.
 *
 * @param defaultPrice                    the price applied when no specific price is defined in
 *                                        the order or listing (required, default 10)
 * @param blacklistAssignedFacilities     if {@code true}, facilities already assigned to the order
 *                                        in a previous routing attempt are excluded from the next
 * @param restowAfterMinutes              minutes after which a restow is executed; if absent,
 *                                        restows are not executed
 * @param stopRoutingAttemptsAfterTime    ISO 8601 duration after which a routing plan is
 *                                        considered not routable (default {@code PT8H})
 * @param timeTriggered                   optional time-triggered reroute configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyGlobalConfiguration(
        Double defaultPrice,
        Boolean blacklistAssignedFacilities,
        Double restowAfterMinutes,
        String stopRoutingAttemptsAfterTime,
        RoutingStrategyRerouteTimeTriggeredConfig timeTriggered
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyGlobalConfiguration}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyGlobalConfiguration}.
     */
    public static final class Builder {

        private Double defaultPrice;
        private Boolean blacklistAssignedFacilities;
        private Double restowAfterMinutes;
        private String stopRoutingAttemptsAfterTime;
        private RoutingStrategyRerouteTimeTriggeredConfig timeTriggered;

        private Builder() {}

        /**
         * Sets the price applied when no specific price is defined in the order or listing.
         * @param defaultPrice the default price (default 10)
         * @return this builder
         */
        public Builder defaultPrice(Double defaultPrice) {
            this.defaultPrice = defaultPrice;
            return this;
        }

        /**
         * Sets whether facilities already assigned in a previous routing attempt are excluded.
         * @param blacklistAssignedFacilities the blacklist flag
         * @return this builder
         */
        public Builder blacklistAssignedFacilities(Boolean blacklistAssignedFacilities) {
            this.blacklistAssignedFacilities = blacklistAssignedFacilities;
            return this;
        }

        /**
         * Sets the minutes after which a restow is executed.
         * @param restowAfterMinutes restow delay in minutes; absent means no restow
         * @return this builder
         */
        public Builder restowAfterMinutes(Double restowAfterMinutes) {
            this.restowAfterMinutes = restowAfterMinutes;
            return this;
        }

        /**
         * Sets the ISO 8601 duration after which a routing plan is considered not routable.
         * @param stopRoutingAttemptsAfterTime the stop-routing duration (default {@code PT8H})
         * @return this builder
         */
        public Builder stopRoutingAttemptsAfterTime(String stopRoutingAttemptsAfterTime) {
            this.stopRoutingAttemptsAfterTime = stopRoutingAttemptsAfterTime;
            return this;
        }

        /**
         * Sets the optional time-triggered reroute configuration.
         * @param timeTriggered the time-triggered reroute config
         * @return this builder
         */
        public Builder timeTriggered(RoutingStrategyRerouteTimeTriggeredConfig timeTriggered) {
            this.timeTriggered = timeTriggered;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyGlobalConfiguration}.
         *
         * @return a new instance.
         */
        public RoutingStrategyGlobalConfiguration build() {
            return new RoutingStrategyGlobalConfiguration(
                    defaultPrice, blacklistAssignedFacilities, restowAfterMinutes,
                    stopRoutingAttemptsAfterTime, timeTriggered);
        }
    }
}
