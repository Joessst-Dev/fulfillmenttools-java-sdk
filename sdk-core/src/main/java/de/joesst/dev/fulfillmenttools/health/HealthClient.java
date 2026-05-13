package de.joesst.dev.fulfillmenttools.health;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

import java.util.concurrent.CompletableFuture;

/**
 * Client for querying the health and status of the fulfillmenttools platform.
 * Provides both synchronous and asynchronous methods to check system health and dependency status.
 */
public interface HealthClient {

    /**
     * Synchronously retrieves detailed health information including the status of all dependencies.
     *
     * @return the health result containing overall status and per-dependency status.
     * @throws FulfillmenttoolsException if the health check request fails.
     */
    HealthResult health();

    /**
     * Asynchronously retrieves detailed health information including the status of all dependencies.
     *
     * @return a {@link CompletableFuture} that completes with the health result.
     */
    CompletableFuture<HealthResult> healthAsync();

    /**
     * Synchronously retrieves the overall system status of the fulfillmenttools platform.
     *
     * @return the system status.
     * @throws FulfillmenttoolsException if the status check request fails.
     */
    SystemStatus status();

    /**
     * Asynchronously retrieves the overall system status of the fulfillmenttools platform.
     *
     * @return a {@link CompletableFuture} that completes with the system status.
     */
    CompletableFuture<SystemStatus> statusAsync();
}
