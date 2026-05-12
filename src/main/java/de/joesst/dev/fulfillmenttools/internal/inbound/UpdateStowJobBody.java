package de.joesst.dev.fulfillmenttools.internal.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Map;

record UpdateStowJobBody(
        Integer version,
        Integer priority,
        Instant targetTime,
        List<Map<String, Object>> assignedUsers,
        Map<String, Object> customAttributes
) {}
