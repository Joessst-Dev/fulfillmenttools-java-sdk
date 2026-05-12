package de.joesst.dev.fulfillmenttools.facilitygroups;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record FacilityGroup(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantFacilityGroupId,
        List<String> facilityRefs,
        Map<String, Object> nameLocalized,
        String name,
        Map<String, Object> customAttributes
) {}
