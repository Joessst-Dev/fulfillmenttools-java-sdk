package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.List;
import java.util.Map;

public record HandoverLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, Object> titleLocalized,
        Map<String, Object> customAttributes,
        List<Map<String, Object>> attributes
) {}
