package de.joesst.dev.fulfillmenttools.routingstrategies;

import de.joesst.dev.fulfillmenttools.id.RoutingStrategyId;

import java.time.Instant;
import java.util.Map;

/**
 * A routing strategy that determines how fulfillment orders are routed to facilities.
 *
 * <p>Only one strategy can be active ({@code inUse}) at a time.
 *
 * @param id                  unique identifier
 * @param version             optimistic-locking version
 * @param created             timestamp when this strategy was created
 * @param lastModified        timestamp when this strategy was last modified
 * @param name                plain-text name
 * @param nameLocalized       localized name keyed by locale (e.g. {@code "en_US"})
 * @param inUse               whether this is the currently active strategy (required)
 * @param revision            the revision number of the strategy (required)
 * @param globalConfiguration global routing configuration applied across all nodes
 * @param rootNode            the root node of the routing decision tree
 * @param customAttributes    arbitrary key/value pairs set by the caller
 */
public record RoutingStrategy(
        RoutingStrategyId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String name,
        Map<String, String> nameLocalized,
        Boolean inUse,
        Integer revision,
        RoutingStrategyGlobalConfiguration globalConfiguration,
        RoutingStrategyNode rootNode,
        Map<String, Object> customAttributes
) {}
