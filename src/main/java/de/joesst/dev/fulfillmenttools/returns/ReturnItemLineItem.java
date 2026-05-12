package de.joesst.dev.fulfillmenttools.returns;

import java.util.List;
import java.util.Map;

public record ReturnItemLineItem(
        String id,
        String status,
        String tenantArticleId,
        String itemCondition,
        String itemConditionComment,
        List<String> itemReturnJobLineItemRefs,
        List<String> scannedCodes,
        List<Map<String, Object>> reasons,
        Map<String, Object> customAttributes
) {}
