package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Configuration for a {@link RoutingStrategyNode} that defines which fences, ratings, order-split
 * behaviour, and reroute settings apply.
 *
 * @param fences           the fences applied to candidate facilities (required)
 * @param ratings          the ratings used to rank candidate facilities (required)
 * @param orderSplit       optional order-split configuration
 * @param reroute          optional reroute configuration
 * @param fallbackFacility optional fallback-facility configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyNodeConfig(
        List<RoutingStrategyFence> fences,
        List<RoutingStrategyRating> ratings,
        RoutingStrategyOrderSplitConfig orderSplit,
        RoutingStrategyRerouteConfig reroute,
        RoutingStrategyFallbackFacilityConfig fallbackFacility
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyNodeConfig}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyNodeConfig}.
     */
    public static final class Builder {

        private List<RoutingStrategyFence> fences;
        private List<RoutingStrategyRating> ratings;
        private RoutingStrategyOrderSplitConfig orderSplit;
        private RoutingStrategyRerouteConfig reroute;
        private RoutingStrategyFallbackFacilityConfig fallbackFacility;

        private Builder() {}

        /**
         * Sets the fences applied to candidate facilities.
         * @param fences the fence list
         * @return this builder
         */
        public Builder fences(List<RoutingStrategyFence> fences) {
            this.fences = fences;
            return this;
        }

        /**
         * Sets the ratings used to rank candidate facilities.
         * @param ratings the rating list
         * @return this builder
         */
        public Builder ratings(List<RoutingStrategyRating> ratings) {
            this.ratings = ratings;
            return this;
        }

        /**
         * Sets the optional order-split configuration.
         * @param orderSplit the order-split configuration
         * @return this builder
         */
        public Builder orderSplit(RoutingStrategyOrderSplitConfig orderSplit) {
            this.orderSplit = orderSplit;
            return this;
        }

        /**
         * Sets the optional reroute configuration.
         * @param reroute the reroute configuration
         * @return this builder
         */
        public Builder reroute(RoutingStrategyRerouteConfig reroute) {
            this.reroute = reroute;
            return this;
        }

        /**
         * Sets the optional fallback-facility configuration.
         * @param fallbackFacility the fallback facility configuration
         * @return this builder
         */
        public Builder fallbackFacility(RoutingStrategyFallbackFacilityConfig fallbackFacility) {
            this.fallbackFacility = fallbackFacility;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyNodeConfig}.
         *
         * @return a new instance.
         */
        public RoutingStrategyNodeConfig build() {
            return new RoutingStrategyNodeConfig(fences, ratings, orderSplit, reroute, fallbackFacility);
        }
    }
}
