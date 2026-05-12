package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record PickLineItem(
        String id,
        Integer quantity,
        Integer picked,
        String status,
        PickLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        Integer secondaryPicked,
        String globalLineItemId,
        String originId,
        String pickJobLineItemRef,
        Instant pickedAt,
        Boolean stockEmptied,
        List<String> scannableCodes,
        List<Map<String, Object>> partialStockLocations,
        List<Map<String, Object>> recordableAttributes,
        Map<String, Object> measurementValidation,
        Map<String, Object> shortPickReason,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
