package de.joesst.dev.fulfillmenttools.eventing;

import java.time.Instant;

/**
 * Represents an event subscription in fulfillmenttools.
 */
public record Subscription(
        String id,
        Integer version,
        Instant created,
        String name,
        String event,
        String callbackUrl,
        String status
) {}
