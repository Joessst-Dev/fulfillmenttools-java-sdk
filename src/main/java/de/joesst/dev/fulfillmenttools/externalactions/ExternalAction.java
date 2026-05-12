package de.joesst.dev.fulfillmenttools.externalactions;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ExternalAction(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String name,
        String processRef,
        Map<String, Object> nameLocalized,
        Map<String, Object> action,
        List<String> groups,
        Map<String, Object> customAttributes
) {}
