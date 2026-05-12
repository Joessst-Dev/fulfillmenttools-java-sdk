package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.orders.OrderSearchQuery;

record OrderSearchBody(OrderSearchQuery query, Integer size, String after, String before) {}
