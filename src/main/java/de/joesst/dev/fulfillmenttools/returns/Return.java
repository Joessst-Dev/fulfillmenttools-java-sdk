package de.joesst.dev.fulfillmenttools.returns;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a return in fulfillmenttools.
 */
public record Return(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        Map<String, Object> customAttributes
) {}
