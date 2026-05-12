package de.joesst.dev.fulfillmenttools.internal.processes;

import java.util.List;

record ProcessSearchBody(List<String> facilityRefs, List<String> status, Integer size, String startAfterId) {}
