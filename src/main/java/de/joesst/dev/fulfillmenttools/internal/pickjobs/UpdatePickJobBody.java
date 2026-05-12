package de.joesst.dev.fulfillmenttools.internal.pickjobs;

import java.util.List;
import java.util.Map;

record UpdatePickJobBody(String status, Map<String, Object> customAttributes, List<String> preferredPickingMethods) {}
