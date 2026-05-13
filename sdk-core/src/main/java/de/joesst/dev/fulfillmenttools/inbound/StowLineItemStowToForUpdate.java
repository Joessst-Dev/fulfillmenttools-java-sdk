package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

import java.util.List;

/**
 * A stow-to instruction used when updating a stow line item.
 *
 * <p>Extends {@link StowLineItemStowToForCreation} with an optional {@code id} field
 * to reference an existing stow-to instruction.
 *
 * <p>Maps to the {@code StowLineItemStowToForUpdate} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                 Optional reference to an existing stow-to instruction.
 * @param quantity           Quantity that should be stowed.
 * @param type               Destination type: {@code STOCK}, {@code LOCATION}, or
 *                           {@code DISCARD}.
 * @param stockRef           Reference to the target stock. Leave {@code null} for types
 *                           {@code LOCATION} or {@code DISCARD}.
 * @param storageLocationRef Reference to the target storage location.
 * @param scannableCodes     Barcodes relevant for stowing the item.
 * @param stowed             Optional operative progress data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StowLineItemStowToForUpdate(
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

        public StowLineItemStowToForUpdate build() {
            return new StowLineItemStowToForUpdate(id, quantity, type, stockRef, storageLocationRef, scannableCodes, stowed);
        }
    }
}
