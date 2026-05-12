package de.joesst.dev.fulfillmenttools.internal.eventing;

import de.joesst.dev.fulfillmenttools.eventing.CallbackHeader;
import de.joesst.dev.fulfillmenttools.eventing.SubscriptionContext;

import java.util.List;
import java.util.Map;

record CreateSubscriptionBody(
        String name,
        String event,
        String callbackUrl,
        List<SubscriptionContext> contexts,
        List<CallbackHeader> headers,
        Map<String, Object> target
) {}
