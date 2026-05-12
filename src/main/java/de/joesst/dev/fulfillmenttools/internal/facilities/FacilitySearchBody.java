package de.joesst.dev.fulfillmenttools.internal.facilities;

import de.joesst.dev.fulfillmenttools.facilities.FacilitySearchQuery;

record FacilitySearchBody(FacilitySearchQuery query, Integer size, String after, String before) {}
