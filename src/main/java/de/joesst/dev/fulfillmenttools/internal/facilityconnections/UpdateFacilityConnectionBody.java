package de.joesst.dev.fulfillmenttools.internal.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateFacilityConnectionBody(
        Integer version,
        String type,
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
