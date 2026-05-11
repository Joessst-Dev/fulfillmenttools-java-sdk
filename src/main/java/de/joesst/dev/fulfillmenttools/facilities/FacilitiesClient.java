package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface FacilitiesClient {

    Facility get(String facilityId);
    CompletableFuture<Facility> getAsync(String facilityId);

    Page<Facility> list(FacilityListRequest request);
    CompletableFuture<Page<Facility>> listAsync(FacilityListRequest request);

    Iterable<Facility> listAll(FacilityListRequest request);

    Facility create(CreateFacilityRequest request);
    CompletableFuture<Facility> createAsync(CreateFacilityRequest request);

    Facility update(String facilityId, UpdateFacilityRequest request);
    CompletableFuture<Facility> updateAsync(String facilityId, UpdateFacilityRequest request);

    void delete(String facilityId);
    CompletableFuture<Void> deleteAsync(String facilityId);
}
