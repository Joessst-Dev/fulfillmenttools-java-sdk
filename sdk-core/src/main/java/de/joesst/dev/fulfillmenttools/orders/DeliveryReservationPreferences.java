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
) {

    /**
     * Returns a builder for constructing a {@code DeliveryReservationPreferences}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link DeliveryReservationPreferences}.
     */
    public static final class Builder {

        private String mode;
        private Instant reservationTime;

        private Builder() {}

        /**
         * Sets the reservation scheduling mode.
         * @param mode the mode ({@code SCHEDULED}, {@code ASAP}, or {@code ALAP})
         * @return this builder
         */
        public Builder mode(String mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Sets the explicit reservation timestamp, used when mode is {@code SCHEDULED}.
         * @param reservationTime the reservation timestamp
         * @return this builder
         */
        public Builder reservationTime(Instant reservationTime) {
            this.reservationTime = reservationTime;
            return this;
        }

        /**
         * Builds a {@link DeliveryReservationPreferences}.
         *
         * @return a new instance.
         */
        public DeliveryReservationPreferences build() {
            return new DeliveryReservationPreferences(mode, reservationTime);
        }
    }
}
