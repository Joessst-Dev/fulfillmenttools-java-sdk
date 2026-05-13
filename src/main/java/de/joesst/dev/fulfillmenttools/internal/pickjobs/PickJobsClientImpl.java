package de.joesst.dev.fulfillmenttools.internal.pickjobs;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
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
    public PickJob get(PickJobId pickJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs/" + pickJobId.value())
                .build();
        return responseHandler.handle(execute(request), PickJob.class);
    }

    @Override
    public Page<PickJob> list(PickJobListRequest request) {
        SdkHttpRequest.Builder builder = buildListRequest(request);
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
    public PickJob update(PickJobId pickJobId, UpdatePickJobRequest request) {
        UpdatePickJobBody body = new UpdatePickJobBody(
                request.version(),
                List.of(new UpdatePickJobBody.ModifyPickJobAction(
                        request.status(), request.customAttributes(), request.preferredPickingMethods()))
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/pickjobs/" + pickJobId.value())
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), PickJob.class);
    }

    @Override
    public PickJob abort(PickJobId pickJobId, int version) {
        return action(pickJobId, "ABORT", version);
    }

    @Override
    public PickJob restart(PickJobId pickJobId, int version) {
        return action(pickJobId, "RESTART", version);
    }

    @Override
    public PickJob reset(PickJobId pickJobId, int version) {
        return action(pickJobId, "RESET", version);
    }

    @Override
    public PickJob obsolete(PickJobId pickJobId, int version) {
        return action(pickJobId, "OBSOLETE", version);
    }

    @Override
    public PickJob start(PickJobId pickJobId, int version) {
        return action(pickJobId, "START", version);
    }

    @Override
    public PickJob pause(PickJobId pickJobId, int version) {
        return action(pickJobId, "PAUSE", version);
    }

    @Override
    public CompletableFuture<PickJob> getAsync(PickJobId pickJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs/" + pickJobId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, PickJob.class));
    }

    @Override
    public CompletableFuture<Page<PickJob>> listAsync(PickJobListRequest request) {
        SdkHttpRequest.Builder builder = buildListRequest(request);
        return transport.executeAsync(builder.build()).thenApply(response -> {
            PickJobListResponse body = responseHandler.handle(response, PickJobListResponse.class);
            return new Page<>(body.pickJobs() != null ? body.pickJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<PickJob> updateAsync(PickJobId pickJobId, UpdatePickJobRequest request) {
        UpdatePickJobBody body = new UpdatePickJobBody(
                request.version(),
                List.of(new UpdatePickJobBody.ModifyPickJobAction(
                        request.status(), request.customAttributes(), request.preferredPickingMethods()))
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/pickjobs/" + pickJobId.value())
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, PickJob.class));
    }

    @Override
    public CompletableFuture<PickJob> abortAsync(PickJobId pickJobId, int version) {
        return actionAsync(pickJobId, "ABORT", version);
    }

    @Override
    public CompletableFuture<PickJob> restartAsync(PickJobId pickJobId, int version) {
        return actionAsync(pickJobId, "RESTART", version);
    }

    @Override
    public CompletableFuture<PickJob> resetAsync(PickJobId pickJobId, int version) {
        return actionAsync(pickJobId, "RESET", version);
    }

    @Override
    public CompletableFuture<PickJob> obsoleteAsync(PickJobId pickJobId, int version) {
        return actionAsync(pickJobId, "OBSOLETE", version);
    }

    @Override
    public CompletableFuture<PickJob> startAsync(PickJobId pickJobId, int version) {
        return actionAsync(pickJobId, "START", version);
    }

    @Override
    public CompletableFuture<PickJob> pauseAsync(PickJobId pickJobId, int version) {
        return actionAsync(pickJobId, "PAUSE", version);
    }

    private PickJob action(PickJobId pickJobId, String name, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/pickjobs/" + pickJobId.value() + "/actions")
                .body(responseHandler.encode(new PickJobActionBody(name, version)))
                .build();
        return responseHandler.handle(execute(httpRequest), PickJob.class);
    }

    private CompletableFuture<PickJob> actionAsync(PickJobId pickJobId, String name, int version) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/pickjobs/" + pickJobId.value() + "/actions")
                .body(responseHandler.encode(new PickJobActionBody(name, version)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, PickJob.class));
    }

    private SdkHttpRequest.Builder buildListRequest(PickJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/pickjobs");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityRef() != null) builder.queryParam("facilityRef", request.facilityRef());
        if (request.status() != null) request.status().forEach(s -> builder.queryParam("status", s));
        if (request.orderRef() != null) builder.queryParam("orderRef", request.orderRef());
        if (request.tenantOrderId() != null) builder.queryParam("tenantOrderId", request.tenantOrderId());
        if (request.assignedUser() != null) builder.queryParam("assignedUser", request.assignedUser());
        if (request.searchTerm() != null) builder.queryParam("searchTerm", request.searchTerm());
        if (request.channel() != null) builder.queryParam("channel", request.channel());
        if (request.consumerName() != null) builder.queryParam("consumerName", request.consumerName());
        if (request.shortId() != null) builder.queryParam("shortId", request.shortId());
        if (request.articleTitle() != null) builder.queryParam("articleTitle", request.articleTitle());
        if (request.anonymized() != null) builder.queryParam("anonymized", String.valueOf(request.anonymized()));
        if (request.orderBy() != null) builder.queryParam("orderBy", request.orderBy());
        if (request.modifiedByUsername() != null) builder.queryParam("modifiedByUsername", request.modifiedByUsername());
        if (request.startOrderDate() != null) builder.queryParam("startOrderDate", request.startOrderDate());
        if (request.endOrderDate() != null) builder.queryParam("endOrderDate", request.endOrderDate());
        if (request.startTargetTime() != null) builder.queryParam("startTargetTime", request.startTargetTime());
        if (request.endTargetTime() != null) builder.queryParam("endTargetTime", request.endTargetTime());
        if (request.carrierKeys() != null) request.carrierKeys().forEach(k -> builder.queryParam("carrierKeys", k));
        if (request.zoneRefs() != null) request.zoneRefs().forEach(z -> builder.queryParam("zoneRefs", z));
        if (request.pickJobRefs() != null) request.pickJobRefs().forEach(r -> builder.queryParam("pickJobRefs", r));

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
