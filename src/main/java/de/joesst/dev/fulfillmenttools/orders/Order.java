package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a customer order in fulfillmenttools.
 */
public record Order(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantOrderId,
        String status,
        String processId,
        Instant orderDate,
        List<OrderLineItem> orderLineItems,
        Map<String, Object> customAttributes,
        Boolean anonymized
) {}
