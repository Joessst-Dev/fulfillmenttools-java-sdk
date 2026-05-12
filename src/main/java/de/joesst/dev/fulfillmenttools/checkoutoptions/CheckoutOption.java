package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.time.Instant;

/**
 * Represents a checkout option evaluation result in fulfillmenttools.
 */
public record CheckoutOption(
        String id,
        Integer version,
        String status,
        Instant created,
        Instant lastModified
) {}
