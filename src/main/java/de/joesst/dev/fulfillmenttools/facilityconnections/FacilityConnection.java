package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record FacilityConnection(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String sourceFacilityRef,
        Map<String, Object> target,
        String carrierKey,
        String carrierName,
        List<Map<String, Object>> context,
        List<Map<String, Object>> fallbackCosts,
        List<Map<String, Object>> nonDeliveryDays,
        List<Map<String, Object>> packagingUnitsByContexts,
        Map<String, Object> cutoffTimes,
        Map<String, Object> fallbackTransitTime,
        Map<String, Object> customAttributes
) {}
