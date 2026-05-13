package de.joesst.dev.fulfillmenttools.externalactions;

import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing external actions in the fulfillmenttools platform.
 */
public interface ExternalActionsClient {

    /**
     * Retrieves an external action by ID.
     * @param externalActionId the external action identifier
     * @return the external action
     */
    ExternalAction get(ExternalActionId externalActionId);

    /**
     * Asynchronously retrieves an external action by ID.
     * @param externalActionId the external action identifier
     * @return a future containing the external action
     */
    CompletableFuture<ExternalAction> getAsync(ExternalActionId externalActionId);

    /**
     * Lists external actions with pagination.
     * @param request the list request with pagination parameters
     * @return a page of external actions
     */
    Page<ExternalAction> list(ExternalActionListRequest request);

    /**
     * Asynchronously lists external actions with pagination.
     * @param request the list request with pagination parameters
     * @return a future containing a page of external actions
     */
    CompletableFuture<Page<ExternalAction>> listAsync(ExternalActionListRequest request);

    /**
     * Lists all external actions, automatically handling pagination.
     * @param request the list request parameters
     * @return an iterable of all external actions
     */
    Iterable<ExternalAction> listAll(ExternalActionListRequest request);

    /**
     * Creates a new external action.
     * @param request the external action creation request
     * @return the created external action
     */
    ExternalAction create(CreateExternalActionRequest request);

    /**
     * Asynchronously creates a new external action.
     * @param request the external action creation request
     * @return a future containing the created external action
     */
    CompletableFuture<ExternalAction> createAsync(CreateExternalActionRequest request);

    /**
     * Updates an existing external action.
     * @param externalActionId the external action identifier
     * @param request the external action update request
     * @return the updated external action
     */
    ExternalAction update(ExternalActionId externalActionId, UpdateExternalActionRequest request);

    /**
     * Asynchronously updates an existing external action.
     * @param externalActionId the external action identifier
     * @param request the external action update request
     * @return a future containing the updated external action
     */
    CompletableFuture<ExternalAction> updateAsync(ExternalActionId externalActionId, UpdateExternalActionRequest request);
}
