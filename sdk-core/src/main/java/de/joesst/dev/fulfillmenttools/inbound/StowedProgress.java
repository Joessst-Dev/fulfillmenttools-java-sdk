package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

import java.time.Instant;

/**
 * Operative data tracking how much of a stow-to instruction has been executed.
 *
 * <p>Maps to the {@code StowedStowLineItemStowToForCreation} schema in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param stowedAt               Timestamp when the item was stowed. Set to the current time
 *                               by the server if not provided.
 * @param stowedQuantity         Quantity that was actually stowed.
 * @param stowedStockRef         Reference to the stock where the item was stowed.
 *                               Set to {@code null} to explicitly create a new stock.
 *                               Exception: when stow-to type is {@code DISCARD}, a new stock
 *                               is not created when {@code null}, but if a stock is specified
 *                               its quantity will be increased regardless.
 * @param stowedStorageLocationRef Storage location reference for the newly created stock
 *                               (only used when {@code stowedStockRef} is {@code null}).
 */
public record StowedProgress(
        Instant stowedAt,
        Integer stowedQuantity,
        String stowedStockRef,
        StorageLocationId stowedStorageLocationRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant stowedAt;
        private Integer stowedQuantity;
        private String stowedStockRef;
        private StorageLocationId stowedStorageLocationRef;

        private Builder() {}

        public Builder stowedAt(Instant stowedAt) { this.stowedAt = stowedAt; return this; }
        public Builder stowedQuantity(Integer stowedQuantity) { this.stowedQuantity = stowedQuantity; return this; }
        public Builder stowedStockRef(String stowedStockRef) { this.stowedStockRef = stowedStockRef; return this; }
        public Builder stowedStorageLocationRef(StorageLocationId stowedStorageLocationRef) { this.stowedStorageLocationRef = stowedStorageLocationRef; return this; }

        public StowedProgress build() {
            return new StowedProgress(stowedAt, stowedQuantity, stowedStockRef, stowedStorageLocationRef);
        }
    }
}
