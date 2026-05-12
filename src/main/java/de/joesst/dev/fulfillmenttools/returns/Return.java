package de.joesst.dev.fulfillmenttools.returns;

import java.time.Instant;

public record Return(
        String id,
        String facilityRef,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
