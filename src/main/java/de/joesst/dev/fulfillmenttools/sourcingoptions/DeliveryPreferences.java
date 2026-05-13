package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private final List<String> sourcingOptionRefs;
    private final List<String> supplyingFacilities;
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

    /** The time by which the delivery result is expected. */
    public Instant targetTime() { return targetTime; }
    /** Optional references to pre-computed sourcing options. */
    public List<String> sourcingOptionRefs() { return sourcingOptionRefs; }
    /** Deprecated: supply facilities at the collect level instead. */
    public List<String> supplyingFacilities() { return supplyingFacilities; }
    /** Configuration for ship-to-customer delivery. */
    public DeliveryPreferencesShipping shipping() { return shipping; }
    /** Configuration for click-and-collect delivery. At most one entry. */
    public List<CollectDelivery> collect() { return collect; }
    /** Preferences controlling how stock reservations are scheduled. */
    public DeliveryReservationPreferences reservationPreferences() { return reservationPreferences; }

    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link DeliveryPreferences}.
     */
    public static final class Builder {

        private Instant targetTime;
        private List<String> sourcingOptionRefs;
        private List<String> supplyingFacilities;
        private DeliveryPreferencesShipping shipping;
        private List<CollectDelivery> collect;
        private DeliveryReservationPreferences reservationPreferences;

        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder sourcingOptionRefs(List<String> sourcingOptionRefs) { this.sourcingOptionRefs = sourcingOptionRefs; return this; }
        public Builder supplyingFacilities(List<String> supplyingFacilities) { this.supplyingFacilities = supplyingFacilities; return this; }
        public Builder shipping(DeliveryPreferencesShipping shipping) { this.shipping = shipping; return this; }
        public Builder collect(List<CollectDelivery> collect) { this.collect = collect; return this; }
        public Builder reservationPreferences(DeliveryReservationPreferences reservationPreferences) { this.reservationPreferences = reservationPreferences; return this; }

        public DeliveryPreferences build() { return new DeliveryPreferences(this); }
    }
}
