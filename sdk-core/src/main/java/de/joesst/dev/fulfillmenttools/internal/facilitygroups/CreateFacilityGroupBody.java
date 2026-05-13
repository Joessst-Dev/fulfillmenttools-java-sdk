package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import java.util.List;
import java.util.Map;

record CreateFacilityGroupBody(
        String tenantFacilityGroupId,
        List<String> facilityRefs,
        Map<String, String> nameLocalized,
        Map<String, Object> customAttributes
) {}
