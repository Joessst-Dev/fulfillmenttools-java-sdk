package de.joesst.dev.fulfillmenttools.purchaseorders;

/**
 * The lifecycle status of a purchase order.
 *
 * <p>Maps to the {@code PurchaseOrder.status} field in the fulfillmenttools OpenAPI specification.
 */
public enum PurchaseOrderStatus {
    OPEN,
    CANCELED
}
