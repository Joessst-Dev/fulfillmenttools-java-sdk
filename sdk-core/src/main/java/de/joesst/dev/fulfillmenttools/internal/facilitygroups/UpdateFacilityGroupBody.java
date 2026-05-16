package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateFacilityGroupBody(
        Integer version,
        String tenantFacilityGroupId,
        List<String> facilityRefs,
        Map<String, String> nameLocalized,
        CustomAttributes customAttributes
) {}
