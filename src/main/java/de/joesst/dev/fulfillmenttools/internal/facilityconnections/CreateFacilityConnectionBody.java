package de.joesst.dev.fulfillmenttools.internal.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.facilityconnections.CarrierTransitTime;
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionContext;
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;
import de.joesst.dev.fulfillmenttools.facilityconnections.CutoffTimes;
import de.joesst.dev.fulfillmenttools.facilityconnections.DeliveryCost;
import de.joesst.dev.fulfillmenttools.facilityconnections.NonDeliveryDaysPerCountry;
import de.joesst.dev.fulfillmenttools.facilityconnections.PackagingUnitsByContext;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateFacilityConnectionBody(
        String type,
        ConnectionTarget target,
        String carrierKey,
        String carrierName,
        List<ConnectionContext> context,
        List<DeliveryCost> fallbackCosts,
        List<NonDeliveryDaysPerCountry> nonDeliveryDays,
        List<PackagingUnitsByContext> packagingUnitsByContexts,
        CutoffTimes cutoffTimes,
        CarrierTransitTime fallbackTransitTime,
        Map<String, Object> customAttributes
) {}
