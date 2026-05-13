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
) {}
