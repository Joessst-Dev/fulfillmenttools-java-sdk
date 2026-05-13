package de.joesst.dev.fulfillmenttools.carriers;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing carriers in the fulfillmenttools platform.
 */
public interface CarriersClient {

    /**
     * Retrieves a carrier by ID.
     * @param carrierId the carrier identifier
     * @return the carrier
     */
    Carrier get(String carrierId);

    /**
     * Asynchronously retrieves a carrier by ID.
     * @param carrierId the carrier identifier
     * @return a future containing the carrier
     */
    CompletableFuture<Carrier> getAsync(String carrierId);

    /**
     * Lists carriers with pagination.
     * @param request the list request with pagination parameters
     * @return a page of carriers
     */
    Page<Carrier> list(CarrierListRequest request);

    /**
     * Asynchronously lists carriers with pagination.
     * @param request the list request with pagination parameters
     * @return a future containing a page of carriers
     */
    CompletableFuture<Page<Carrier>> listAsync(CarrierListRequest request);

    /**
     * Lists all carriers, automatically handling pagination.
     * @param request the list request parameters
     * @return an iterable of all carriers
     */
    Iterable<Carrier> listAll(CarrierListRequest request);

    /**
     * Creates a new carrier.
     * @param request the carrier creation request
     * @return the created carrier
     */
    Carrier create(CreateCarrierRequest request);

    /**
     * Asynchronously creates a new carrier.
     * @param request the carrier creation request
     * @return a future containing the created carrier
     */
    CompletableFuture<Carrier> createAsync(CreateCarrierRequest request);

    /**
     * Updates an existing carrier.
     * @param carrierId the carrier identifier
     * @param request the carrier update request
     * @return the updated carrier
     */
    Carrier update(String carrierId, UpdateCarrierRequest request);

    /**
     * Asynchronously updates an existing carrier.
     * @param carrierId the carrier identifier
     * @param request the carrier update request
     * @return a future containing the updated carrier
     */
    CompletableFuture<Carrier> updateAsync(String carrierId, UpdateCarrierRequest request);

    /**
     * Deletes a carrier.
     * @param carrierId the carrier identifier
     */
    void delete(String carrierId);

    /**
     * Asynchronously deletes a carrier.
     * @param carrierId the carrier identifier
     * @return a future that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String carrierId);
}
