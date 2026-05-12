package de.joesst.dev.fulfillmenttools.internal.eventing;

record CreateSubscriptionBody(String topic, String callbackUrl) {}
