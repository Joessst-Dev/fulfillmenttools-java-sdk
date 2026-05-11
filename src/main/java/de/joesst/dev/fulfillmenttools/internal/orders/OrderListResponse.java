package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.orders.Order;

import java.util.List;

record OrderListResponse(List<Order> orders, String nextCursor) {}
