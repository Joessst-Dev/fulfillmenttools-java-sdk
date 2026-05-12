package de.joesst.dev.fulfillmenttools.inbound;

import java.util.Map;

/**
 * Article information for a stow job line item.
 */
public record StowLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        String measurementUnitKey,
        Map<String, Object> customAttributes
) {}
