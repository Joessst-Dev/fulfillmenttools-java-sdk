package de.joesst.dev.fulfillmenttools.internal.orders;

record OrderForceCancelActionBody(String name, Integer version) {
    OrderForceCancelActionBody(Integer version) {
        this("FORCE_CANCEL", version);
    }
}
