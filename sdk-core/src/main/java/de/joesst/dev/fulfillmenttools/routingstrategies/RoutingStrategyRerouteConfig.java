package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Node-level reroute configuration controlling when and how unfulfilled orders are rerouted.
 *
 * @param clickAndCollect     reroute config for click-and-collect orders
 * @param shipFromStore       reroute config for ship-from-store orders
 * @param manualReroute       whether manual rerouting via API is allowed
 * @param rerouteZeroPicksOnly whether only pick jobs with zero items picked are eligible for
 *                             rerouting (default {@code false})
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyRerouteConfig(
        RoutingStrategyFacilityRerouteConfig clickAndCollect,
        RoutingStrategyFacilityRerouteConfig shipFromStore,
        Boolean manualReroute,
        Boolean rerouteZeroPicksOnly
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyRerouteConfig}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyRerouteConfig}.
     */
    public static final class Builder {

        private RoutingStrategyFacilityRerouteConfig clickAndCollect;
        private RoutingStrategyFacilityRerouteConfig shipFromStore;
        private Boolean manualReroute;
        private Boolean rerouteZeroPicksOnly;

        private Builder() {}

        /**
         * Sets the reroute config for click-and-collect orders.
         * @param clickAndCollect the click-and-collect reroute configuration
         * @return this builder
         */
        public Builder clickAndCollect(RoutingStrategyFacilityRerouteConfig clickAndCollect) {
            this.clickAndCollect = clickAndCollect;
            return this;
        }

        /**
         * Sets the reroute config for ship-from-store orders.
         * @param shipFromStore the ship-from-store reroute configuration
         * @return this builder
         */
        public Builder shipFromStore(RoutingStrategyFacilityRerouteConfig shipFromStore) {
            this.shipFromStore = shipFromStore;
            return this;
        }

        /**
         * Sets whether manual rerouting via API is allowed.
         * @param manualReroute the manual reroute flag
         * @return this builder
         */
        public Builder manualReroute(Boolean manualReroute) {
            this.manualReroute = manualReroute;
            return this;
        }

        /**
         * Sets whether only pick jobs with zero items picked are eligible for rerouting.
         * @param rerouteZeroPicksOnly the zero-picks-only flag (default {@code false})
         * @return this builder
         */
        public Builder rerouteZeroPicksOnly(Boolean rerouteZeroPicksOnly) {
            this.rerouteZeroPicksOnly = rerouteZeroPicksOnly;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyRerouteConfig}.
         *
         * @return a new instance.
         */
        public RoutingStrategyRerouteConfig build() {
            return new RoutingStrategyRerouteConfig(
                    clickAndCollect, shipFromStore, manualReroute, rerouteZeroPicksOnly);
        }
    }
}
