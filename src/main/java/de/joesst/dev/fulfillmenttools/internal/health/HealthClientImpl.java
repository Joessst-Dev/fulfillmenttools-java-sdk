package de.joesst.dev.fulfillmenttools.internal.health;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.health.HealthClient;
import de.joesst.dev.fulfillmenttools.health.HealthStatus;
import de.joesst.dev.fulfillmenttools.health.SystemStatus;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public final class HealthClientImpl implements HealthClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public HealthClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public HealthStatus health() {
        return responseHandler.handle(execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/health")
                .build()), HealthStatus.class);
    }

    @Override
    public CompletableFuture<HealthStatus> healthAsync() {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/health")
                .build()).thenApply(r -> responseHandler.handle(r, HealthStatus.class));
    }

    @Override
    public SystemStatus status() {
        return responseHandler.handle(execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/status")
                .build()), SystemStatus.class);
    }

    @Override
    public CompletableFuture<SystemStatus> statusAsync() {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/status")
                .build()).thenApply(r -> responseHandler.handle(r, SystemStatus.class));
    }

    private de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
