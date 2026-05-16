package de.joesst.dev.fulfillmenttools.internal.facilities;

import de.joesst.dev.fulfillmenttools.facilities.ClosingDay;
import de.joesst.dev.fulfillmenttools.facilities.FacilityAddress;
import de.joesst.dev.fulfillmenttools.facilities.FacilityContact;
import de.joesst.dev.fulfillmenttools.facilities.FacilityOperativeCost;
import de.joesst.dev.fulfillmenttools.facilities.PickingTimes;
import de.joesst.dev.fulfillmenttools.facilities.ScanningRuleConfiguration;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

record UpdateFacilityBody(
        String name,
        String tenantFacilityId,
        String status,
        String type,
        String locationType,
        FacilityAddress address,
        FacilityContact contact,
        List<String> pickingMethods,
        PickingTimes pickingTimes,
        List<ClosingDay> closingDays,
        ScanningRuleConfiguration scanningRule,
        Boolean capacityEnabled,
        Integer capacityPlanningTimeframe,
        Integer fulfillmentProcessBuffer,
        List<FacilityOperativeCost> operativeCosts,
        List<TagReference> tags,
        CustomAttributes customAttributes
) {}
