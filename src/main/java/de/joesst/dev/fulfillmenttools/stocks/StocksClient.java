package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface StocksClient {

    Page<StockItem> list(StockListRequest request);
    CompletableFuture<Page<StockItem>> listAsync(StockListRequest request);

    Iterable<StockItem> listAll(StockListRequest request);
}
