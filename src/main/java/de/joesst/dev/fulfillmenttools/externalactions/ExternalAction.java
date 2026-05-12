package de.joesst.dev.fulfillmenttools.externalactions;

import java.time.Instant;
import java.util.Map;

/**
 * Represents an external action configured in fulfillmenttools.
 */
public record ExternalAction(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String name,
        String actionType,
        String status,
        Map<String, Object> customAttributes
) {}
