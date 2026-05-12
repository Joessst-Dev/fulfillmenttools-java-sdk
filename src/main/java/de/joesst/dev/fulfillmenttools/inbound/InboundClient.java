package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface InboundClient {

    StowJob get(String stowJobId);
    CompletableFuture<StowJob> getAsync(String stowJobId);

    Page<StowJob> list(StowJobListRequest request);
    CompletableFuture<Page<StowJob>> listAsync(StowJobListRequest request);

    Iterable<StowJob> listAll(StowJobListRequest request);

    StowJob create(CreateStowJobRequest request);
    CompletableFuture<StowJob> createAsync(CreateStowJobRequest request);

    StowJob update(String stowJobId, UpdateStowJobRequest request);
    CompletableFuture<StowJob> updateAsync(String stowJobId, UpdateStowJobRequest request);

    StowJob start(String stowJobId, int version);
    CompletableFuture<StowJob> startAsync(String stowJobId, int version);

    StowJob pause(String stowJobId, int version);
    CompletableFuture<StowJob> pauseAsync(String stowJobId, int version);

    StowJob cancel(String stowJobId, int version);
    CompletableFuture<StowJob> cancelAsync(String stowJobId, int version);

    StowJob reopen(String stowJobId, int version);
    CompletableFuture<StowJob> reopenAsync(String stowJobId, int version);

    StowJob close(String stowJobId, int version);
    CompletableFuture<StowJob> closeAsync(String stowJobId, int version);
}
