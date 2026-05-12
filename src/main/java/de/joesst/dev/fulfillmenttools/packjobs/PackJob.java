package de.joesst.dev.fulfillmenttools.packjobs;

import java.time.Instant;

public record PackJob(
        String id,
        String facilityRef,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
