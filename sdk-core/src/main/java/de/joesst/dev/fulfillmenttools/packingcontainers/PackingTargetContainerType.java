package de.joesst.dev.fulfillmenttools.packingcontainers;

/**
 * The physical nature of a packing target container.
 *
 * <p>Maps to the {@code PackingTargetContainerType} schema in the fulfillmenttools OpenAPI specification.
 */
public enum PackingTargetContainerType {
    /** A real physical container (e.g. a box or bag). */
    PHYSICAL,
    /** A virtual grouping without a physical counterpart. */
    VIRTUAL
}
