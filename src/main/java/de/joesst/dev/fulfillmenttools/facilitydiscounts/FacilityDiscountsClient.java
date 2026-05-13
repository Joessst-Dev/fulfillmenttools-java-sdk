package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing facility discounts in the fulfillmenttools platform.
 * Provides synchronous and asynchronous operations for CRUD operations.
 */
public interface FacilityDiscountsClient {

    /**
     * Retrieves a facility discount by facility reference and discount reference.
     *
     * @param facilityRef the facility reference
     * @param discountRef the discount reference
     * @return the facility discount
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityDiscount get(String facilityRef, String discountRef);

    /**
     * Asynchronously retrieves a facility discount by facility reference and discount reference.
     *
     * @param facilityRef the facility reference
     * @param discountRef the discount reference
     * @return a CompletableFuture containing the facility discount
     */
    CompletableFuture<FacilityDiscount> getAsync(String facilityRef, String discountRef);

    /**
     * Lists facility discounts for a facility with pagination.
     *
     * @param facilityRef the facility reference
     * @param request the list request with pagination parameters
     * @return a page of facility discounts
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<FacilityDiscount> list(String facilityRef, FacilityDiscountListRequest request);

    /**
     * Lists all facility discounts for a facility by automatically handling pagination.
     *
     * @param facilityRef the facility reference
     * @param request the list request
     * @return an iterable over all facility discounts
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Iterable<FacilityDiscount> listAll(String facilityRef, FacilityDiscountListRequest request);

    /**
     * Asynchronously lists facility discounts for a facility with pagination.
     *
     * @param facilityRef the facility reference
     * @param request the list request with pagination parameters
     * @return a CompletableFuture containing a page of facility discounts
     */
    CompletableFuture<Page<FacilityDiscount>> listAsync(String facilityRef, FacilityDiscountListRequest request);

    /**
     * Creates a new facility discount.
     *
     * @param facilityRef the facility reference
     * @param request the create request containing discount data
     * @return the created facility discount
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityDiscount create(String facilityRef, CreateFacilityDiscountRequest request);

    /**
     * Asynchronously creates a new facility discount.
     *
     * @param facilityRef the facility reference
     * @param request the create request containing discount data
     * @return a CompletableFuture containing the created facility discount
     */
    CompletableFuture<FacilityDiscount> createAsync(String facilityRef, CreateFacilityDiscountRequest request);

    /**
     * Updates an existing facility discount.
     *
     * @param facilityRef the facility reference
     * @param discountRef the discount reference
     * @param request the update request containing the new values
     * @return the updated facility discount
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityDiscount update(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request);

    /**
     * Asynchronously updates an existing facility discount.
     *
     * @param facilityRef the facility reference
     * @param discountRef the discount reference
     * @param request the update request containing the new values
     * @return a CompletableFuture containing the updated facility discount
     */
    CompletableFuture<FacilityDiscount> updateAsync(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request);

    /**
     * Deletes a facility discount.
     *
     * @param facilityRef the facility reference
     * @param discountRef the discount reference
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    void delete(String facilityRef, String discountRef);

    /**
     * Asynchronously deletes a facility discount.
     *
     * @param facilityRef the facility reference
     * @param discountRef the discount reference
     * @return a CompletableFuture that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String facilityRef, String discountRef);
}
