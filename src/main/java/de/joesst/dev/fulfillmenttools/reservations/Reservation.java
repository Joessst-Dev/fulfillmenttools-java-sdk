package de.joesst.dev.fulfillmenttools.reservations;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a stock reservation in fulfillmenttools.
 */
public record Reservation(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String tenantArticleId,
        int quantity,
        ReservationHost host,
        RelatedRefs relatedRefs,
        Map<String, Object> customAttributes
) {}
