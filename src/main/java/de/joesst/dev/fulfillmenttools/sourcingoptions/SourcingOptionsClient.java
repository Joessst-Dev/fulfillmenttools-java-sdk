package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.concurrent.CompletableFuture;

public interface SourcingOptionsClient {

    SourcingOptionsResult evaluate(EvaluateSourcingOptionsRequest request);
    CompletableFuture<SourcingOptionsResult> evaluateAsync(EvaluateSourcingOptionsRequest request);
}
