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
) {}
