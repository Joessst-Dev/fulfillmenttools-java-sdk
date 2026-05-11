package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.Page;

public interface OrdersClient {

    Order get(String orderId);

    Page<Order> list(OrderListRequest request);

    Iterable<Order> listAll(OrderListRequest request);

    Order create(CreateOrderRequest request);

    Order update(String orderId, UpdateOrderRequest request);

    void delete(String orderId);
}
