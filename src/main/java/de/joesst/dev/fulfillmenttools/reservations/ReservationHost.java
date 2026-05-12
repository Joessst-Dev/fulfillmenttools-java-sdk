package de.joesst.dev.fulfillmenttools.reservations;

/**
 * The host system that owns a reservation.
 */
public record ReservationHost(String reference, String type) {}
