package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.Page;

public interface FacilitiesClient {

    Facility get(String facilityId);

    Page<Facility> list(FacilityListRequest request);

    Iterable<Facility> listAll(FacilityListRequest request);

    Facility create(CreateFacilityRequest request);

    Facility update(String facilityId, UpdateFacilityRequest request);

    void delete(String facilityId);
}
