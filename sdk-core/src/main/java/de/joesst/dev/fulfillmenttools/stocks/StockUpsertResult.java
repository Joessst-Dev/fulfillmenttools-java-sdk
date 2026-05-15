package de.joesst.dev.fulfillmenttools.stocks;

/**
 * The result of a single operation within a {@link StocksClient#upsertStocks} call.
 *
 * @param stock  the stock entry after the operation
 * @param status one of {@code "CREATED"}, {@code "UPDATED"}, or {@code "UNCHANGED"}
 */
public record StockUpsertResult(StockItem stock, String status) {}
