package de.joesst.dev.fulfillmenttools.internal.inbound;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.inbound.CreateStowJobRequest;
import de.joesst.dev.fulfillmenttools.inbound.InboundClient;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.inbound.StowJobListRequest;
import de.joesst.dev.fulfillmenttools.inbound.UpdateStowJobRequest;
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

public final class InboundClientImpl implements InboundClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public InboundClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public StowJob get(String stowJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stowjobs/" + stowJobId)
                .build();
        return responseHandler.handle(execute(request), StowJob.class);
    }

    @Override
    public Page<StowJob> list(StowJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stowjobs");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }
        if (request.status() != null) {
            builder.queryParam("status", request.status());
        }

        SdkHttpResponse response = execute(builder.build());
        StowJobListResponse body = responseHandler.handle(response, StowJobListResponse.class);
        return new Page<>(
                body.stowJobs() != null ? body.stowJobs() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<StowJob> listAll(StowJobListRequest request) {
        return Pages.all(cursor -> {
            StowJobListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public StowJob create(CreateStowJobRequest request) {
        CreateStowJobBody body = new CreateStowJobBody(request.facilityRef());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stowjobs")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), StowJob.class);
    }

    @Override
    public StowJob update(String stowJobId, UpdateStowJobRequest request) {
        UpdateStowJobBody body = new UpdateStowJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/stowjobs/" + stowJobId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), StowJob.class);
    }

    @Override
    public CompletableFuture<StowJob> getAsync(String stowJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stowjobs/" + stowJobId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, StowJob.class));
    }

    @Override
    public CompletableFuture<Page<StowJob>> listAsync(StowJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stowjobs");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }
        if (request.status() != null) {
            builder.queryParam("status", request.status());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            StowJobListResponse body = responseHandler.handle(response, StowJobListResponse.class);
            return new Page<>(body.stowJobs() != null ? body.stowJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<StowJob> createAsync(CreateStowJobRequest request) {
        CreateStowJobBody body = new CreateStowJobBody(request.facilityRef());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stowjobs")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, StowJob.class));
    }

    @Override
    public CompletableFuture<StowJob> updateAsync(String stowJobId, UpdateStowJobRequest request) {
        UpdateStowJobBody body = new UpdateStowJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/stowjobs/" + stowJobId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, StowJob.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
