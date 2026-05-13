package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.id.ReturnId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for interacting with the fulfillmenttools returns API.
 *
 * Provides synchronous and asynchronous methods for retrieving, creating, and managing returns.
 */
public interface ReturnsClient {

    /**
     * Gets a return by ID.
     *
     * @param returnId the return ID
     * @return the return
     * @throws FulfillmenttoolsException if the request fails
     */
    Return get(ReturnId returnId);

    /**
     * Gets a return by ID asynchronously.
     *
     * @param returnId the return ID
     * @return a future resolving to the return
     */
    CompletableFuture<Return> getAsync(ReturnId returnId);

    /**
     * Lists returns according to the given request parameters.
     *
     * @param request the list request
     * @return a page of returns
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<Return> list(ReturnListRequest request);

    /**
     * Lists returns asynchronously according to the given request parameters.
     *
     * @param request the list request
     * @return a future resolving to a page of returns
     */
    CompletableFuture<Page<Return>> listAsync(ReturnListRequest request);

    /**
     * Lists all returns, iterating through all pages.
     *
     * @param request the list request
     * @return an iterable of returns
     * @throws FulfillmenttoolsException if the request fails
     */
    Iterable<Return> listAll(ReturnListRequest request);

    /**
     * Creates a new return.
     *
     * @param request the create return request
     * @return the created return
     * @throws FulfillmenttoolsException if the request fails
     */
    Return create(CreateReturnRequest request);

    /**
     * Creates a new return asynchronously.
     *
     * @param request the create return request
     * @return a future resolving to the created return
     */
    CompletableFuture<Return> createAsync(CreateReturnRequest request);

    /**
     * Starts a return process.
     *
     * @param returnId the return ID
     * @param version the current version of the return
     * @return the updated return
     * @throws FulfillmenttoolsException if the request fails
     */
    Return start(ReturnId returnId, int version);

    /**
     * Starts a return process asynchronously.
     *
     * @param returnId the return ID
     * @param version the current version of the return
     * @return a future resolving to the updated return
     */
    CompletableFuture<Return> startAsync(ReturnId returnId, int version);

    /**
     * Finishes a return process.
     *
     * @param returnId the return ID
     * @param version the current version of the return
     * @return the updated return
     * @throws FulfillmenttoolsException if the request fails
     */
    Return finish(ReturnId returnId, int version);

    /**
     * Finishes a return process asynchronously.
     *
     * @param returnId the return ID
     * @param version the current version of the return
     * @return a future resolving to the updated return
     */
    CompletableFuture<Return> finishAsync(ReturnId returnId, int version);

    /**
     * Restarts a return process.
     *
     * @param returnId the return ID
     * @param version the current version of the return
     * @return the updated return
     * @throws FulfillmenttoolsException if the request fails
     */
    Return restart(ReturnId returnId, int version);

    /**
     * Restarts a return process asynchronously.
     *
     * @param returnId the return ID
     * @param version the current version of the return
     * @return a future resolving to the updated return
     */
    CompletableFuture<Return> restartAsync(ReturnId returnId, int version);
}
