package de.joesst.dev.fulfillmenttools.returns;

import java.util.List;
import java.util.Map;

public record ReturnJobLineItem(
        String id,
        String status,
        String globalLineItemId,
        Double delivered,
        Double returned,
        Double returnable,
        ReturnJobLineItemArticle article,
        List<String> scannableCodes,
        List<String> serviceJobRefs,
        Map<String, Object> customAttributes
) {}
