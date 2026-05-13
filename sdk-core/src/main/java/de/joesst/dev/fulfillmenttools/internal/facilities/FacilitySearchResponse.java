package de.joesst.dev.fulfillmenttools.internal.facilities;

import de.joesst.dev.fulfillmenttools.facilities.Facility;

import java.util.List;

record FacilitySearchResponse(List<Facility> facilities, PageInfo pageInfo, Integer total) {
    record PageInfo(String endCursor, String startCursor, Boolean hasNextPage, Boolean hasPreviousPage) {}
}
