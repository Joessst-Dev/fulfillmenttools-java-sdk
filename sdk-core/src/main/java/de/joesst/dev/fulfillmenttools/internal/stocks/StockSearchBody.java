package de.joesst.dev.fulfillmenttools.internal.stocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.stocks.StockSearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record StockSearchBody(StockSearchQuery query, Integer size, String after) {}
