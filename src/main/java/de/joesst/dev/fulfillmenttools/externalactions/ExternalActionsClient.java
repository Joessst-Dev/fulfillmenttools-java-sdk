package de.joesst.dev.fulfillmenttools.externalactions;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface ExternalActionsClient {

    ExternalAction get(String externalActionId);
    CompletableFuture<ExternalAction> getAsync(String externalActionId);

    Page<ExternalAction> list(ExternalActionListRequest request);
    CompletableFuture<Page<ExternalAction>> listAsync(ExternalActionListRequest request);

    Iterable<ExternalAction> listAll(ExternalActionListRequest request);

    ExternalAction create(CreateExternalActionRequest request);
    CompletableFuture<ExternalAction> createAsync(CreateExternalActionRequest request);

    ExternalAction update(String externalActionId, UpdateExternalActionRequest request);
    CompletableFuture<ExternalAction> updateAsync(String externalActionId, UpdateExternalActionRequest request);
}
