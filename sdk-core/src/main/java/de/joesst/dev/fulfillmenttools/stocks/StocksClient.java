package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing stock entries in the fulfillmenttools stocks module.
 *
 * <p>Provides synchronous and asynchronous operations to list and query stock entries
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
}
