package de.joesst.dev.fulfillmenttools.eventing;

import java.util.List;

/**
 * Context filter for an event subscription.
 *
 * @param type the context type
 * @param values list of context values to filter on
 */
public record SubscriptionContext(String type, List<String> values) {}
