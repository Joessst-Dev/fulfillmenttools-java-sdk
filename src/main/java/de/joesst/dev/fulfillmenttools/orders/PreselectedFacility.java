package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

/**
 * A pre-selected facility reference for shipping delivery preferences.
 *
 * <p>Maps to the {@code PreselectedFacility} schema in the fulfillmenttools OpenAPI spec.
 * Either {@code facilityRef} or {@code tenantFacilityId} must be provided.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef      The fulfillmenttools {@link FacilityId} facility identifier.
 * @param tenantFacilityId The tenant-specific {@link TenantFacilityId} facility identifier.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreselectedFacility(
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId
) {}
