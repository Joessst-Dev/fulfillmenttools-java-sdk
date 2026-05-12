package de.joesst.dev.fulfillmenttools.returns;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ReturnItem(
        String id,
        Instant created,
        Instant lastModified,
        String status,
        String returnFacilityRef,
        String tenantOrderId,
        List<String> scannableCodes,
        List<String> parcelRefs,
        List<ReturnItemLineItem> returnedLineItems,
        Map<String, Object> customAttributes
) {}
