package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

import java.util.List;

/**
 * A stow-to instruction used when creating a stow line item.
 *
 * <p>Maps to the {@code StowLineItemStowToForCreation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param quantity           Quantity that should be stowed.
 * @param type               Destination type: {@code STOCK}, {@code LOCATION}, or
 *                           {@code DISCARD}. Use {@code DISCARD} to book the item out of
 *                           the system.
 * @param stockRef           Reference to the target stock. Leave {@code null} for types
 *                           {@code LOCATION} or {@code DISCARD}.
 * @param storageLocationRef Reference to the target storage location.
 * @param scannableCodes     Barcodes relevant for stowing the item.
 * @param stowed             Optional operative progress data (if stowing has already started).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StowLineItemStowToForCreation(
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
        private Integer quantity;
        private String type;
        private String stockRef;
        private StorageLocationId storageLocationRef;
        private List<String> scannableCodes;
        private StowedProgress stowed;

        private Builder() {}

        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder stockRef(String stockRef) { this.stockRef = stockRef; return this; }
        public Builder storageLocationRef(StorageLocationId storageLocationRef) { this.storageLocationRef = storageLocationRef; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder stowed(StowedProgress stowed) { this.stowed = stowed; return this; }

        public StowLineItemStowToForCreation build() {
            return new StowLineItemStowToForCreation(quantity, type, stockRef, storageLocationRef, scannableCodes, stowed);
        }
    }
}
