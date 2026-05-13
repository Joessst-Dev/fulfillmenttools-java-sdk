package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record FacilityGroupSearchBody(FacilityGroupSearchQuery query, Integer size, String after, String before) {}
