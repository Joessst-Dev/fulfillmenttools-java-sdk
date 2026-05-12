package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;
import java.util.Map;

public record StowLineItem(
        String id,
        StowLineItemArticle article,
        Map<String, Object> customAttributes,
        String heldStockRef,
        List<Map<String, Object>> reasons,
        List<Map<String, Object>> stowTo,
        Map<String, Object> takeFrom
) {}
