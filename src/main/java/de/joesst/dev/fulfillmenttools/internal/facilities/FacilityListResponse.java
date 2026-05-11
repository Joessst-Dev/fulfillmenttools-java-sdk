package de.joesst.dev.fulfillmenttools.internal.facilities;

import de.joesst.dev.fulfillmenttools.facilities.Facility;

import java.util.List;

record FacilityListResponse(List<Facility> facilities, String nextCursor) {}
