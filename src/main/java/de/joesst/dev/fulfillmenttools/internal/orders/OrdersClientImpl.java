package de.joesst.dev.fulfillmenttools.internal.orders;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.orders.CreateOrderRequest;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.orders.OrderListRequest;
import de.joesst.dev.fulfillmenttools.orders.OrdersClient;
import de.joesst.dev.fulfillmenttools.orders.UpdateOrderRequest;

import java.io.IOException;
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
    public Order get(String orderId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders/" + orderId)
                .build();
        return responseHandler.handle(execute(request), Order.class);
    }

    @Override
    public Page<Order> list(OrderListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

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
    public Order create(CreateOrderRequest request) {
        CreateOrderBody body = new CreateOrderBody(
                request.orderDate(),
                request.orderLineItems(),
                request.tenantOrderId(),
                request.consumer(),
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
    public Order update(String orderId, UpdateOrderRequest request) {
        UpdateOrderBody body = new UpdateOrderBody(request.tenantOrderId(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/orders/" + orderId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Order.class);
    }

    @Override
    public void delete(String orderId) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/orders/" + orderId)
                .build();
        responseHandler.handleVoid(execute(httpRequest));
    }

    @Override
    public CompletableFuture<Order> getAsync(String orderId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders/" + orderId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public CompletableFuture<Page<Order>> listAsync(OrderListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/orders");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            OrderListResponse body = responseHandler.handle(response, OrderListResponse.class);
            return new Page<>(body.orders() != null ? body.orders() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Order> createAsync(CreateOrderRequest request) {
        CreateOrderBody body = new CreateOrderBody(
                request.orderDate(),
                request.orderLineItems(),
                request.tenantOrderId(),
                request.consumer(),
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
    public CompletableFuture<Order> updateAsync(String orderId, UpdateOrderRequest request) {
        UpdateOrderBody body = new UpdateOrderBody(request.tenantOrderId(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/orders/" + orderId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Order.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String orderId) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/orders/" + orderId)
                .build();
        return transport.executeAsync(httpRequest).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
