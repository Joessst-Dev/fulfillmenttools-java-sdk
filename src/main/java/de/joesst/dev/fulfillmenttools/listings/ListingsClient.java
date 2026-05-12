package de.joesst.dev.fulfillmenttools.listings;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ListingsClient {

    List<Listing> bulkUpsert(ListingBulkUpsertRequest request);
    CompletableFuture<List<Listing>> bulkUpsertAsync(ListingBulkUpsertRequest request);

    Page<Listing> search(ListingSearchRequest request);
    Iterable<Listing> searchAll(ListingSearchRequest request);
    CompletableFuture<Page<Listing>> searchAsync(ListingSearchRequest request);
}
