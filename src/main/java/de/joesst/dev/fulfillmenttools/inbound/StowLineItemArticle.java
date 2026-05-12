package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;
import java.util.Map;

public record StowLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        String measurementUnitKey,
        Map<String, Object> customAttributes,
        Map<String, Object> titleLocalized,
        List<String> scannableCodes
) {}
