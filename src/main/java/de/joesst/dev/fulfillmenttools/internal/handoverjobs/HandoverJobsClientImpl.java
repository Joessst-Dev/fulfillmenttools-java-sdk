package de.joesst.dev.fulfillmenttools.internal.handoverjobs;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJob;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJobListRequest;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJobsClient;
import de.joesst.dev.fulfillmenttools.handoverjobs.UpdateHandoverJobRequest;
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

public final class HandoverJobsClientImpl implements HandoverJobsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public HandoverJobsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public HandoverJob get(String handoverJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId)
                .build();
        return responseHandler.handle(execute(request), HandoverJob.class);
    }

    @Override
    public Page<HandoverJob> list(HandoverJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs");

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
        HandoverJobListResponse body = responseHandler.handle(response, HandoverJobListResponse.class);
        return new Page<>(
                body.handoverJobs() != null ? body.handoverJobs() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<HandoverJob> listAll(HandoverJobListRequest request) {
        return Pages.all(cursor -> {
            HandoverJobListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public HandoverJob update(String handoverJobId, UpdateHandoverJobRequest request) {
        UpdateHandoverJobBody body = new UpdateHandoverJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), HandoverJob.class);
    }

    @Override
    public CompletableFuture<HandoverJob> getAsync(String handoverJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, HandoverJob.class));
    }

    @Override
    public CompletableFuture<Page<HandoverJob>> listAsync(HandoverJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs");

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
            HandoverJobListResponse body = responseHandler.handle(response, HandoverJobListResponse.class);
            return new Page<>(body.handoverJobs() != null ? body.handoverJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<HandoverJob> updateAsync(String handoverJobId, UpdateHandoverJobRequest request) {
        UpdateHandoverJobBody body = new UpdateHandoverJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, HandoverJob.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
