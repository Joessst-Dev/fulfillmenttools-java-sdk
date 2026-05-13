package de.joesst.dev.fulfillmenttools.internal.processes;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record ProcessSearchBody(ProcessSearchQuery query, Integer size, String after, String before, Integer last) {}
