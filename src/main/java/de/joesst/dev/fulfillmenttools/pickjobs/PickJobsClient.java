package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.Page;

public interface PickJobsClient {

    PickJob get(String pickJobId);

    Page<PickJob> list(PickJobListRequest request);

    Iterable<PickJob> listAll(PickJobListRequest request);

    PickJob update(String pickJobId, UpdatePickJobRequest request);
}
