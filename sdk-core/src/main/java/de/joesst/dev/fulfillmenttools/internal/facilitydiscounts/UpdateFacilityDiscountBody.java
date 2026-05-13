package de.joesst.dev.fulfillmenttools.internal.facilitydiscounts;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountContext;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateFacilityDiscountBody(
        Integer version,
        String type,
        Integer priority,
        FacilityDiscountValue discount,
        List<FacilityDiscountContext> context
) {}
