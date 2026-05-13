package de.joesst.dev.fulfillmenttools.listings;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Client for managing product listings in the fulfillmenttools listings module.
 *
 * <p>Provides synchronous and asynchronous operations to search, bulk upsert, and manage
 * product listings across sales channels.
 */
public interface ListingsClient {

    /**
     * Bulk upserts product listings.
     *
     * @param request the bulk upsert request containing listings to create or update
     * @return the list of upserted listings
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    List<Listing> bulkUpsert(ListingBulkUpsertRequest request);

    /**
     * Bulk upserts product listings asynchronously.
     *
     * @param request the bulk upsert request containing listings to create or update
     * @return a {@code CompletableFuture} that resolves to the list of upserted listings
     */
    CompletableFuture<List<Listing>> bulkUpsertAsync(ListingBulkUpsertRequest request);

    /**
     * Searches for product listings matching the specified criteria, returning one page of results.
     *
     * @param request the search request with query and pagination
     * @return a page of matching listings
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<Listing> search(ListingSearchRequest request);

    /**
     * Searches for all product listings matching the specified criteria by automatically iterating through pages.
     *
     * @param request the search request with query
     * @return an {@code Iterable} over all matching listings
     */
    Iterable<Listing> searchAll(ListingSearchRequest request);

    /**
     * Searches for product listings matching the specified criteria asynchronously, returning one page of results.
     *
     * @param request the search request with query and pagination
     * @return a {@code CompletableFuture} that resolves to a page of matching listings
     */
    CompletableFuture<Page<Listing>> searchAsync(ListingSearchRequest request);
}
