package de.joesst.dev.fulfillmenttools.storagelocations;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

/**
 * Represents a single entry in a storage location's running sequence, describing the
 * order in which locations are visited during picking or restow operations.
 *
 * <p>Maps to {@code StorageLocationSequenceItem} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type                       The sequence type. Known values: {@code PICKING_SEQUENCE},
 *                                   {@code RESTOW_SEQUENCE}.
 * @param nextStorageLocationRef     Reference to the next storage location in the sequence,
 *                                   or {@code null} if this is the last entry.
 * @param previousStorageLocationRef Reference to the previous storage location in the sequence,
 *                                   or {@code null} if this is the first entry.
 * @param score                      Deprecated ordering score. May be {@code null}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StorageLocationSequenceItem(
        String type,
        StorageLocationId nextStorageLocationRef,
        StorageLocationId previousStorageLocationRef,
        Double score
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String type;
        private StorageLocationId nextStorageLocationRef;
        private StorageLocationId previousStorageLocationRef;
        private Double score;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder nextStorageLocationRef(StorageLocationId nextStorageLocationRef) {
            this.nextStorageLocationRef = nextStorageLocationRef;
            return this;
        }

        public Builder previousStorageLocationRef(StorageLocationId previousStorageLocationRef) {
            this.previousStorageLocationRef = previousStorageLocationRef;
            return this;
        }

        public Builder score(Double score) {
            this.score = score;
            return this;
        }

        public StorageLocationSequenceItem build() {
            return new StorageLocationSequenceItem(type, nextStorageLocationRef, previousStorageLocationRef, score);
        }
    }
}
