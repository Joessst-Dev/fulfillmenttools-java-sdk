package de.joesst.dev.fulfillmenttools.listings;

import java.time.Instant;
import java.util.Map;

public record Listing(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String tenantArticleId,
        String title,
        Map<String, Object> stock,
        Map<String, Object> customAttributes
) {}
