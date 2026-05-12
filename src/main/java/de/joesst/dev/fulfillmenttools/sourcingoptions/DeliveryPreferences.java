package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class DeliveryPreferences {

    private final Instant targetTime;
    private final List<String> sourcingOptionRefs;
    private final List<String> supplyingFacilities;
    private final Map<String, Object> shipping;
    private final Map<String, Object> collect;
    private final Map<String, Object> reservationPreferences;

    private DeliveryPreferences(Builder builder) {
        this.targetTime = builder.targetTime;
        this.sourcingOptionRefs = builder.sourcingOptionRefs;
        this.supplyingFacilities = builder.supplyingFacilities;
        this.shipping = builder.shipping;
        this.collect = builder.collect;
        this.reservationPreferences = builder.reservationPreferences;
    }

    public Instant targetTime() { return targetTime; }
    public List<String> sourcingOptionRefs() { return sourcingOptionRefs; }
    public List<String> supplyingFacilities() { return supplyingFacilities; }
    public Map<String, Object> shipping() { return shipping; }
    public Map<String, Object> collect() { return collect; }
    public Map<String, Object> reservationPreferences() { return reservationPreferences; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Instant targetTime;
        private List<String> sourcingOptionRefs;
        private List<String> supplyingFacilities;
        private Map<String, Object> shipping;
        private Map<String, Object> collect;
        private Map<String, Object> reservationPreferences;

        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder sourcingOptionRefs(List<String> sourcingOptionRefs) { this.sourcingOptionRefs = sourcingOptionRefs; return this; }
        public Builder supplyingFacilities(List<String> supplyingFacilities) { this.supplyingFacilities = supplyingFacilities; return this; }
        public Builder shipping(Map<String, Object> shipping) { this.shipping = shipping; return this; }
        public Builder collect(Map<String, Object> collect) { this.collect = collect; return this; }
        public Builder reservationPreferences(Map<String, Object> reservationPreferences) { this.reservationPreferences = reservationPreferences; return this; }

        public DeliveryPreferences build() { return new DeliveryPreferences(this); }
    }
}
