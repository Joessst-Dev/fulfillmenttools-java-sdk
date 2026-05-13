package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;

/**
 * A transfer step between two nodes within a sourcing option, describing stock movement
 * from a supplying facility to a receiving node.
 *
 * <p>Maps to the {@code SourcingOptionTransfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id              Unique identifier of this transfer.
 * @param sourceNodeRef   Reference to the source node that provides the stock.
 * @param targetNodeRef   Reference to the target node that receives the stock.
 * @param lineItemRefs    References to the line items involved in this transfer.
 */
public record SourcingOptionTransfer(
        String id,
        String sourceNodeRef,
        String targetNodeRef,
        List<String> lineItemRefs
) {}
