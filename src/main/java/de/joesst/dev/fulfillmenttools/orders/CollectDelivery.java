package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Click-and-collect delivery configuration for an order.
 *
 * <p>Maps to the {@code CollectDelivery} schema in the fulfillmenttools OpenAPI spec.
 * Either {@code facilityRef} or {@code tenantFacilityId} must be specified.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef     The fulfillmenttools facility ID where the consumer will collect.
 * @param tenantFacilityId The tenant-specific facility identifier.
 * @param paid            Whether the order is already paid. Defaults to {@code false}.
 * @param provisioningTime The target time by which the order must be provisioned.
 * @param supplyingFacilities Deprecated: use {@code supplyingFacilitiesConfigurations}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CollectDelivery(
        String facilityRef,
        String tenantFacilityId,
        Boolean paid,
        Instant provisioningTime,
        List<String> supplyingFacilities
) {}
