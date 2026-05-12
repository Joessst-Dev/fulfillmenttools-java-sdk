package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.Map;

public record CancelationReason(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String action,
        String reason,
        Map<String, Object> reasonLocalized
) {}
