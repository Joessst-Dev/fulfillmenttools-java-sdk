package de.joesst.dev.fulfillmenttools.facilitygroups;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FacilityGroupsClient {

    FacilityGroup get(String facilityGroupId);
    CompletableFuture<FacilityGroup> getAsync(String facilityGroupId);

    Page<FacilityGroup> list(FacilityGroupListRequest request);
    Iterable<FacilityGroup> listAll(FacilityGroupListRequest request);
    CompletableFuture<Page<FacilityGroup>> listAsync(FacilityGroupListRequest request);

    FacilityGroup create(CreateFacilityGroupRequest request);
    CompletableFuture<FacilityGroup> createAsync(CreateFacilityGroupRequest request);

    FacilityGroup update(String facilityGroupId, UpdateFacilityGroupRequest request);
    CompletableFuture<FacilityGroup> updateAsync(String facilityGroupId, UpdateFacilityGroupRequest request);

    void delete(String facilityGroupId);
    CompletableFuture<Void> deleteAsync(String facilityGroupId);

    FacilityGroup addFacilities(String facilityGroupId, List<String> facilityRefs, Integer version);
    CompletableFuture<FacilityGroup> addFacilitiesAsync(String facilityGroupId, List<String> facilityRefs, Integer version);

    FacilityGroup removeFacilities(String facilityGroupId, List<String> facilityRefs, Integer version);
    CompletableFuture<FacilityGroup> removeFacilitiesAsync(String facilityGroupId, List<String> facilityRefs, Integer version);

    Page<FacilityGroup> search(FacilityGroupSearchRequest request);
    Iterable<FacilityGroup> searchAll(FacilityGroupSearchRequest request);
    CompletableFuture<Page<FacilityGroup>> searchAsync(FacilityGroupSearchRequest request);
}
