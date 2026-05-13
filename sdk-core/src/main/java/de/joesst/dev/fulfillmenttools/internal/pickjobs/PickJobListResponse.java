package de.joesst.dev.fulfillmenttools.internal.pickjobs;

import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;

import java.util.List;

record PickJobListResponse(List<PickJob> pickJobs, String nextCursor) {}
