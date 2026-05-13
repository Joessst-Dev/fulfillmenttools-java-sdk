package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

import java.util.List;

/**
 * Instructions on how and where to take an article from before stowing it.
 *
 * <p>Maps to the {@code StowLineItemTakeFrom} and {@code StowLineItemTakeFromForCreation}
 * schemas in the fulfillmenttools OpenAPI spec. The same structure is used for both the
 * read response and the creation/update request body.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param quantity           Quantity that should be taken.
 * @param type               Source type: {@code STOCK}, {@code LOCATION}, or {@code UNREGISTERED}.
 *                           Use {@code UNREGISTERED} when booking a new item into the system.
 * @param stockRef           Reference to the stock from which the item should be taken.
 *                           Leave {@code null} for type {@code UNREGISTERED}.
 * @param storageLocationRef Reference to the storage location from which the item should be taken.
 * @param scannableCodes     Barcodes relevant for taking the item.
 * @param stockInformation   Additional stock information. Only allowed when type is
 *                           {@code UNREGISTERED}.
 * @param taken              Operative progress data tracking what has been taken so far.
 */
public record StowLineItemTakeFrom(
        Integer quantity,
        String type,
        String stockRef,
        StorageLocationId storageLocationRef,
        List<String> scannableCodes,
        StowJobStockInformation stockInformation,
        TakenProgress taken
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
        private StowJobStockInformation stockInformation;
        private TakenProgress taken;

        private Builder() {}

        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder stockRef(String stockRef) { this.stockRef = stockRef; return this; }
        public Builder storageLocationRef(StorageLocationId storageLocationRef) { this.storageLocationRef = storageLocationRef; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder stockInformation(StowJobStockInformation stockInformation) { this.stockInformation = stockInformation; return this; }
        public Builder taken(TakenProgress taken) { this.taken = taken; return this; }

        public StowLineItemTakeFrom build() {
            return new StowLineItemTakeFrom(quantity, type, stockRef, storageLocationRef, scannableCodes, stockInformation, taken);
        }
    }
}
