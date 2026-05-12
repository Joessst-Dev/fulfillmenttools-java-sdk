package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record Return(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String status,
        String shortId,
        String processRef,
        String tenantOrderId,
        List<String> originFacilityRefs,
        List<String> scannableCodes,
        List<ConsumerAddress> consumerAddresses,
        List<ReturnJobLineItem> returnableLineItems,
        List<ReturnJobLineItem> notReturnableLineItems,
        List<ReturnItem> itemReturns,
        Boolean anonymized,
        Map<String, Object> customAttributes
) {}
