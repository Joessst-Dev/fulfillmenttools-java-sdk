package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;
import java.util.Map;

public record SourcingOption(
        String id,
        String runId,
        Double totalPenalty,
        String estimatedDeliveryDate,
        String estimatedPickupDate,
        String validUntil,
        List<SourcingOptionNode> nodes,
        List<Map<String, Object>> transfers,
        List<Map<String, Object>> listingDetails,
        List<Map<String, Object>> nonAssignedOrderLineItems,
        List<Map<String, Object>> ratingResults,
        Map<String, Object> totalCosts
) {}
