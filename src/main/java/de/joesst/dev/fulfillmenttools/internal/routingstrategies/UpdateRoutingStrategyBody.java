package de.joesst.dev.fulfillmenttools.internal.routingstrategies;

import java.util.Map;

record UpdateRoutingStrategyBody(
        Integer version,
        Map<String, Object> nameLocalized,
        Map<String, Object> rootNode,
        Map<String, Object> globalConfiguration
) {}
