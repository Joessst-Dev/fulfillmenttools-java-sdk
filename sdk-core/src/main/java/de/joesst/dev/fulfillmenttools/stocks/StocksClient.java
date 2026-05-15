package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Client for managing stock entries in the fulfillmenttools stocks module.
 *
 * <p>Provides synchronous and asynchronous operations to list, create, and update stock entries
 * across facilities and articles.
 */
public interface StocksClient {

    /**
     * Lists stock entries matching the specified criteria, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a page of stock entries
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<StockItem> list(StockListRequest request);

    /**
     * Lists stock entries matching the specified criteria asynchronously, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a {@code CompletableFuture} that resolves to a page of stock entries
     */
    CompletableFuture<Page<StockItem>> listAsync(StockListRequest request);

    /**
     * Lists all stock entries matching the specified criteria by automatically iterating through pages.
     *
     * @param request the list request with filters
     * @return an {@code Iterable} over all matching stock entries
     */
    Iterable<StockItem> listAll(StockListRequest request);

    /**
     * Creates a new stock entry.
     *
     * @param request the create request
     * @return the created stock entry
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StockItem create(CreateStockRequest request);

    /**
     * Creates a new stock entry asynchronously.
     *
     * @param request the create request
     * @return a {@code CompletableFuture} that resolves to the created stock entry
     */
    CompletableFuture<StockItem> createAsync(CreateStockRequest request);

    /**
     * Updates an existing stock entry.
     *
     * @param stockId the ID of the stock entry to update
     * @param request the update request
     * @return the updated stock entry
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StockItem update(StockId stockId, UpdateStockRequest request);

    /**
     * Updates an existing stock entry asynchronously.
     *
     * @param stockId the ID of the stock entry to update
     * @param request the update request
     * @return a {@code CompletableFuture} that resolves to the updated stock entry
     */
    CompletableFuture<StockItem> updateAsync(StockId stockId, UpdateStockRequest request);

    /**
     * Searches for stock entries matching the given structured query, returning one page of results.
     *
     * <p>Unlike {@link #list}, search accepts a rich {@link StockSearchQuery} with typed filter
     * objects and logical {@code and}/{@code or} combinators, and always POSTs a JSON body.
     *
     * @param request the search request with an optional query and pagination
     * @return a page of matching stock entries
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<StockItem> search(StockSearchRequest request);

    /**
     * Searches for stock entries asynchronously, returning one page of results.
     *
     * @param request the search request with an optional query and pagination
     * @return a {@code CompletableFuture} that resolves to a page of matching stock entries;
     *         completes exceptionally with {@link de.joesst.dev.fulfillmenttools.FulfillmenttoolsException}
     *         if the request fails
     */
    CompletableFuture<Page<StockItem>> searchAsync(StockSearchRequest request);

    /**
     * Searches for all stock entries matching the given query by automatically iterating through pages.
     *
     * @param request the search request with an optional query
     * @return an {@code Iterable} over all matching stock entries
     */
    Iterable<StockItem> searchAll(StockSearchRequest request);

    /**
     * Creates or updates multiple stock entries in a single request (versionless bulk upsert).
     * Each operation is either a {@link VersionlessStockCreate} or a {@link VersionlessStockUpdate}.
     *
     * @param operations the list of create/update operations (1–25 items; must not be empty)
     * @return a list of results in the same order as the input operations
     * @throws IllegalArgumentException if {@code operations} is null or empty
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    List<StockUpsertResult> upsertStocks(List<VersionlessStockOperation> operations);

    /**
     * Creates or updates multiple stock entries asynchronously (versionless bulk upsert).
     *
     * @param operations the list of create/update operations (1–25 items; must not be empty)
     * @return a {@code CompletableFuture} that resolves to a list of results; completes exceptionally
     *         with {@link de.joesst.dev.fulfillmenttools.FulfillmenttoolsException} if the request fails
     * @throws IllegalArgumentException if {@code operations} is null or empty
     */
    CompletableFuture<List<StockUpsertResult>> upsertStocksAsync(List<VersionlessStockOperation> operations);
}
