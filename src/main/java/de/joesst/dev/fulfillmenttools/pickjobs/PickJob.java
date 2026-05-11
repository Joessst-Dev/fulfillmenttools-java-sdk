package de.joesst.dev.fulfillmenttools.pickjobs;

import java.time.Instant;

public record PickJob(
        String id,
        String facilityRef,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
