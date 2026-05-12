package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;

public record OrderLineItem(
        String id,
        Integer quantity,
        OrderLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        List<String> scannableCodes,
        List<Map<String, Object>> allowedSubstitutes,
        Map<String, Object> measurementValidation,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
