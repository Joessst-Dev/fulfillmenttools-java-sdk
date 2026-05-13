package de.joesst.dev.fulfillmenttools.internal.handoverjobs;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJob;
import de.joesst.dev.fulfillmenttools.id.HandoverJobId;
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
    public HandoverJob get(HandoverJobId handoverJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId.value())
                .build();
        return responseHandler.handle(execute(request), HandoverJob.class);
    }

    @Override
    public Page<HandoverJob> list(HandoverJobListRequest request) {
        SdkHttpResponse response = execute(buildListRequest(request).build());
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
    public HandoverJob update(HandoverJobId handoverJobId, UpdateHandoverJobRequest request) {
        UpdateHandoverJobBody body = new UpdateHandoverJobBody(
                request.version(),
                List.of(new UpdateHandoverJobBody.ModifyHandoverjobAction(request.status(), request.customAttributes()))
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId.value())
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), HandoverJob.class);
    }

    @Override
    public HandoverJob cancel(HandoverJobId handoverJobId, int version, String cancelReason) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId.value() + "/actions")
                .body(responseHandler.encode(new HandoverJobCancelBody(version, cancelReason)))
                .build();
        return responseHandler.handle(execute(httpRequest), HandoverJob.class);
    }

    @Override
    public CompletableFuture<HandoverJob> getAsync(HandoverJobId handoverJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, HandoverJob.class));
    }

    @Override
    public CompletableFuture<Page<HandoverJob>> listAsync(HandoverJobListRequest request) {
        return transport.executeAsync(buildListRequest(request).build()).thenApply(response -> {
            HandoverJobListResponse body = responseHandler.handle(response, HandoverJobListResponse.class);
            return new Page<>(body.handoverJobs() != null ? body.handoverJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<HandoverJob> updateAsync(HandoverJobId handoverJobId, UpdateHandoverJobRequest request) {
        UpdateHandoverJobBody body = new UpdateHandoverJobBody(
                request.version(),
                List.of(new UpdateHandoverJobBody.ModifyHandoverjobAction(request.status(), request.customAttributes()))
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId.value())
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, HandoverJob.class));
    }

    @Override
    public CompletableFuture<HandoverJob> cancelAsync(HandoverJobId handoverJobId, int version, String cancelReason) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/handoverjobs/" + handoverJobId.value() + "/actions")
                .body(responseHandler.encode(new HandoverJobCancelBody(version, cancelReason)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, HandoverJob.class));
    }

    private SdkHttpRequest.Builder buildListRequest(HandoverJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/handoverjobs");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityRef() != null) builder.queryParam("facilityRef", request.facilityRef().value());
        if (request.status() != null) request.status().forEach(s -> builder.queryParam("status", s));
        if (request.pickJobRef() != null) builder.queryParam("pickJobRef", request.pickJobRef().value());
        if (request.shipmentRef() != null) builder.queryParam("shipmentRef", request.shipmentRef());
        if (request.assignedUser() != null) builder.queryParam("assignedUser", request.assignedUser());
        if (request.carrierRefs() != null) request.carrierRefs().forEach(c -> builder.queryParam("carrierRefs", c.value()));
        if (request.channel() != null) builder.queryParam("channel", request.channel());
        if (request.anonymized() != null) builder.queryParam("anonymized", String.valueOf(request.anonymized()));
        if (request.tenantOrderId() != null) builder.queryParam("tenantOrderId", request.tenantOrderId().value());
        if (request.searchTerm() != null) builder.queryParam("searchTerm", request.searchTerm());
        if (request.startTargetTime() != null) builder.queryParam("startTargetTime", request.startTargetTime());
        if (request.endTargetTime() != null) builder.queryParam("endTargetTime", request.endTargetTime());

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
