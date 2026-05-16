package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;
import de.joesst.dev.fulfillmenttools.orders.CollectDelivery;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferencesShipping;
import de.joesst.dev.fulfillmenttools.orders.DeliveryReservationPreferences;

import java.time.Instant;
import java.util.List;

/**
 * Delivery preferences for an order submitted to the sourcing options engine.
 *
 * <p>Maps to the {@code DeliveryPreferences} field on {@code OrderForSourcingOptionsRequest}
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Reuses {@link DeliveryPreferencesShipping}, {@link CollectDelivery}, and
 * {@link DeliveryReservationPreferences} from the orders domain — the schemas are identical.
 *
 * <p>Thread-safety: immutable; safe for concurrent use.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DeliveryPreferences {

    private final Instant targetTime;
    private final List<SourcingOptionsRequestId> sourcingOptionRefs;
    private final List<FacilityId> supplyingFacilities;
    private final DeliveryPreferencesShipping shipping;
    private final List<CollectDelivery> collect;
    private final DeliveryReservationPreferences reservationPreferences;

    private DeliveryPreferences(Builder builder) {
        this.targetTime = builder.targetTime;
        this.sourcingOptionRefs = builder.sourcingOptionRefs;
        this.supplyingFacilities = builder.supplyingFacilities;
        this.shipping = builder.shipping;
        this.collect = builder.collect;
        this.reservationPreferences = builder.reservationPreferences;
    }

    /**
     * Returns the target time for delivery.
     * @return the target time, or {@code null} if not set
     */
    public Instant targetTime() { return targetTime; }

    /**
     * Returns references to pre-computed sourcing options.
     * @return the sourcing option refs, or {@code null} if not set
     */
    public List<SourcingOptionsRequestId> sourcingOptionRefs() { return sourcingOptionRefs; }

    /**
     * Returns the supplying facilities.
     * @return the supplying facilities, or {@code null} if not set
     */
    public List<FacilityId> supplyingFacilities() { return supplyingFacilities; }

    /**
     * Returns the shipping delivery configuration.
     * @return the shipping preferences, or {@code null} if not set
     */
    public DeliveryPreferencesShipping shipping() { return shipping; }

    /**
     * Returns the click-and-collect delivery configuration.
     * @return the collect delivery options, or {@code null} if not set
     */
    public List<CollectDelivery> collect() { return collect; }

    /**
     * Returns the stock reservation preferences.
     * @return the reservation preferences, or {@code null} if not set
     */
    public DeliveryReservationPreferences reservationPreferences() { return reservationPreferences; }

    /**
     * Creates a new builder for constructing a {@link DeliveryPreferences}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link DeliveryPreferences}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private Instant targetTime;
        private List<SourcingOptionsRequestId> sourcingOptionRefs;
        private List<FacilityId> supplyingFacilities;
        private DeliveryPreferencesShipping shipping;
        private List<CollectDelivery> collect;
        private DeliveryReservationPreferences reservationPreferences;

        /**
         * Sets the target time for delivery.
         * @param targetTime the target delivery time
         * @return this builder
         */
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }

        /**
         * Sets references to pre-computed sourcing options.
         * @param sourcingOptionRefs the sourcing option refs
         * @return this builder
         */
        public Builder sourcingOptionRefs(List<SourcingOptionsRequestId> sourcingOptionRefs) { this.sourcingOptionRefs = sourcingOptionRefs; return this; }

        /**
         * Sets the supplying facilities.
         * @param supplyingFacilities the supplying facilities
         * @return this builder
         */
        public Builder supplyingFacilities(List<FacilityId> supplyingFacilities) { this.supplyingFacilities = supplyingFacilities; return this; }

        /**
         * Sets the shipping delivery configuration.
         * @param shipping the shipping preferences
         * @return this builder
         */
        public Builder shipping(DeliveryPreferencesShipping shipping) { this.shipping = shipping; return this; }

        /**
         * Sets the click-and-collect delivery configuration.
         * @param collect the collect delivery options
         * @return this builder
         */
        public Builder collect(List<CollectDelivery> collect) { this.collect = collect; return this; }

        /**
         * Sets the stock reservation preferences.
         * @param reservationPreferences the reservation preferences
         * @return this builder
         */
        public Builder reservationPreferences(DeliveryReservationPreferences reservationPreferences) { this.reservationPreferences = reservationPreferences; return this; }

        /**
         * Builds and returns a new {@link DeliveryPreferences}.
         * @return a new instance
         */
        public DeliveryPreferences build() { return new DeliveryPreferences(this); }
    }
}
