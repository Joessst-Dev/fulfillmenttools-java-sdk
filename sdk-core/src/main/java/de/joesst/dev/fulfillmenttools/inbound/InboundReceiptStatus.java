package de.joesst.dev.fulfillmenttools.inbound;

/**
 * The lifecycle status of an inbound receipt.
 *
 * <p>Maps to the {@code InboundReceipt.status} field in the fulfillmenttools OpenAPI specification.
 */
public enum InboundReceiptStatus {
    OPEN,
    IN_PROGRESS,
    FINISHED
}
