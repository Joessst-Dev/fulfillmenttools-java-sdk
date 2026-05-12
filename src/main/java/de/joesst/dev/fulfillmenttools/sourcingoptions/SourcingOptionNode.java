package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;
import java.util.Map;

public record SourcingOptionNode(
        String id,
        String facilityRef,
        String tenantFacilityId,
        String type,
        Boolean isPickUpLocation,
        List<Map<String, Object>> lineItems
) {}
