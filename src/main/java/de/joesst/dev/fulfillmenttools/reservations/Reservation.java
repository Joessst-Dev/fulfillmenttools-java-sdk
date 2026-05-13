package de.joesst.dev.fulfillmenttools.reservations;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a stock reservation in fulfillmenttools.
 *
 * <p>Maps to the {@code Reservation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               unique reservation identifier; required
 * @param version          optimistic-concurrency version; required
 * @param created          creation timestamp in UTC
 * @param lastModified     last-modification timestamp in UTC
 * @param facilityRef      reference to the facility holding the stock; required
 * @param tenantArticleId  tenant-specific article identifier; required
 * @param quantity         reserved quantity; required
 * @param host             the host system that owns this reservation; required
 * @param relatedRefs      cross-domain entity references; required
 * @param customAttributes arbitrary key-value metadata provided by the tenant;
 *                         kept as {@code Map<String,Object>} because the spec defines
 *                         this field as a free-form {@code object}
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
