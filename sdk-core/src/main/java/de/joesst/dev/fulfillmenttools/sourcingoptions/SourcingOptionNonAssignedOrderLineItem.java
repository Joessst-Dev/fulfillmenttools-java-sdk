package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * An order line item that could not be assigned to any node within a sourcing option.
 *
 * <p>Maps to the non-assigned line item schema referenced in {@code SourcingOption}
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param orderLineItemRef  Reference to the original order line item.
 * @param reason            The reason why this line item could not be assigned.
 */
public record SourcingOptionNonAssignedOrderLineItem(
        String orderLineItemRef,
        String reason
) {}
