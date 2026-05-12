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

    PickJob abort(String pickJobId, int version);
    CompletableFuture<PickJob> abortAsync(String pickJobId, int version);

    PickJob restart(String pickJobId, int version);
    CompletableFuture<PickJob> restartAsync(String pickJobId, int version);

    PickJob reset(String pickJobId, int version);
    CompletableFuture<PickJob> resetAsync(String pickJobId, int version);

    PickJob obsolete(String pickJobId, int version);
    CompletableFuture<PickJob> obsoleteAsync(String pickJobId, int version);

    PickJob start(String pickJobId, int version);
    CompletableFuture<PickJob> startAsync(String pickJobId, int version);

    PickJob pause(String pickJobId, int version);
    CompletableFuture<PickJob> pauseAsync(String pickJobId, int version);
}
