package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.concurrent.CompletableFuture;

public interface SourcingOptionsClient {

    SourcingOptionsResult evaluate(SourcingOptionsRequest request);
    CompletableFuture<SourcingOptionsResult> evaluateAsync(SourcingOptionsRequest request);

    SourcingOptionsResult get(String sourcingOptionsRequestId);
    CompletableFuture<SourcingOptionsResult> getAsync(String sourcingOptionsRequestId);
}
