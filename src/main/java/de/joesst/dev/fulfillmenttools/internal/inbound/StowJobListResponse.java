package de.joesst.dev.fulfillmenttools.internal.inbound;

import de.joesst.dev.fulfillmenttools.inbound.StowJob;

import java.util.List;

record StowJobListResponse(List<StowJob> stowJobs, String nextCursor) {}
