package de.joesst.dev.fulfillmenttools.internal.packjobs;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.packjobs.PackJob;
import de.joesst.dev.fulfillmenttools.packjobs.PackJobListRequest;
import de.joesst.dev.fulfillmenttools.packjobs.PackingClient;
import de.joesst.dev.fulfillmenttools.packjobs.UpdatePackJobRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class PackingClientImpl implements PackingClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public PackingClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public PackJob get(String packJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs/" + packJobId)
                .build();
        return responseHandler.handle(execute(request), PackJob.class);
    }

    @Override
    public Page<PackJob> list(PackJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs");

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
        PackJobListResponse body = responseHandler.handle(response, PackJobListResponse.class);
        return new Page<>(
                body.packJobs() != null ? body.packJobs() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<PackJob> listAll(PackJobListRequest request) {
        return Pages.all(cursor -> {
            PackJobListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public PackJob update(String packJobId, UpdatePackJobRequest request) {
        UpdatePackJobBody body = new UpdatePackJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/packjobs/" + packJobId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), PackJob.class);
    }

    @Override
    public CompletableFuture<PackJob> getAsync(String packJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs/" + packJobId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, PackJob.class));
    }

    @Override
    public CompletableFuture<Page<PackJob>> listAsync(PackJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs");

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
            PackJobListResponse body = responseHandler.handle(response, PackJobListResponse.class);
            return new Page<>(body.packJobs() != null ? body.packJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<PackJob> updateAsync(String packJobId, UpdatePackJobRequest request) {
        UpdatePackJobBody body = new UpdatePackJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/packjobs/" + packJobId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, PackJob.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
