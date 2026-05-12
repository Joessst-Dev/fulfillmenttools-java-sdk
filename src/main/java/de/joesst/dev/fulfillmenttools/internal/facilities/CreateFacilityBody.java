package de.joesst.dev.fulfillmenttools.internal.facilities;

import java.util.Map;

record CreateFacilityBody(
        String name,
        String tenantFacilityId,
        String status,
        String type,
        Map<String, Object> customAttributes
) {}
