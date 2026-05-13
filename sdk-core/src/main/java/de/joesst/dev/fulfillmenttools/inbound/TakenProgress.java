package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;

/**
 * Operative data tracking how much of a take-from instruction has been executed.
 *
 * <p>Maps to the {@code TakenStowLineItemTakeFromForCreation} schema in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param takenAt       Timestamp when the item was taken.
 * @param takenQuantity Quantity that was actually taken.
 * @param takenStockRef Reference to the stock from which the goods were taken.
 *                      May be {@code null} when type is {@code UNREGISTERED}.
 */
public record TakenProgress(
        Instant takenAt,
        Integer takenQuantity,
        String takenStockRef
) {}
