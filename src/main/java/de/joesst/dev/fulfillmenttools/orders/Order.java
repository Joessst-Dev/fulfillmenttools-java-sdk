package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record Order(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantOrderId,
        String status,
        String processId,
        Double schemaVersion,
        Instant orderDate,
        List<OrderLineItem> orderLineItems,
        OrderForCreationConsumer consumer,
        Map<String, Object> deliveryPreferences,
        OrderPaymentInfo paymentInfo,
        CancelationReason cancelationReason,
        List<Map<String, Object>> customServices,
        List<OrderUpdateDetail> updateDetails,
        List<TagReference> tags,
        List<Map<String, Object>> stickers,
        List<Map<String, Object>> statusReasons,
        Map<String, Object> source,
        Map<String, Object> customAttributes,
        Boolean anonymized
) {}
