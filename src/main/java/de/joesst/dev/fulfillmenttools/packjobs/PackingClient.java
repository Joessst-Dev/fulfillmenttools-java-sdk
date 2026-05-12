package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface PackingClient {

    PackJob get(String packJobId);
    CompletableFuture<PackJob> getAsync(String packJobId);

    Page<PackJob> list(PackJobListRequest request);
    CompletableFuture<Page<PackJob>> listAsync(PackJobListRequest request);

    Iterable<PackJob> listAll(PackJobListRequest request);

    PackJob update(String packJobId, UpdatePackJobRequest request);
    CompletableFuture<PackJob> updateAsync(String packJobId, UpdatePackJobRequest request);
}
