package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.orders.Order;

import java.util.List;

record OrderSearchResponse(List<Order> orders, PageInfo pageInfo, Double total) {
    record PageInfo(String endCursor, String startCursor, Boolean hasNextPage, Boolean hasPreviousPage) {}
}
