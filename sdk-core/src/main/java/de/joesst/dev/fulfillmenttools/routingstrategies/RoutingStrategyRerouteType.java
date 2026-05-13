package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The type of reroute to apply to an order.
 *
 * <ul>
 *   <li>{@code REROUTE} — the complete routing plan is rerouted.</li>
 *   <li>{@code ORDERSPLIT} — the order is split; the split line items are moved to a new routing
 *   plan and rerouted.</li>
 * </ul>
 */
public enum RoutingStrategyRerouteType {
    /**
     * The complete routing plan is rerouted.
     */
    REROUTE,
    /**
     * The order is split; the split line items are moved to a new routing plan and rerouted.
     */
    ORDERSPLIT
}
