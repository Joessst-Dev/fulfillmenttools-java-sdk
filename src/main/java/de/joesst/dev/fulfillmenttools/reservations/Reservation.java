package de.joesst.dev.fulfillmenttools.reservations;

import java.time.Instant;

public record Reservation(
        String id,
        String facilityRef,
        String tenantArticleId,
        int quantity,
        String status,
        Instant createdDate,
        Instant expiry
) {}
