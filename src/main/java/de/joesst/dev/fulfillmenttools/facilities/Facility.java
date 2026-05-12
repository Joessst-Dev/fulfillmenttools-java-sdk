package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record Facility(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantFacilityId,
        String name,
        String status,
        String type,
        String locationType,
        FacilityAddress address,
        FacilityContact contact,
        List<FacilityService> services,
        List<String> pickingMethods,
        PickingTimes pickingTimes,
        List<ClosingDay> closingDays,
        ScanningRuleConfiguration scanningRule,
        Boolean capacityEnabled,
        Integer capacityPlanningTimeframe,
        Integer fulfillmentProcessBuffer,
        List<FacilityOperativeCost> operativeCosts,
        List<LinkedConfiguration> configs,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
