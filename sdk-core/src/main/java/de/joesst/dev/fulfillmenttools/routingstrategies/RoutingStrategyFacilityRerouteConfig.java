package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Reroute configuration for a specific fulfilment channel (click-and-collect or ship-from-store).
 *
 * @param rerouteType the type of reroute to apply (required)
 * @param active      whether this reroute channel is active
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyFacilityRerouteConfig(
        RoutingStrategyRerouteType rerouteType,
        Boolean active
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyFacilityRerouteConfig}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyFacilityRerouteConfig}.
     */
    public static final class Builder {

        private RoutingStrategyRerouteType rerouteType;
        private Boolean active;

        private Builder() {}

        /**
         * Sets the type of reroute to apply.
         * @param rerouteType the reroute type
         * @return this builder
         */
        public Builder rerouteType(RoutingStrategyRerouteType rerouteType) {
            this.rerouteType = rerouteType;
            return this;
        }

        /**
         * Sets whether this reroute channel is active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyFacilityRerouteConfig}.
         *
         * @return a new instance.
         */
        public RoutingStrategyFacilityRerouteConfig build() {
            return new RoutingStrategyFacilityRerouteConfig(rerouteType, active);
        }
    }
}
