package de.joesst.dev.fulfillmenttools.processes;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface OperativeProcessClient {

    Process get(String processId);
    CompletableFuture<Process> getAsync(String processId);

    Page<Process> list(ProcessListRequest request);
    CompletableFuture<Page<Process>> listAsync(ProcessListRequest request);

    Iterable<Process> listAll(ProcessListRequest request);

    Page<Process> search(ProcessSearchRequest request);
    CompletableFuture<Page<Process>> searchAsync(ProcessSearchRequest request);
}
