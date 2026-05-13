package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * A zone reference from which articles may be picked in a pick job.
 *
 * <p>Maps to the {@code PickingZone} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param zoneRef Reference to the zone entity.
 */
public record PickingZone(
        String zoneRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String zoneRef;

        private Builder() {}

        public Builder zoneRef(String zoneRef) { this.zoneRef = zoneRef; return this; }

        public PickingZone build() {
            return new PickingZone(zoneRef);
        }
    }
}
