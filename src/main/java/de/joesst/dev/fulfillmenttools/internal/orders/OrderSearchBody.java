package de.joesst.dev.fulfillmenttools.internal.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.orders.OrderSearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record OrderSearchBody(OrderSearchQuery query, Integer size, String after, String before, Integer last) {}
