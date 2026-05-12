package de.joesst.dev.fulfillmenttools.internal.handoverjobs;

record HandoverJobCancelBody(String name, Integer version, Payload payload) {

    record Payload(String handoverJobCancelReason) {}

    HandoverJobCancelBody(Integer version, String cancelReason) {
        this("CANCEL", version, new Payload(cancelReason));
    }
}
