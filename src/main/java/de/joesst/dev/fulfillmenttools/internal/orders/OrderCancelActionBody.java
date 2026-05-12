package de.joesst.dev.fulfillmenttools.internal.orders;

record OrderCancelActionBody(String name, Integer version, String cancelationReasonId) {
    OrderCancelActionBody(Integer version, String cancelationReasonId) {
        this("CANCEL", version, cancelationReasonId);
    }
}
