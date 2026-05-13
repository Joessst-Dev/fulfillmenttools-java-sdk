package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateFacilityGroupBody(
        Integer version,
        String tenantFacilityGroupId,
        List<String> facilityRefs,
        Map<String, String> nameLocalized,
        Map<String, Object> customAttributes
) {}
