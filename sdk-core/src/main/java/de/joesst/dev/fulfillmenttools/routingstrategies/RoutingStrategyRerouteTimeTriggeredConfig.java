package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Global time-triggered reroute configuration, split by fulfilment channel.
 * All three channels are required.
 *
 * @param clickAndCollectReroute          reroute config for click-and-collect orders (required)
 * @param shipFromStoreDeliveryReroute    reroute config for ship-from-store delivery orders (required)
 * @param shipFromStoreSamedayReroute     reroute config for same-day ship-from-store orders (required)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyRerouteTimeTriggeredConfig(
        RerouteConfiguration clickAndCollectReroute,
        RerouteConfiguration shipFromStoreDeliveryReroute,
        RerouteConfiguration shipFromStoreSamedayReroute
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyRerouteTimeTriggeredConfig}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyRerouteTimeTriggeredConfig}.
     */
    public static final class Builder {

        private RerouteConfiguration clickAndCollectReroute;
        private RerouteConfiguration shipFromStoreDeliveryReroute;
        private RerouteConfiguration shipFromStoreSamedayReroute;

        private Builder() {}

        /**
         * Sets the reroute config for click-and-collect orders.
         * @param clickAndCollectReroute the click-and-collect reroute configuration
         * @return this builder
         */
        public Builder clickAndCollectReroute(RerouteConfiguration clickAndCollectReroute) {
            this.clickAndCollectReroute = clickAndCollectReroute;
            return this;
        }

        /**
         * Sets the reroute config for ship-from-store delivery orders.
         * @param shipFromStoreDeliveryReroute the ship-from-store delivery reroute configuration
         * @return this builder
         */
        public Builder shipFromStoreDeliveryReroute(RerouteConfiguration shipFromStoreDeliveryReroute) {
            this.shipFromStoreDeliveryReroute = shipFromStoreDeliveryReroute;
            return this;
        }

        /**
         * Sets the reroute config for same-day ship-from-store orders.
         * @param shipFromStoreSamedayReroute the same-day ship-from-store reroute configuration
         * @return this builder
         */
        public Builder shipFromStoreSamedayReroute(RerouteConfiguration shipFromStoreSamedayReroute) {
            this.shipFromStoreSamedayReroute = shipFromStoreSamedayReroute;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyRerouteTimeTriggeredConfig}.
         *
         * @return a new instance.
         */
        public RoutingStrategyRerouteTimeTriggeredConfig build() {
            return new RoutingStrategyRerouteTimeTriggeredConfig(
                    clickAndCollectReroute, shipFromStoreDeliveryReroute, shipFromStoreSamedayReroute);
        }
    }
}
