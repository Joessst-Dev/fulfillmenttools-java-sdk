package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface FacilityDiscountsClient {

    FacilityDiscount get(String facilityRef, String discountRef);
    CompletableFuture<FacilityDiscount> getAsync(String facilityRef, String discountRef);

    Page<FacilityDiscount> list(String facilityRef, FacilityDiscountListRequest request);
    Iterable<FacilityDiscount> listAll(String facilityRef, FacilityDiscountListRequest request);
    CompletableFuture<Page<FacilityDiscount>> listAsync(String facilityRef, FacilityDiscountListRequest request);

    FacilityDiscount create(String facilityRef, CreateFacilityDiscountRequest request);
    CompletableFuture<FacilityDiscount> createAsync(String facilityRef, CreateFacilityDiscountRequest request);

    FacilityDiscount update(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request);
    CompletableFuture<FacilityDiscount> updateAsync(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request);

    void delete(String facilityRef, String discountRef);
    CompletableFuture<Void> deleteAsync(String facilityRef, String discountRef);
}
