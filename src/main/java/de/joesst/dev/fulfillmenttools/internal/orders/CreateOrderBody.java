package de.joesst.dev.fulfillmenttools.internal.orders;

import java.time.Instant;

record CreateOrderBody(String tenantOrderId, Instant orderDate) {}
