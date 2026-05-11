package de.joesst.dev.fulfillmenttools.stocks;

public record StockItem(
        String facilityRef,
        String tenantArticleId,
        int quantity,
        String unit
) {}
