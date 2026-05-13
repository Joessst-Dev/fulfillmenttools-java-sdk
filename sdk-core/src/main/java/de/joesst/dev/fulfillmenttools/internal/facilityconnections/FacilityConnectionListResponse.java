package de.joesst.dev.fulfillmenttools.internal.facilityconnections;

import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;

import java.util.List;

record FacilityConnectionListResponse(List<FacilityConnection> interFacilityConnections, Integer total) {}
