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
) {}
