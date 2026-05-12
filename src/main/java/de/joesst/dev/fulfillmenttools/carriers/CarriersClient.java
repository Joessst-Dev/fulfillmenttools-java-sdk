package de.joesst.dev.fulfillmenttools.carriers;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface CarriersClient {

    Carrier get(String carrierId);
    CompletableFuture<Carrier> getAsync(String carrierId);

    Page<Carrier> list(CarrierListRequest request);
    CompletableFuture<Page<Carrier>> listAsync(CarrierListRequest request);

    Iterable<Carrier> listAll(CarrierListRequest request);

    Carrier create(CreateCarrierRequest request);
    CompletableFuture<Carrier> createAsync(CreateCarrierRequest request);

    Carrier update(String carrierId, UpdateCarrierRequest request);
    CompletableFuture<Carrier> updateAsync(String carrierId, UpdateCarrierRequest request);

    void delete(String carrierId);
    CompletableFuture<Void> deleteAsync(String carrierId);
}
