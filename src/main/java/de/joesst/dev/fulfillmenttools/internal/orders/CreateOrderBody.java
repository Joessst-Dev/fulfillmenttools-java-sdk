package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.orders.OrderForCreationConsumer;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;

import java.time.Instant;
import java.util.List;
import java.util.Map;

record CreateOrderBody(
        Instant orderDate,
        List<OrderLineItemForCreation> orderLineItems,
        String tenantOrderId,
        OrderForCreationConsumer consumer,
        Map<String, Object> customAttributes
) {}
