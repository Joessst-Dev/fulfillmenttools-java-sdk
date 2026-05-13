package de.joesst.dev.fulfillmenttools.internal.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;

import java.util.List;

record FacilityDiscountListResponse(List<FacilityDiscount> items, Integer total) {}
