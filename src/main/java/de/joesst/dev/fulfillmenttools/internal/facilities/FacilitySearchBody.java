package de.joesst.dev.fulfillmenttools.internal.facilities;

import java.util.Map;

record FacilitySearchBody(Map<String, Object> query, Integer size, String after, String before) {}
