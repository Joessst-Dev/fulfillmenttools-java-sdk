package de.joesst.dev.fulfillmenttools.inbound;

/**
 * A trait configuration entry for a storage location, describing which operational
 * actions the associated stock is available for.
 *
 * <p>Maps to the {@code StorageLocationTraitConfigEntry} schema in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param enabled Whether the trait is enabled.
 * @param trait   The trait type. Known values:
 *                {@code PICKABLE} — stock is available for picking;
 *                {@code ACCESSIBLE} — stock is available for stock movements;
 *                {@code KEEP_ON_ZERO} — stock will not be deleted when emptied;
 *                {@code OUTBOUND} — stock is intended for outbound processes.
 */
public record StorageLocationTraitConfigEntry(
        Boolean enabled,
        String trait
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean enabled;
        private String trait;

        private Builder() {}

        public Builder enabled(Boolean enabled) { this.enabled = enabled; return this; }
        public Builder trait(String trait) { this.trait = trait; return this; }

        public StorageLocationTraitConfigEntry build() {
            return new StorageLocationTraitConfigEntry(enabled, trait);
        }
    }
}
