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
) {}
