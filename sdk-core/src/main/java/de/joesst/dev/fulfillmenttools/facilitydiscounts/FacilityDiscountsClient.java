package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing facility discounts in the fulfillmenttools platform.
 * Provides synchronous and asynchronous operations for CRUD operations.
 */
public interface FacilityDiscountsClient {

    /**
     * Retrieves a facility discount by facility ID and discount ID.
     *
     * @param facilityId the facility ID
     * @param discountId the discount ID
     * @return the facility discount
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityDiscount get(FacilityId facilityId, FacilityDiscountId discountId);

    /**
     * Asynchronously retrieves a facility discount by facility ID and discount ID.
     *
     * @param facilityId the facility ID
     * @param discountId the discount ID
     * @return a CompletableFuture containing the facility discount
     */
    CompletableFuture<FacilityDiscount> getAsync(FacilityId facilityId, FacilityDiscountId discountId);

    /**
     * Lists facility discounts for a facility with pagination.
     *
     * @param facilityId the facility ID
     * @param request the list request with pagination parameters
     * @return a page of facility discounts
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<FacilityDiscount> list(FacilityId facilityId, FacilityDiscountListRequest request);

    /**
     * Lists all facility discounts for a facility by automatically handling pagination.
     *
     * @param facilityId the facility ID
     * @param request the list request
     * @return an iterable over all facility discounts
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Iterable<FacilityDiscount> listAll(FacilityId facilityId, FacilityDiscountListRequest request);

    /**
     * Asynchronously lists facility discounts for a facility with pagination.
     *
     * @param facilityId the facility ID
     * @param request the list request with pagination parameters
     * @return a CompletableFuture containing a page of facility discounts
     */
    CompletableFuture<Page<FacilityDiscount>> listAsync(FacilityId facilityId, FacilityDiscountListRequest request);

    /**
     * Creates a new facility discount.
     *
     * @param facilityId the facility ID
     * @param request the create request containing discount data
     * @return the created facility discount
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityDiscount create(FacilityId facilityId, CreateFacilityDiscountRequest request);

    /**
     * Asynchronously creates a new facility discount.
     *
     * @param facilityId the facility ID
     * @param request the create request containing discount data
     * @return a CompletableFuture containing the created facility discount
     */
    CompletableFuture<FacilityDiscount> createAsync(FacilityId facilityId, CreateFacilityDiscountRequest request);

    /**
     * Updates an existing facility discount.
     *
     * @param facilityId the facility ID
     * @param discountId the discount ID
     * @param request the update request containing the new values
     * @return the updated facility discount
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityDiscount update(FacilityId facilityId, FacilityDiscountId discountId, UpdateFacilityDiscountRequest request);

    /**
     * Asynchronously updates an existing facility discount.
     *
     * @param facilityId the facility ID
     * @param discountId the discount ID
     * @param request the update request containing the new values
     * @return a CompletableFuture containing the updated facility discount
     */
    CompletableFuture<FacilityDiscount> updateAsync(FacilityId facilityId, FacilityDiscountId discountId, UpdateFacilityDiscountRequest request);

    /**
     * Deletes a facility discount.
     *
     * @param facilityId the facility ID
     * @param discountId the discount ID
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    void delete(FacilityId facilityId, FacilityDiscountId discountId);

    /**
     * Asynchronously deletes a facility discount.
     *
     * @param facilityId the facility ID
     * @param discountId the discount ID
     * @return a CompletableFuture that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(FacilityId facilityId, FacilityDiscountId discountId);
}
