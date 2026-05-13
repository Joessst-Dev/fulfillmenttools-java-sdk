package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration for order splitting within a routing strategy node.
 *
 * @param active                                   whether order splitting is active (required)
 * @param maxSplitCount                            maximum number of splits allowed (required)
 * @param activeForSameday                         whether splitting is active for same-day orders
 *                                                 (required)
 * @param shouldUseWaitingRoomForPreBackOrderItems whether pre-back-order items should use the
 *                                                 waiting room (required)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyOrderSplitConfig(
        Boolean active,
        Integer maxSplitCount,
        Boolean activeForSameday,
        Boolean shouldUseWaitingRoomForPreBackOrderItems
) {}
