package de.joesst.dev.fulfillmenttools.internal.routingstrategies;

import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategy;

import java.util.List;

record RoutingStrategyListResponse(List<RoutingStrategy> routingStrategies, String nextCursor) {}
