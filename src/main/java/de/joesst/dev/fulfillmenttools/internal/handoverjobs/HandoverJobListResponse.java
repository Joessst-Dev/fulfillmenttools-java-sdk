package de.joesst.dev.fulfillmenttools.internal.handoverjobs;

import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJob;

import java.util.List;

record HandoverJobListResponse(List<HandoverJob> handoverJobs, String nextCursor) {}
