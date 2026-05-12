package de.joesst.dev.fulfillmenttools.eventing;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record Subscription(
        String id,
        Instant created,
        String name,
        String event,
        String callbackUrl,
        List<SubscriptionContext> contexts,
        List<CallbackHeader> headers,
        Map<String, Object> target
) {}
