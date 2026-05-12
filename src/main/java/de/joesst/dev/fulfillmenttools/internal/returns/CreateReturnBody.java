package de.joesst.dev.fulfillmenttools.internal.returns;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.util.List;
import java.util.Map;

record CreateReturnBody(
        List<String> originFacilityRefs,
        String status,
        List<ConsumerAddress> consumerAddresses,
        List<Map<String, Object>> returnableLineItems,
        List<Map<String, Object>> notReturnableLineItems,
        List<String> scannableCodes,
        String shortId,
        String tenantOrderId,
        Map<String, Object> customAttributes
) {}
