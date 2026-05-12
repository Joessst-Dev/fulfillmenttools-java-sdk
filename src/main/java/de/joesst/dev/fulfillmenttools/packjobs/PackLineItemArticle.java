package de.joesst.dev.fulfillmenttools.packjobs;

import java.util.List;
import java.util.Map;

public record PackLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, Object> titleLocalized,
        Map<String, Object> customAttributes,
        List<Map<String, Object>> attributes
) {}
