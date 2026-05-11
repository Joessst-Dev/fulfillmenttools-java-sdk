package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;

public record Order(
        String id,
        String tenantOrderId,
        String status,
        Instant createdDate,
        Instant lastModifiedDate
) {}
