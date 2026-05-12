package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record OrderUpdateDetailChanges(
        OrderForCreationConsumer consumer,
        Map<String, Object> customAttributes,
        List<OrderLineItemForUpdate> orderLineItems,
        Instant preferredHandlingTime
) {}
