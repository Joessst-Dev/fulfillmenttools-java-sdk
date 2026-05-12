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
        SdkHttpResponse response = execute(buildListRequest(request).build());
        StowJobListResponse body = responseHandler.handle(response, StowJobListResponse.class);
        return new Page<>(body.stowJobs() != null ? body.stowJobs() : List.of(), body.nextCursor());
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
        CreateStowJobBody body = new CreateStowJobBody(
                request.facilityRef(), request.status(), request.stowLineItems(),
                request.assignedUsers(), request.customAttributes(),
                request.priority(), request.shortId(), request.targetTime());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stowjobs")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), StowJob.class);
    }

    @Override
    public StowJob update(String stowJobId, UpdateStowJobRequest request) {
        UpdateStowJobBody body = new UpdateStowJobBody(
                request.version(), request.priority(), request.targetTime(),
                request.assignedUsers(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/stowjobs/" + stowJobId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), StowJob.class);
    }

    @Override
    public StowJob start(String stowJobId, int version) {
        return action(stowJobId, "START_STOW_JOB", version);
    }

    @Override
    public StowJob pause(String stowJobId, int version) {
        return action(stowJobId, "PAUSE_STOW_JOB", version);
    }

    @Override
    public StowJob cancel(String stowJobId, int version) {
        return action(stowJobId, "CANCEL_STOW_JOB", version);
    }

    @Override
    public StowJob reopen(String stowJobId, int version) {
        return action(stowJobId, "OPEN_STOW_JOB", version);
    }

    @Override
    public StowJob close(String stowJobId, int version) {
        return action(stowJobId, "CLOSE_STOW_JOB", version);
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
        return transport.executeAsync(buildListRequest(request).build()).thenApply(response -> {
            StowJobListResponse body = responseHandler.handle(response, StowJobListResponse.class);
            return new Page<>(body.stowJobs() != null ? body.stowJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<StowJob> createAsync(CreateStowJobRequest request) {
        CreateStowJobBody body = new CreateStowJobBody(
                request.facilityRef(), request.status(), request.stowLineItems(),
                request.assignedUsers(), request.customAttributes(),
                request.priority(), request.shortId(), request.targetTime());
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
        UpdateStowJobBody body = new UpdateStowJobBody(
                request.version(), request.priority(), request.targetTime(),
                request.assignedUsers(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/stowjobs/" + stowJobId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, StowJob.class));
    }

    @Override
    public CompletableFuture<StowJob> startAsync(String stowJobId, int version) {
        return actionAsync(stowJobId, "START_STOW_JOB", version);
    }

    @Override
    public CompletableFuture<StowJob> pauseAsync(String stowJobId, int version) {
        return actionAsync(stowJobId, "PAUSE_STOW_JOB", version);
    }

    @Override
    public CompletableFuture<StowJob> cancelAsync(String stowJobId, int version) {
        return actionAsync(stowJobId, "CANCEL_STOW_JOB", version);
    }

    @Override
    public CompletableFuture<StowJob> reopenAsync(String stowJobId, int version) {
        return actionAsync(stowJobId, "OPEN_STOW_JOB", version);
    }

    @Override
    public CompletableFuture<StowJob> closeAsync(String stowJobId, int version) {
        return actionAsync(stowJobId, "CLOSE_STOW_JOB", version);
    }

    private StowJob action(String stowJobId, String name, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stowjobs/" + stowJobId + "/actions")
                .body(responseHandler.encode(new StowJobActionBody(name, version)))
                .build();
        return responseHandler.handle(execute(httpRequest), StowJob.class);
    }

    private CompletableFuture<StowJob> actionAsync(String stowJobId, String name, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stowjobs/" + stowJobId + "/actions")
                .body(responseHandler.encode(new StowJobActionBody(name, version)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, StowJob.class));
    }

    private SdkHttpRequest.Builder buildListRequest(StowJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stowjobs");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.sort() != null) builder.queryParam("sort", request.sort());
        if (request.facilityRef() != null) request.facilityRef().forEach(v -> builder.queryParam("facilityRef", v));
        if (request.status() != null) request.status().forEach(v -> builder.queryParam("status", v));
        if (request.tenantArticleId() != null) request.tenantArticleId().forEach(v -> builder.queryParam("tenantArticleId", v));
        if (request.locationRef() != null) request.locationRef().forEach(v -> builder.queryParam("locationRef", v));
        if (request.stockRef() != null) request.stockRef().forEach(v -> builder.queryParam("stockRef", v));
        if (request.shortId() != null) request.shortId().forEach(v -> builder.queryParam("shortId", v));
        if (request.priority() != null) request.priority().forEach(v -> builder.queryParam("priority", String.valueOf(v)));

        return builder;
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
