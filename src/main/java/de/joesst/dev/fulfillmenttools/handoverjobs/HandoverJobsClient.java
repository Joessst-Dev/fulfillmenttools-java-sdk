package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface HandoverJobsClient {

    HandoverJob get(String handoverJobId);
    CompletableFuture<HandoverJob> getAsync(String handoverJobId);

    Page<HandoverJob> list(HandoverJobListRequest request);
    CompletableFuture<Page<HandoverJob>> listAsync(HandoverJobListRequest request);

    Iterable<HandoverJob> listAll(HandoverJobListRequest request);

    HandoverJob update(String handoverJobId, UpdateHandoverJobRequest request);
    CompletableFuture<HandoverJob> updateAsync(String handoverJobId, UpdateHandoverJobRequest request);
}
