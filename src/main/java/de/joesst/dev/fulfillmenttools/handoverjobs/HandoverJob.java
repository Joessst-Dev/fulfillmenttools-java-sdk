package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.time.Instant;

public record HandoverJob(
        String id,
        String facilityRef,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
