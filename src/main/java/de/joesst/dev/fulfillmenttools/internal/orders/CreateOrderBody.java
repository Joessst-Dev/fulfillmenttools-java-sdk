package de.joesst.dev.fulfillmenttools.internal.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;
import de.joesst.dev.fulfillmenttools.orders.OrderForCreationConsumer;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderPaymentInfoForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderSource;
import de.joesst.dev.fulfillmenttools.orders.OrderStatusReason;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateOrderBody(
        Instant orderDate,
        List<OrderLineItemForCreation> orderLineItems,
        OrderForCreationConsumer consumer,
        String tenantOrderId,
        DeliveryPreferences deliveryPreferences,
        OrderPaymentInfoForCreation paymentInfo,
        List<TagReference> tags,
        List<Sticker> stickers,
        List<OrderStatusReason> statusReasons,
        OrderSource source,
        Map<String, Object> customAttributes
) {}
