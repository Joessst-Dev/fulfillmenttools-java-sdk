package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface OrdersClient {

    Order get(String orderId);
    CompletableFuture<Order> getAsync(String orderId);

    Page<Order> list(OrderListRequest request);
    CompletableFuture<Page<Order>> listAsync(OrderListRequest request);

    Iterable<Order> listAll(OrderListRequest request);

    Page<Order> search(OrderSearchRequest request);
    CompletableFuture<Page<Order>> searchAsync(OrderSearchRequest request);

    Order create(CreateOrderRequest request);
    CompletableFuture<Order> createAsync(CreateOrderRequest request);

    Order update(String orderId, UpdateOrderRequest request);
    CompletableFuture<Order> updateAsync(String orderId, UpdateOrderRequest request);

    void delete(String orderId);
    CompletableFuture<Void> deleteAsync(String orderId);

    Order cancel(String orderId, CancelOrderRequest request);
    CompletableFuture<Order> cancelAsync(String orderId, CancelOrderRequest request);

    Order forceCancel(String orderId, int version);
    CompletableFuture<Order> forceCancelAsync(String orderId, int version);

    Order unlock(String orderId, int version);
    Order unlock(String orderId, int version, java.time.Instant targetTime);
    CompletableFuture<Order> unlockAsync(String orderId, int version);
    CompletableFuture<Order> unlockAsync(String orderId, int version, java.time.Instant targetTime);
}
