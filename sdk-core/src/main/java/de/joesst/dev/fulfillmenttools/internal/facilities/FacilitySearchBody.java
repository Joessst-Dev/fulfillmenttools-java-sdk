package de.joesst.dev.fulfillmenttools.internal.facilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.facilities.FacilitySearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record FacilitySearchBody(FacilitySearchQuery query, Integer size, String after, String before, Integer last) {}
