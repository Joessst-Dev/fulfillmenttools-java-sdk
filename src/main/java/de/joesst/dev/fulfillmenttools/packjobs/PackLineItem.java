package de.joesst.dev.fulfillmenttools.packjobs;

/**
 * A single line item within a pack job.
 */
public record PackLineItem(
        String id,
        Integer quantity,
        PackLineItemArticle article,
        Integer packed,
        String measurementUnitKey
) {}
