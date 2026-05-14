package de.joesst.dev.fulfillmenttools.storagelocations;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A single entry in a storage location's trait configuration, describing whether a
 * specific operational trait is enabled or disabled for that location.
 *
 * <p>Maps to {@code StorageLocationTraitConfigEntry} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param trait   The trait identifier. Known values: {@code PICKABLE}, {@code ACCESSIBLE},
 *                {@code KEEP_ON_ZERO}, {@code OUTBOUND}, {@code IN_MOTION}.
 * @param enabled Whether the trait is currently enabled for this storage location.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StorageLocationTraitConfigEntry(
        String trait,
        Boolean enabled
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String trait;
        private Boolean enabled;

        public Builder trait(String trait) {
            this.trait = trait;
            return this;
        }

        public Builder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public StorageLocationTraitConfigEntry build() {
            return new StorageLocationTraitConfigEntry(trait, enabled);
        }
    }
}
