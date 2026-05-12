package de.joesst.dev.fulfillmenttools.internal.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Map;

record CreateStowJobBody(
        String facilityRef,
        String status,
        List<Map<String, Object>> stowLineItems,
        List<Map<String, Object>> assignedUsers,
        Map<String, Object> customAttributes,
        Integer priority,
        String shortId,
        Instant targetTime
) {}
