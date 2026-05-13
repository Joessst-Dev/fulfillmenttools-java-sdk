package de.joesst.dev.fulfillmenttools.internal.eventing;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.eventing.CallbackHeader;
import de.joesst.dev.fulfillmenttools.eventing.SubscriptionContext;
import de.joesst.dev.fulfillmenttools.eventing.SubscriptionTarget;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateSubscriptionBody(
        String name,
        String event,
        String callbackUrl,
        List<SubscriptionContext> contexts,
        List<CallbackHeader> headers,
        SubscriptionTarget target
) {}
