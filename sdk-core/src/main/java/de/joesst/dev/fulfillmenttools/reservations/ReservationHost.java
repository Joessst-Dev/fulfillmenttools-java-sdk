package de.joesst.dev.fulfillmenttools.reservations;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The host system that owns a {@link Reservation}.
 *
 * <p>Maps to {@code ReservationHost} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reference the identifier of the host entity (e.g., stock ID); required
 * @param type      the type of host system; required
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReservationHost(String reference, HostType type) {}
