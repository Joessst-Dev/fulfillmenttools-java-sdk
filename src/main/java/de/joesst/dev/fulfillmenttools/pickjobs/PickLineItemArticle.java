package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.List;
import java.util.Map;

public record PickLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, Object> titleLocalized,
        List<Map<String, Object>> attributes,
        Map<String, Object> customAttributes
) {}
