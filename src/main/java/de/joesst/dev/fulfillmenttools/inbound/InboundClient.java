package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing stow jobs in the fulfillmenttools inbound module.
 *
 * <p>Provides synchronous and asynchronous operations to retrieve, list, create, update,
 * and manage the lifecycle of stow jobs (e.g. start, pause, cancel, reopen, close).
 */
public interface InboundClient {

    /**
     * Retrieves a single stow job by ID.
     *
     * @param stowJobId the ID of the stow job
     * @return the stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob get(String stowJobId);

    /**
     * Retrieves a single stow job by ID asynchronously.
     *
     * @param stowJobId the ID of the stow job
     * @return a {@code CompletableFuture} that resolves to the stow job
     */
    CompletableFuture<StowJob> getAsync(String stowJobId);

    /**
     * Lists stow jobs matching the specified criteria, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a page of stow jobs
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<StowJob> list(StowJobListRequest request);

    /**
     * Lists stow jobs matching the specified criteria asynchronously, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a {@code CompletableFuture} that resolves to a page of stow jobs
     */
    CompletableFuture<Page<StowJob>> listAsync(StowJobListRequest request);

    /**
     * Lists all stow jobs matching the specified criteria by automatically iterating through pages.
     *
     * @param request the list request with filters
     * @return an {@code Iterable} over all matching stow jobs
     */
    Iterable<StowJob> listAll(StowJobListRequest request);

    /**
     * Creates a new stow job.
     *
     * @param request the creation request
     * @return the created stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob create(CreateStowJobRequest request);

    /**
     * Creates a new stow job asynchronously.
     *
     * @param request the creation request
     * @return a {@code CompletableFuture} that resolves to the created stow job
     */
    CompletableFuture<StowJob> createAsync(CreateStowJobRequest request);

    /**
     * Updates an existing stow job.
     *
     * @param stowJobId the ID of the stow job to update
     * @param request   the update request
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob update(String stowJobId, UpdateStowJobRequest request);

    /**
     * Updates an existing stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to update
     * @param request   the update request
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> updateAsync(String stowJobId, UpdateStowJobRequest request);

    /**
     * Starts a stow job.
     *
     * @param stowJobId the ID of the stow job to start
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob start(String stowJobId, int version);

    /**
     * Starts a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to start
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> startAsync(String stowJobId, int version);

    /**
     * Pauses a stow job.
     *
     * @param stowJobId the ID of the stow job to pause
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob pause(String stowJobId, int version);

    /**
     * Pauses a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to pause
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> pauseAsync(String stowJobId, int version);

    /**
     * Cancels a stow job.
     *
     * @param stowJobId the ID of the stow job to cancel
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob cancel(String stowJobId, int version);

    /**
     * Cancels a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to cancel
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> cancelAsync(String stowJobId, int version);

    /**
     * Reopens a stow job.
     *
     * @param stowJobId the ID of the stow job to reopen
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob reopen(String stowJobId, int version);

    /**
     * Reopens a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to reopen
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> reopenAsync(String stowJobId, int version);

    /**
     * Closes a stow job.
     *
     * @param stowJobId the ID of the stow job to close
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob close(String stowJobId, int version);

    /**
     * Closes a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to close
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> closeAsync(String stowJobId, int version);
}
