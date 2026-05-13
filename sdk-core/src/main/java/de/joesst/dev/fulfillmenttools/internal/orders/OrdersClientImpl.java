package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.orders.CancelOrderRequest;
import de.joesst.dev.fulfillmenttools.orders.CreateOrderRequest;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.orders.OrderListRequest;
import de.joesst.dev.fulfillmenttools.orders.OrderSearchRequest;
import de.joesst.dev.fulfillmenttools.orders.OrdersClient;
import de.joesst.dev.fulfillmenttools.orders.UpdateOrderRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class OrdersClientImpl implements OrdersClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public OrdersClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Order get(OrderId orderId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders/" + orderId.value())
                .build();
        return responseHandler.handle(execute(request), Order.class);
    }

    @Override
    public Page<Order> list(OrderListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.tenantOrderId() != null) builder.queryParam("tenantOrderId", request.tenantOrderId().value());
        if (request.consumerId() != null) builder.queryParam("consumerId", request.consumerId());

        SdkHttpResponse response = execute(builder.build());
        OrderListResponse body = responseHandler.handle(response, OrderListResponse.class);
        return new Page<>(
                body.orders() != null ? body.orders() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<Order> listAll(OrderListRequest request) {
        return Pages.all(cursor -> {
            OrderListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Page<Order> search(OrderSearchRequest request) {
        OrderSearchBody body = new OrderSearchBody(
                request.query(), request.size(), request.after(), request.before(), request.last());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/search")
                .body(responseHandler.encode(body))
                .build();
        OrderSearchResponse resp = responseHandler.handle(execute(httpRequest), OrderSearchResponse.class);
        String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
        return new Page<>(resp.orders() != null ? resp.orders() : List.of(), cursor);
    }

    @Override
    public Iterable<Order> searchAll(OrderSearchRequest request) {
        return Pages.all(cursor -> {
            OrderSearchRequest r = cursor == null
                    ? request
                    : request.toBuilder().after(cursor).build();
            return search(r);
        });
    }

    @Override
    public Order create(CreateOrderRequest request) {
        CreateOrderBody body = new CreateOrderBody(
                request.orderDate(),
                request.orderLineItems(),
                request.consumer(),
                request.tenantOrderId() != null ? request.tenantOrderId().value() : null,
                request.deliveryPreferences(),
                request.paymentInfo(),
                request.tags(),
                request.stickers(),
                request.statusReasons(),
                request.source(),
                request.customAttributes()
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Order.class);
    }

    @Override
    public Order update(OrderId orderId, UpdateOrderRequest request) {
        UpdateOrderBody body = new UpdateOrderBody(
                request.version(),
                request.comment(),
                request.consumer(),
                request.customAttributes(),
                request.orderLineItems(),
                request.preferredHandlingTime()
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/orders/" + orderId.value())
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Order.class);
    }

    @Override
    public void delete(OrderId orderId) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/orders/" + orderId.value())
                .build();
        responseHandler.handleVoid(execute(httpRequest));
    }

    @Override
    public CompletableFuture<Order> getAsync(OrderId orderId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders/" + orderId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public CompletableFuture<Page<Order>> listAsync(OrderListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.tenantOrderId() != null) builder.queryParam("tenantOrderId", request.tenantOrderId().value());
        if (request.consumerId() != null) builder.queryParam("consumerId", request.consumerId());

        return transport.executeAsync(builder.build()).thenApply(response -> {
            OrderListResponse body = responseHandler.handle(response, OrderListResponse.class);
            return new Page<>(body.orders() != null ? body.orders() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Page<Order>> searchAsync(OrderSearchRequest request) {
        OrderSearchBody body = new OrderSearchBody(
                request.query(), request.size(), request.after(), request.before(), request.last());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/search")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest).thenApply(response -> {
            OrderSearchResponse resp = responseHandler.handle(response, OrderSearchResponse.class);
            String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
            return new Page<>(resp.orders() != null ? resp.orders() : List.of(), cursor);
        });
    }

    @Override
    public CompletableFuture<Order> createAsync(CreateOrderRequest request) {
        CreateOrderBody body = new CreateOrderBody(
                request.orderDate(),
                request.orderLineItems(),
                request.consumer(),
                request.tenantOrderId() != null ? request.tenantOrderId().value() : null,
                request.deliveryPreferences(),
                request.paymentInfo(),
                request.tags(),
                request.stickers(),
                request.statusReasons(),
                request.source(),
                request.customAttributes()
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public CompletableFuture<Order> updateAsync(OrderId orderId, UpdateOrderRequest request) {
        UpdateOrderBody body = new UpdateOrderBody(
                request.version(),
                request.comment(),
                request.consumer(),
                request.customAttributes(),
                request.orderLineItems(),
                request.preferredHandlingTime()
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/orders/" + orderId.value())
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(OrderId orderId) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/orders/" + orderId.value())
                .build();
        return transport.executeAsync(httpRequest).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    @Override
    public Order cancel(OrderId orderId, CancelOrderRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/" + orderId.value() + "/actions")
                .body(responseHandler.encode(new OrderCancelActionBody(request.version(), request.cancelationReasonId())))
                .build();
        return responseHandler.handle(execute(httpRequest), Order.class);
    }

    @Override
    public CompletableFuture<Order> cancelAsync(OrderId orderId, CancelOrderRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/" + orderId.value() + "/actions")
                .body(responseHandler.encode(new OrderCancelActionBody(request.version(), request.cancelationReasonId())))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public Order forceCancel(OrderId orderId, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/" + orderId.value() + "/actions")
                .body(responseHandler.encode(new OrderForceCancelActionBody(version)))
                .build();
        return responseHandler.handle(execute(httpRequest), Order.class);
    }

    @Override
    public CompletableFuture<Order> forceCancelAsync(OrderId orderId, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/" + orderId.value() + "/actions")
                .body(responseHandler.encode(new OrderForceCancelActionBody(version)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public Order unlock(OrderId orderId, int version) {
        return unlock(orderId, version, null);
    }

    @Override
    public Order unlock(OrderId orderId, int version, Instant targetTime) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/" + orderId.value() + "/actions")
                .body(responseHandler.encode(new OrderUnlockActionBody(version, targetTime)))
                .build();
        return responseHandler.handle(execute(httpRequest), Order.class);
    }

    @Override
    public CompletableFuture<Order> unlockAsync(OrderId orderId, int version) {
        return unlockAsync(orderId, version, null);
    }

    @Override
    public CompletableFuture<Order> unlockAsync(OrderId orderId, int version, Instant targetTime) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/orders/" + orderId.value() + "/actions")
                .body(responseHandler.encode(new OrderUnlockActionBody(version, targetTime)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
