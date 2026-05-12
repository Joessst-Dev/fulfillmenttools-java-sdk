package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

/**
 * Article information for an order line item.
 */
public record OrderLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, Object> customAttributes
) {}
