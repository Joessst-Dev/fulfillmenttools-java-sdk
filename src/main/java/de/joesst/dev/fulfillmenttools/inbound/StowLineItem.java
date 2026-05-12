package de.joesst.dev.fulfillmenttools.inbound;

import java.util.Map;

/**
 * A single line item within a stow job.
 */
public record StowLineItem(
        String id,
        StowLineItemArticle article,
        Map<String, Object> customAttributes
) {}
