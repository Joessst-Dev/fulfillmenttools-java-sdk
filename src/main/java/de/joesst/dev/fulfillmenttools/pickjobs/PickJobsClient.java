package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface PickJobsClient {

    PickJob get(String pickJobId);
    CompletableFuture<PickJob> getAsync(String pickJobId);

    Page<PickJob> list(PickJobListRequest request);
    CompletableFuture<Page<PickJob>> listAsync(PickJobListRequest request);

    Iterable<PickJob> listAll(PickJobListRequest request);

    PickJob update(String pickJobId, UpdatePickJobRequest request);
    CompletableFuture<PickJob> updateAsync(String pickJobId, UpdatePickJobRequest request);
}
