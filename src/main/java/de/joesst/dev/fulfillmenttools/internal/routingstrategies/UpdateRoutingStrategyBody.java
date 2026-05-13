package de.joesst.dev.fulfillmenttools.internal.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategyGlobalConfiguration;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategyNode;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateRoutingStrategyBody(
        Integer version,
        Map<String, String> nameLocalized,
        RoutingStrategyNode rootNode,
        RoutingStrategyGlobalConfiguration globalConfiguration
) {}
