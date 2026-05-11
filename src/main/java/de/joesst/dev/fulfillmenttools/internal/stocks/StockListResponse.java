package de.joesst.dev.fulfillmenttools.internal.stocks;

import de.joesst.dev.fulfillmenttools.stocks.StockItem;

import java.util.List;

record StockListResponse(List<StockItem> stocks, String nextCursor) {}
