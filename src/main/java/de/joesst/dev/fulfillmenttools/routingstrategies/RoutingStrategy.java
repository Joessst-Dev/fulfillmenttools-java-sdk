package de.joesst.dev.fulfillmenttools.routingstrategies;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a routing strategy in fulfillmenttools.
 */
public record RoutingStrategy(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String name,
        Boolean inUse,
        Integer revision,
        Map<String, Object> customAttributes
) {}
