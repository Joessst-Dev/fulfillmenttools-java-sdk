package de.joesst.dev.fulfillmenttools.internal.eventing;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.eventing.CreateSubscriptionRequest;
import de.joesst.dev.fulfillmenttools.eventing.EventingClient;
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.eventing.SubscriptionListRequest;
import de.joesst.dev.fulfillmenttools.eventing.UpdateSubscriptionRequest;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class EventingClientImpl implements EventingClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public EventingClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Subscription get(String subscriptionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/subscriptions/" + subscriptionId)
                .build();
        return responseHandler.handle(execute(request), Subscription.class);
    }

    @Override
    public Page<Subscription> list(SubscriptionListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/subscriptions");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        SdkHttpResponse response = execute(builder.build());
        SubscriptionListResponse body = responseHandler.handle(response, SubscriptionListResponse.class);
        return new Page<>(
                body.subscriptions() != null ? body.subscriptions() : List.of(),
                null);
    }

    @Override
    public Iterable<Subscription> listAll(SubscriptionListRequest request) {
        return Pages.all(cursor -> {
            SubscriptionListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Subscription create(CreateSubscriptionRequest request) {
        CreateSubscriptionBody body = new CreateSubscriptionBody(
                request.name(), request.event(), request.callbackUrl(),
                request.contexts(), request.headers(), request.target());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/subscriptions")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Subscription.class);
    }

    @Override
    public Subscription update(String subscriptionId, UpdateSubscriptionRequest request) {
        UpdateSubscriptionBody body = new UpdateSubscriptionBody(request.callbackUrl(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/subscriptions/" + subscriptionId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Subscription.class);
    }

    @Override
    public void delete(String subscriptionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/subscriptions/" + subscriptionId)
                .build();
        responseHandler.handleVoid(execute(request));
    }

    @Override
    public CompletableFuture<Subscription> getAsync(String subscriptionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/subscriptions/" + subscriptionId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Subscription.class));
    }

    @Override
    public CompletableFuture<Page<Subscription>> listAsync(SubscriptionListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/subscriptions");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            SubscriptionListResponse body = responseHandler.handle(response, SubscriptionListResponse.class);
            return new Page<>(body.subscriptions() != null ? body.subscriptions() : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<Subscription> createAsync(CreateSubscriptionRequest request) {
        CreateSubscriptionBody body = new CreateSubscriptionBody(
                request.name(), request.event(), request.callbackUrl(),
                request.contexts(), request.headers(), request.target());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/subscriptions")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Subscription.class));
    }

    @Override
    public CompletableFuture<Subscription> updateAsync(String subscriptionId, UpdateSubscriptionRequest request) {
        UpdateSubscriptionBody body = new UpdateSubscriptionBody(request.callbackUrl(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/subscriptions/" + subscriptionId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Subscription.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String subscriptionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/subscriptions/" + subscriptionId)
                .build();
        return transport.executeAsync(request).thenApply(response -> {
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
