package de.joesst.dev.fulfillmenttools.eventing;

public record Subscription(
        String id,
        String topic,
        String callbackUrl,
        String status
) {}
