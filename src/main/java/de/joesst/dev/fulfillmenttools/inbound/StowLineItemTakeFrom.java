package de.joesst.dev.fulfillmenttools.inbound;

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
        String storageLocationRef,
        List<String> scannableCodes,
        StowJobStockInformation stockInformation,
        TakenProgress taken
) {}
