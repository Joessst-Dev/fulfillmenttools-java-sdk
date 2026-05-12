package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;

public record HandoverLineItem(
        String id,
        Integer quantity,
        Integer handedOverQuantity,
        String originId,
        String status,
        HandoverLineItemArticle article,
        List<Map<String, Object>> refused,
        List<Map<String, Object>> stickers,
        List<Map<String, Object>> substituteLineItems,
        List<TagReference> tags
) {}
