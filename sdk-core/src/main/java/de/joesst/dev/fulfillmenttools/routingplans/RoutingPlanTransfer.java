package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * Describes a transfer relationship associated with a routing plan.
 *
 * <p>Maps to the {@code Transfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Known values for {@code transferType}: {@code SUPPLIER}, {@code RECEIVER}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param transferId   The identifier of the transfer.
 * @param transferType The role of this routing plan in the transfer, either
 *                     {@code "SUPPLIER"} or {@code "RECEIVER"}.
 */
public record RoutingPlanTransfer(
        String transferId,
        String transferType
) {}
