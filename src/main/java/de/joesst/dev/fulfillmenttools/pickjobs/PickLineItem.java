package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * A single line item within a pick job.
 */
public record PickLineItem(
        String id,
        Integer quantity,
        PickLineItemArticle article,
        Integer picked,
        String measurementUnitKey,
        Map<String, Object> customAttributes
) {}
