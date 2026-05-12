package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.orders.OrderForCreationConsumer;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForUpdate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

record UpdateOrderBody(
        Integer version,
        String comment,
        OrderForCreationConsumer consumer,
        Map<String, Object> customAttributes,
        List<OrderLineItemForUpdate> orderLineItems,
        Instant preferredHandlingTime
) {}
