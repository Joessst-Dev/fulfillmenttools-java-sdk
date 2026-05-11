package de.joesst.dev.fulfillmenttools.facilities;

import java.time.Instant;

public record Facility(
        String id,
        String tenantFacilityId,
        String name,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
