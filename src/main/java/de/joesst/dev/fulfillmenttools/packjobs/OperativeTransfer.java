package de.joesst.dev.fulfillmenttools.packjobs;

/**
 * A transfer associated with a pack job, identifying either a supplier or receiver party.
 *
 * <p>Maps to the {@code OperativeTransfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id   Unique identifier of the transfer.
 * @param type The role of this transfer in the pack job: {@code SUPPLIER} or {@code RECEIVER}.
 */
public record OperativeTransfer(
        String id,
        String type
) {}
