package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;

public record StowJob(
        String id,
        String facilityRef,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
