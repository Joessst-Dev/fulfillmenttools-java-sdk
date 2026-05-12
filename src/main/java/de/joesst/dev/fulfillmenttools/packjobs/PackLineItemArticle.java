package de.joesst.dev.fulfillmenttools.packjobs;

import java.util.Map;

/**
 * Article information for a pack job line item.
 */
public record PackLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Map<String, Object> customAttributes
) {}
