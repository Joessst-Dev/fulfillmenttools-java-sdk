package de.joesst.dev.fulfillmenttools.routingstrategies;

import java.time.Instant;
import java.util.Map;

public record RoutingStrategy(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String name,
        Map<String, Object> nameLocalized,
        Boolean inUse,
        Integer revision,
        Map<String, Object> globalConfiguration,
        Map<String, Object> rootNode
) {}
