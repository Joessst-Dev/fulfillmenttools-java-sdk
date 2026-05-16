package de.joesst.dev.fulfillmenttools.internal.facilitygroups;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Map;

record CreateFacilityGroupBody(
        String tenantFacilityGroupId,
        List<String> facilityRefs,
        Map<String, String> nameLocalized,
        CustomAttributes customAttributes
) {}
