package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a facility in the fulfillmenttools platform.
 *
 * @param id unique identifier assigned by the system
 * @param version optimistic-locking version number
 * @param created creation timestamp in UTC
 * @param lastModified last modification timestamp in UTC
 * @param tenantFacilityId optional tenant-provided facility identifier
 * @param name human-readable name of the facility
 * @param status operational status (e.g. "ONLINE", "SUSPENDED", "OFFLINE")
 * @param type facility type (e.g. "MANAGED_FACILITY", "SUPPLIER")
 * @param locationType location classification (e.g. "STORE", "WAREHOUSE", "EXTERNAL")
 * @param address physical address of the facility
 * @param contact primary contact person for the facility
 * @param services list of fulfillment services offered by the facility
 * @param pickingMethods list of picking strategies supported
 * @param pickingTimes operating hours for picking operations
 * @param closingDays periods when the facility is unavailable
 * @param scanningRule scanning rule configuration for items
 * @param capacityEnabled whether capacity planning is enabled
 * @param capacityPlanningTimeframe planning horizon in days
 * @param fulfillmentProcessBuffer time buffer for fulfillment processing
 * @param operativeCosts operational cost estimates
 * @param configs linked configuration references
 * @param tags business tags for categorization
 * @param customAttributes free-form custom attributes
 */
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
