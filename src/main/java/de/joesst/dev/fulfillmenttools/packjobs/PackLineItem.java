package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;

public record PackLineItem(
        String id,
        Integer quantity,
        PackLineItemArticle article,
        Integer packed,
        String measurementUnitKey,
        String originId,
        List<String> serviceJobRefs,
        List<Map<String, Object>> stickers,
        List<TagReference> tags
) {}
