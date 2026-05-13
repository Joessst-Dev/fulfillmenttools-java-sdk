package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for interacting with the fulfillmenttools orders API.
 *
 * Provides synchronous and asynchronous methods for retrieving, searching, creating, updating, and deleting orders.
 */
public interface OrdersClient {

    /**
     * Gets an order by ID.
     *
     * @param orderId the order ID
     * @return the order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order get(OrderId orderId);

    /**
     * Gets an order by ID asynchronously.
     *
     * @param orderId the order ID
     * @return a future resolving to the order
     */
    CompletableFuture<Order> getAsync(OrderId orderId);

    /**
     * Lists orders according to the given request parameters.
     *
     * @param request the list request
     * @return a page of orders
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<Order> list(OrderListRequest request);

    /**
     * Lists orders asynchronously according to the given request parameters.
     *
     * @param request the list request
     * @return a future resolving to a page of orders
     */
    CompletableFuture<Page<Order>> listAsync(OrderListRequest request);

    /**
     * Lists all orders, iterating through all pages.
     *
     * @param request the list request
     * @return an iterable of orders
     * @throws FulfillmenttoolsException if the request fails
     */
    Iterable<Order> listAll(OrderListRequest request);

    /**
     * Searches orders according to the given search request.
     *
     * @param request the search request
     * @return a page of matching orders
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<Order> search(OrderSearchRequest request);

    /**
     * Searches orders asynchronously according to the given search request.
     *
     * @param request the search request
     * @return a future resolving to a page of matching orders
     */
    CompletableFuture<Page<Order>> searchAsync(OrderSearchRequest request);

    /**
     * Searches orders, iterating through all matching pages.
     *
     * @param request the search request
     * @return an iterable of matching orders
     * @throws FulfillmenttoolsException if the request fails
     */
    Iterable<Order> searchAll(OrderSearchRequest request);

    /**
     * Creates a new order.
     *
     * @param request the create order request
     * @return the created order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order create(CreateOrderRequest request);

    /**
     * Creates a new order asynchronously.
     *
     * @param request the create order request
     * @return a future resolving to the created order
     */
    CompletableFuture<Order> createAsync(CreateOrderRequest request);

    /**
     * Updates an existing order.
     *
     * @param orderId the order ID
     * @param request the update request
     * @return the updated order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order update(OrderId orderId, UpdateOrderRequest request);

    /**
     * Updates an existing order asynchronously.
     *
     * @param orderId the order ID
     * @param request the update request
     * @return a future resolving to the updated order
     */
    CompletableFuture<Order> updateAsync(OrderId orderId, UpdateOrderRequest request);

    /**
     * Deletes an order by ID.
     *
     * @param orderId the order ID
     * @throws FulfillmenttoolsException if the request fails
     */
    void delete(OrderId orderId);

    /**
     * Deletes an order by ID asynchronously.
     *
     * @param orderId the order ID
     * @return a future that completes when the delete is finished
     */
    CompletableFuture<Void> deleteAsync(OrderId orderId);

    /**
     * Cancels an order with a cancellation reason.
     *
     * @param orderId the order ID
     * @param request the cancellation request
     * @return the cancelled order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order cancel(OrderId orderId, CancelOrderRequest request);

    /**
     * Cancels an order asynchronously with a cancellation reason.
     *
     * @param orderId the order ID
     * @param request the cancellation request
     * @return a future resolving to the cancelled order
     */
    CompletableFuture<Order> cancelAsync(OrderId orderId, CancelOrderRequest request);

    /**
     * Forces cancellation of an order, bypassing normal validation.
     *
     * @param orderId the order ID
     * @param version the current version of the order
     * @return the cancelled order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order forceCancel(OrderId orderId, int version);

    /**
     * Forces cancellation of an order asynchronously, bypassing normal validation.
     *
     * @param orderId the order ID
     * @param version the current version of the order
     * @return a future resolving to the cancelled order
     */
    CompletableFuture<Order> forceCancelAsync(OrderId orderId, int version);

    /**
     * Unlocks an order, releasing any locks held on it.
     *
     * @param orderId the order ID
     * @param version the current version of the order
     * @return the unlocked order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order unlock(OrderId orderId, int version);

    /**
     * Unlocks an order at a specific target time.
     *
     * @param orderId the order ID
     * @param version the current version of the order
     * @param targetTime the time at which to unlock the order
     * @return the unlocked order
     * @throws FulfillmenttoolsException if the request fails
     */
    Order unlock(OrderId orderId, int version, java.time.Instant targetTime);

    /**
     * Unlocks an order asynchronously.
     *
     * @param orderId the order ID
     * @param version the current version of the order
     * @return a future resolving to the unlocked order
     */
    CompletableFuture<Order> unlockAsync(OrderId orderId, int version);

    /**
     * Unlocks an order asynchronously at a specific target time.
     *
     * @param orderId the order ID
     * @param version the current version of the order
     * @param targetTime the time at which to unlock the order
     * @return a future resolving to the unlocked order
     */
    CompletableFuture<Order> unlockAsync(OrderId orderId, int version, java.time.Instant targetTime);
}
