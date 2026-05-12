package de.joesst.dev.fulfillmenttools.facilityconnections;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface FacilityConnectionsClient {

    FacilityConnection get(String facilityId, String connectionId);
    CompletableFuture<FacilityConnection> getAsync(String facilityId, String connectionId);

    Page<FacilityConnection> list(String facilityId, FacilityConnectionListRequest request);
    Iterable<FacilityConnection> listAll(String facilityId, FacilityConnectionListRequest request);
    CompletableFuture<Page<FacilityConnection>> listAsync(String facilityId, FacilityConnectionListRequest request);

    FacilityConnection create(String facilityId, CreateFacilityConnectionRequest request);
    CompletableFuture<FacilityConnection> createAsync(String facilityId, CreateFacilityConnectionRequest request);

    FacilityConnection update(String facilityId, String connectionId, UpdateFacilityConnectionRequest request);
    CompletableFuture<FacilityConnection> updateAsync(String facilityId, String connectionId, UpdateFacilityConnectionRequest request);

    void delete(String facilityId, String connectionId);
    CompletableFuture<Void> deleteAsync(String facilityId, String connectionId);
}
