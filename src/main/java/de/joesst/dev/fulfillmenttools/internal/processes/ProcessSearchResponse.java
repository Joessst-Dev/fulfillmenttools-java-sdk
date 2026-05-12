package de.joesst.dev.fulfillmenttools.internal.processes;

import de.joesst.dev.fulfillmenttools.processes.Process;

import java.util.List;

record ProcessSearchResponse(List<Process> processes, PageInfo pageInfo, Double total) {
    record PageInfo(String endCursor, String startCursor, Boolean hasNextPage, Boolean hasPreviousPage) {}
}
