package de.joesst.dev.fulfillmenttools.processes;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for interacting with the fulfillmenttools operative processes API.
 *
 * Provides synchronous and asynchronous methods for retrieving and searching operative processes.
 */
public interface OperativeProcessClient {

    /**
     * Gets a process by ID.
     *
     * @param processId the process ID
     * @return the process
     * @throws FulfillmenttoolsException if the request fails
     */
    Process get(String processId);

    /**
     * Gets a process by ID asynchronously.
     *
     * @param processId the process ID
     * @return a future resolving to the process
     */
    CompletableFuture<Process> getAsync(String processId);

    /**
     * Lists processes according to the given request parameters.
     *
     * @param request the list request
     * @return a page of processes
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<Process> list(ProcessListRequest request);

    /**
     * Lists processes asynchronously according to the given request parameters.
     *
     * @param request the list request
     * @return a future resolving to a page of processes
     */
    CompletableFuture<Page<Process>> listAsync(ProcessListRequest request);

    /**
     * Lists all processes, iterating through all pages.
     *
     * @param request the list request
     * @return an iterable of processes
     * @throws FulfillmenttoolsException if the request fails
     */
    Iterable<Process> listAll(ProcessListRequest request);

    /**
     * Searches processes according to the given search request.
     *
     * @param request the search request
     * @return a page of matching processes
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<Process> search(ProcessSearchRequest request);

    /**
     * Searches processes, iterating through all matching pages.
     *
     * @param request the search request
     * @return an iterable of matching processes
     * @throws FulfillmenttoolsException if the request fails
     */
    Iterable<Process> searchAll(ProcessSearchRequest request);

    /**
     * Searches processes asynchronously according to the given search request.
     *
     * @param request the search request
     * @return a future resolving to a page of matching processes
     */
    CompletableFuture<Page<Process>> searchAsync(ProcessSearchRequest request);
}
