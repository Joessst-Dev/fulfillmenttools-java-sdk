package de.joesst.dev.fulfillmenttools.internal.checkoutoptions;

import java.util.List;
import java.util.Map;

record EvaluateCheckoutOptionsBody(
        Map<String, Object> deliveryPreferences,
        List<Map<String, Object>> orderLineItems,
        Map<String, Object> consumerAddress,
        Map<String, Object> customAttributes,
        Boolean filterDuplicates,
        List<Map<String, Object>> customServices,
        Map<String, Object> geoFence,
        List<Map<String, Object>> tags
) {}
