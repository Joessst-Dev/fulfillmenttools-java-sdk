package de.joesst.dev.fulfillmenttools.internal.packjobs;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
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
    public PackJob get(PackJobId packJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs/" + packJobId.value())
                .build();
        return responseHandler.handle(execute(request), PackJob.class);
    }

    @Override
    public Page<PackJob> list(PackJobListRequest request) {
        SdkHttpResponse response = execute(buildListRequest(request).build());
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
    public PackJob update(PackJobId packJobId, UpdatePackJobRequest request) {
        UpdatePackJobBody body = new UpdatePackJobBody(
                request.version(),
                List.of(new UpdatePackJobBody.ModifyPackJobAction(request.status(), request.customAttributes()))
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/packjobs/" + packJobId.value())
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), PackJob.class);
    }

    @Override
    public CompletableFuture<PackJob> getAsync(PackJobId packJobId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs/" + packJobId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, PackJob.class));
    }

    @Override
    public CompletableFuture<Page<PackJob>> listAsync(PackJobListRequest request) {
        return transport.executeAsync(buildListRequest(request).build()).thenApply(response -> {
            PackJobListResponse body = responseHandler.handle(response, PackJobListResponse.class);
            return new Page<>(body.packJobs() != null ? body.packJobs() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<PackJob> updateAsync(PackJobId packJobId, UpdatePackJobRequest request) {
        UpdatePackJobBody body = new UpdatePackJobBody(
                request.version(),
                List.of(new UpdatePackJobBody.ModifyPackJobAction(request.status(), request.customAttributes()))
        );
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/packjobs/" + packJobId.value())
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, PackJob.class));
    }

    private SdkHttpRequest.Builder buildListRequest(PackJobListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/packjobs");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityRef() != null) builder.queryParam("facilityRef", request.facilityRef());
        if (request.status() != null) request.status().forEach(s -> builder.queryParam("status", s));
        if (request.anonymized() != null) builder.queryParam("anonymized", String.valueOf(request.anonymized()));
        if (request.assignedUser() != null) builder.queryParam("assignedUser", request.assignedUser());
        if (request.searchTerm() != null) builder.queryParam("searchTerm", request.searchTerm());
        if (request.channel() != null) builder.queryParam("channel", request.channel());
        if (request.sourceContainerCodes() != null) request.sourceContainerCodes().forEach(c -> builder.queryParam("sourceContainerCodes", c));
        if (request.orderBy() != null) builder.queryParam("orderBy", request.orderBy());
        if (request.startTargetTime() != null) builder.queryParam("startTargetTime", request.startTargetTime());
        if (request.endTargetTime() != null) builder.queryParam("endTargetTime", request.endTargetTime());
        if (request.orderRef() != null) builder.queryParam("orderRef", request.orderRef());
        if (request.packJobIds() != null) request.packJobIds().forEach(id -> builder.queryParam("packJobIds", id));
        if (request.processId() != null) builder.queryParam("processId", request.processId());
        if (request.pickJobRef() != null) builder.queryParam("pickJobRef", request.pickJobRef());
        if (request.shortId() != null) builder.queryParam("shortId", request.shortId());
        if (request.articleTitle() != null) builder.queryParam("articleTitle", request.articleTitle());
        if (request.startOrderDate() != null) builder.queryParam("startOrderDate", request.startOrderDate());
        if (request.endOrderDate() != null) builder.queryParam("endOrderDate", request.endOrderDate());
        if (request.modifiedByUsername() != null) builder.queryParam("modifiedByUsername", request.modifiedByUsername());
        if (request.tenantOrderId() != null) builder.queryParam("tenantOrderId", request.tenantOrderId().value());

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
