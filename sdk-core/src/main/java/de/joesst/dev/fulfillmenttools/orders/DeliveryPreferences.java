package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Delivery preferences that determine how and where an order should be fulfilled.
 *
 * <p>Maps to the {@code DeliveryPreferences} schema in the fulfillmenttools OpenAPI spec.
 * Exactly one of {@code shipping} or {@code collect} should be provided to specify the
 * fulfillment channel.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param shipping               Configuration for ship-to-customer delivery.
 * @param collect                Configuration for click-and-collect delivery. At most one entry.
 * @param targetTime             The time by which the delivery result is expected.
 * @param sourcingOptionRefs     Optional references to pre-computed sourcing options.
 * @param supplyingFacilities    Deprecated: supply facilities at the collect level instead.
 * @param reservationPreferences Preferences controlling how stock reservations are scheduled.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryPreferences(
        DeliveryPreferencesShipping shipping,
        List<CollectDelivery> collect,
        Instant targetTime,
        List<String> sourcingOptionRefs,
        List<String> supplyingFacilities,
        DeliveryReservationPreferences reservationPreferences
) {

    /**
     * Returns a builder for constructing a {@code DeliveryPreferences}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link DeliveryPreferences}.
     */
    public static final class Builder {

        private DeliveryPreferencesShipping shipping;
        private List<CollectDelivery> collect;
        private Instant targetTime;
        private List<String> sourcingOptionRefs;
        private List<String> supplyingFacilities;
        private DeliveryReservationPreferences reservationPreferences;

        private Builder() {}

        /**
         * Sets the configuration for ship-to-customer delivery.
         * @param shipping the shipping delivery preferences
         * @return this builder
         */
        public Builder shipping(DeliveryPreferencesShipping shipping) {
            this.shipping = shipping;
            return this;
        }

        /**
         * Sets the configuration for click-and-collect delivery.
         * @param collect the list of collect delivery configurations
         * @return this builder
         */
        public Builder collect(List<CollectDelivery> collect) {
            this.collect = collect;
            return this;
        }

        /**
         * Sets the time by which the delivery result is expected.
         * @param targetTime the target delivery time
         * @return this builder
         */
        public Builder targetTime(Instant targetTime) {
            this.targetTime = targetTime;
            return this;
        }

        /**
         * Sets optional references to pre-computed sourcing options.
         * @param sourcingOptionRefs the sourcing option references
         * @return this builder
         */
        public Builder sourcingOptionRefs(List<String> sourcingOptionRefs) {
            this.sourcingOptionRefs = sourcingOptionRefs;
            return this;
        }

        /**
         * Sets the deprecated list of supplying facilities.
         * @param supplyingFacilities the list of supplying facility identifiers
         * @return this builder
         */
        public Builder supplyingFacilities(List<String> supplyingFacilities) {
            this.supplyingFacilities = supplyingFacilities;
            return this;
        }

        /**
         * Sets the preferences controlling how stock reservations are scheduled.
         * @param reservationPreferences the reservation preferences
         * @return this builder
         */
        public Builder reservationPreferences(DeliveryReservationPreferences reservationPreferences) {
            this.reservationPreferences = reservationPreferences;
            return this;
        }

        /**
         * Builds a {@link DeliveryPreferences}.
         *
         * @return a new instance.
         */
        public DeliveryPreferences build() {
            return new DeliveryPreferences(
                    shipping, collect, targetTime, sourcingOptionRefs,
                    supplyingFacilities, reservationPreferences);
        }
    }
}
