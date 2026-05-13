package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;

import java.util.List;

record FacilityGroupSearchResponse(List<FacilityGroup> facilityGroups, PageInfoDto pageInfo, Integer total) {
    record PageInfoDto(String endCursor, Boolean hasNextPage, Boolean hasPreviousPage, String startCursor) {}
}
