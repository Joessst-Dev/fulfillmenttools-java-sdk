package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A reason explaining why an order is in a particular status.
 *
 * <p>Maps to the {@code OrderStatusReason} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reason The human-readable reason text.
 * @param status The order status this reason is associated with.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderStatusReason(
        String reason,
        String status
) {}
