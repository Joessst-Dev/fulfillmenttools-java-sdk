package de.joesst.dev.fulfillmenttools.stocks;

import java.util.Map;

/**
 * Represents the stock level of an article at a facility.
 */
public record StockItem(
        String facilityRef,
        String tenantArticleId,
        int quantity,
        String unit,
        Map<String, Object> customAttributes
) {}
