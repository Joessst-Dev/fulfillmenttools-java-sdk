package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Preferences controlling how reservations are made for a delivery.
 *
 * <p>Maps to the {@code DeliveryReservationPreferences} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param mode            The reservation scheduling mode ({@code SCHEDULED}, {@code ASAP},
 *                        or {@code ALAP}).
 * @param reservationTime The explicit reservation timestamp, used when mode is
 *                        {@code SCHEDULED}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryReservationPreferences(
        String mode,
        Instant reservationTime
) {}
