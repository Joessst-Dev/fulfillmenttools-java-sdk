package de.joesst.dev.fulfillmenttools.processes;

import java.time.Instant;

public record Process(
        String id,
        String facilityRef,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
