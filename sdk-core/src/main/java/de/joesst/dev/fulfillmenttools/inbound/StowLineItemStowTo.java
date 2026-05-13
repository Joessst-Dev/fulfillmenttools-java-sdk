package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

import java.util.List;

/**
 * A stow-to instruction within a stow line item as returned by the API, including the
 * server-assigned ID and operative stowing progress.
 *
 * <p>Maps to the {@code StowLineItemStowToWithId} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                 Server-assigned ID of this stow-to instruction.
 * @param quantity           Quantity that should be stowed.
 * @param type               Destination type: {@code STOCK}, {@code LOCATION}, or
 *                           {@code DISCARD}. Use {@code DISCARD} to book the item out of
 *                           the system.
 * @param stockRef           Reference to the target stock. Leave {@code null} for types
 *                           {@code LOCATION} or {@code DISCARD}.
 * @param storageLocationRef Reference to the target storage location.
 * @param scannableCodes     Barcodes relevant for stowing the item.
 * @param stowed             Operative progress data tracking what has been stowed so far.
 */
public record StowLineItemStowTo(
        String id,
        Integer quantity,
        String type,
        String stockRef,
        StorageLocationId storageLocationRef,
        List<String> scannableCodes,
        StowedProgress stowed
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Integer quantity;
        private String type;
        private String stockRef;
        private StorageLocationId storageLocationRef;
        private List<String> scannableCodes;
        private StowedProgress stowed;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder stockRef(String stockRef) { this.stockRef = stockRef; return this; }
        public Builder storageLocationRef(StorageLocationId storageLocationRef) { this.storageLocationRef = storageLocationRef; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder stowed(StowedProgress stowed) { this.stowed = stowed; return this; }

        public StowLineItemStowTo build() {
            return new StowLineItemStowTo(id, quantity, type, stockRef, storageLocationRef, scannableCodes, stowed);
        }
    }
}
