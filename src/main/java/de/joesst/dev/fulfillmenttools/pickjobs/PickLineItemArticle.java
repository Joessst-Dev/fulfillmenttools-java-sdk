package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * Article information for a pick job line item.
 */
public record PickLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Map<String, Object> customAttributes
) {}
