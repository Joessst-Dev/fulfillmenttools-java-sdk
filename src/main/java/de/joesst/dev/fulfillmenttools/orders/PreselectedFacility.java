package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A pre-selected facility reference for shipping delivery preferences.
 *
 * <p>Maps to the {@code PreselectedFacility} schema in the fulfillmenttools OpenAPI spec.
 * Either {@code facilityRef} or {@code tenantFacilityId} must be provided.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef      The fulfillmenttools facility ID.
 * @param tenantFacilityId The tenant-specific facility identifier.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreselectedFacility(
        String facilityRef,
        String tenantFacilityId
) {}
