package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.OrderForCreationConsumer;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderPaymentInfoForCreation;

import java.time.Instant;
import java.util.List;
import java.util.Map;

record CreateOrderBody(
        Instant orderDate,
        List<OrderLineItemForCreation> orderLineItems,
        OrderForCreationConsumer consumer,
        String tenantOrderId,
        Map<String, Object> deliveryPreferences,
        OrderPaymentInfoForCreation paymentInfo,
        List<TagReference> tags,
        List<Map<String, Object>> stickers,
        List<Map<String, Object>> statusReasons,
        Map<String, Object> source,
        Map<String, Object> customAttributes
) {}
