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
) {}
