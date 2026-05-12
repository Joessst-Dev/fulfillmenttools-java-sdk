package de.joesst.dev.fulfillmenttools.returns;

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
        Boolean anonymized,
        Map<String, Object> customAttributes
) {}
