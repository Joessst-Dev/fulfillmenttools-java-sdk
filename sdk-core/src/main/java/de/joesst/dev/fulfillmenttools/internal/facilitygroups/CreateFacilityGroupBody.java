package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

record CreateFacilityGroupBody(
        String tenantFacilityGroupId,
        List<String> facilityRefs,
        Map<String, String> nameLocalized,
        CustomAttributes customAttributes
) {}
