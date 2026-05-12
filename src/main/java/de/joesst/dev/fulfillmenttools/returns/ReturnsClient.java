package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface ReturnsClient {

    Return get(String returnId);
    CompletableFuture<Return> getAsync(String returnId);

    Page<Return> list(ReturnListRequest request);
    CompletableFuture<Page<Return>> listAsync(ReturnListRequest request);

    Iterable<Return> listAll(ReturnListRequest request);

    Return create(CreateReturnRequest request);
    CompletableFuture<Return> createAsync(CreateReturnRequest request);

    Return start(String returnId, int version);
    CompletableFuture<Return> startAsync(String returnId, int version);

    Return finish(String returnId, int version);
    CompletableFuture<Return> finishAsync(String returnId, int version);

    Return restart(String returnId, int version);
    CompletableFuture<Return> restartAsync(String returnId, int version);
}
