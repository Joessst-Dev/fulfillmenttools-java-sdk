package de.joesst.dev.fulfillmenttools.stocks;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The result of a single operation within a {@link StocksClient#upsertStocks} call.
 *
 * @param stock  the stock entry after the operation
 * @param status one of {@code "CREATED"}, {@code "UPDATED"}, or {@code "UNCHANGED"}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StockUpsertResult(StockItem stock, String status) {}
