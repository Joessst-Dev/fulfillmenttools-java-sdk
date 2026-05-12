package de.joesst.dev.fulfillmenttools.internal.eventing;

import de.joesst.dev.fulfillmenttools.eventing.Subscription;

import java.util.List;

record SubscriptionListResponse(List<Subscription> subscriptions, String nextCursor) {}
