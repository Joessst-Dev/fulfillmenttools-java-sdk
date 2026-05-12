package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The source of an order, identifying the external system that created it.
 *
 * <p>Maps to the {@code source} field on {@code OrderForCreation} in the fulfillmenttools
 * OpenAPI spec, which is a {@code oneOf} between {@code SourceRef} and {@code TenantSourceId}.
 *
 * <p>Use {@link BySourceRef} when you have a fulfillmenttools source reference ID, or
 * {@link ByTenantSourceId} when you have a tenant-specific source identifier.
 *
 * <p>Thread-safety: immutable sealed hierarchy; safe for concurrent use.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderSource.BySourceRef.class),
        @JsonSubTypes.Type(value = OrderSource.ByTenantSourceId.class)
})
public sealed interface OrderSource
        permits OrderSource.BySourceRef, OrderSource.ByTenantSourceId {

    /**
     * Order source identified by a fulfillmenttools {@code sourceRef}.
     *
     * @param sourceRef The fulfillmenttools source reference identifier.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record BySourceRef(String sourceRef) implements OrderSource {}

    /**
     * Order source identified by a tenant-specific source identifier.
     *
     * @param tenantSourceId The tenant's own source system identifier.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record ByTenantSourceId(String tenantSourceId) implements OrderSource {}
}
