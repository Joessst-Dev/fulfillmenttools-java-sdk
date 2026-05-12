package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import java.time.Instant;
import java.util.List;

public record FacilityDiscount(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String type,
        Integer priority,
        FacilityDiscountValue discount,
        List<FacilityDiscountContext> context
) {}
