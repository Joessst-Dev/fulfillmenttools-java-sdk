package de.joesst.dev.fulfillmenttools.facilitygroups;

import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a facility group in the fulfillmenttools platform.
 *
 * @param id the facility group's unique {@link FacilityGroupId}
 * @param version the current version of the resource
 * @param created the timestamp when the facility group was created
 * @param lastModified the timestamp when the facility group was last updated
 * @param tenantFacilityGroupId the tenant-scoped identifier for this group
 * @param facilityRefs the list of {@link FacilityId} references in this group
 * @param nameLocalized the localized names of the group by language code
 * @param name the default name of the group
 * @param customAttributes additional attributes as a key-value map
 */
public record FacilityGroup(
        FacilityGroupId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantFacilityGroupId,
        List<FacilityId> facilityRefs,
        Map<String, String> nameLocalized,
        String name,
        Map<String, Object> customAttributes
) {}
