package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

/**
 * A single line item within an order.
 */
public record OrderLineItem(
        String id,
        Integer quantity,
        OrderLineItemArticle article,
        String measurementUnitKey,
        Map<String, Object> customAttributes
) {}
