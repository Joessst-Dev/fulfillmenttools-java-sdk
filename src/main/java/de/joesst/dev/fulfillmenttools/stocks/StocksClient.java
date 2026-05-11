package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.model.Page;

public interface StocksClient {

    Page<StockItem> list(StockListRequest request);

    Iterable<StockItem> listAll(StockListRequest request);
}
