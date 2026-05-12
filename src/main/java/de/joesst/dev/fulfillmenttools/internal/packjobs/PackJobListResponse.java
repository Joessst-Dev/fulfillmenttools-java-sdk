package de.joesst.dev.fulfillmenttools.internal.packjobs;

import de.joesst.dev.fulfillmenttools.packjobs.PackJob;

import java.util.List;

record PackJobListResponse(List<PackJob> packJobs, String nextCursor) {}
