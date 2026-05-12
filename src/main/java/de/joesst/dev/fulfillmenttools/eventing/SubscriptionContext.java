package de.joesst.dev.fulfillmenttools.eventing;

import java.util.List;

public record SubscriptionContext(String type, List<String> values) {}
