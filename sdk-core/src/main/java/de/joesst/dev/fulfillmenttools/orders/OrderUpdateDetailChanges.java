package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents the specific changes made in an order update event.
 *
 * @param consumer the consumer information that was changed
 * @param customAttributes custom attributes that were changed
 * @param orderLineItems the order line items that were changed
 * @param preferredHandlingTime the preferred handling time that was changed
 */
public record OrderUpdateDetailChanges(
        OrderForCreationConsumer consumer,
        Map<String, Object> customAttributes,
        List<OrderLineItemForUpdate> orderLineItems,
        Instant preferredHandlingTime
) {}
