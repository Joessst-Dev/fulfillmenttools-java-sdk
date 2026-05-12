package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a handover job in fulfillmenttools.
 */
public record HandoverJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        Map<String, Object> customAttributes
) {}
