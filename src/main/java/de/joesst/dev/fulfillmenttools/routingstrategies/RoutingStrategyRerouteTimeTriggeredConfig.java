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
) {}
