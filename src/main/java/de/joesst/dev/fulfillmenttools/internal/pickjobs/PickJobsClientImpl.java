package de.joesst.dev.fulfillmenttools.internal.pickjobs;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJobListRequest;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJobsClient;
import de.joesst.dev.fulfillmenttools.pickjobs.UpdatePickJobRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class PickJobsClientImpl implements PickJobsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public PickJobsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public PickJob get(String pickJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs/" + pickJobId)
                .build();
        return responseHandler.handle(execute(request), PickJob.class);
    }

    @Override
    public Page<PickJob> list(PickJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs");

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
        PickJobListResponse body = responseHandler.handle(response, PickJobListResponse.class);
        return new Page<>(
                body.pickJobs() != null ? body.pickJobs() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<PickJob> listAll(PickJobListRequest request) {
        return Pages.all(cursor -> {
            PickJobListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public PickJob update(String pickJobId, UpdatePickJobRequest request) {
        UpdatePickJobBody body = new UpdatePickJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/pickjobs/" + pickJobId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), PickJob.class);
    }

    @Override
    public CompletableFuture<PickJob> getAsync(String pickJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs/" + pickJobId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, PickJob.class));
    }

    @Override
    public CompletableFuture<Page<PickJob>> listAsync(PickJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs");

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
            PickJobListResponse body = responseHandler.handle(response, PickJobListResponse.class);
            return new Page<>(body.pickJobs() != null ? body.pickJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<PickJob> updateAsync(String pickJobId, UpdatePickJobRequest request) {
        UpdatePickJobBody body = new UpdatePickJobBody(request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/pickjobs/" + pickJobId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, PickJob.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
