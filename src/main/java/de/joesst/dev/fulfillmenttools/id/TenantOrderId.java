package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Customer-defined identifier for an order ({@code order.tenantOrderId} in the API).
 *
 * @param value the tenant-supplied order identifier
 */
public record TenantOrderId(String value) implements TenantId {
    public TenantOrderId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
