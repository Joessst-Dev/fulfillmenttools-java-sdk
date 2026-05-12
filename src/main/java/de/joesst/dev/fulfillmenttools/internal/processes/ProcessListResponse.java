package de.joesst.dev.fulfillmenttools.internal.processes;

import de.joesst.dev.fulfillmenttools.processes.Process;

import java.util.List;

record ProcessListResponse(List<Process> processes, String nextCursor) {}
