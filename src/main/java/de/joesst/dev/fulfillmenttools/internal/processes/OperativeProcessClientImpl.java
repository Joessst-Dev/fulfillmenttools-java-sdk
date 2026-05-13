package de.joesst.dev.fulfillmenttools.internal.processes;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.processes.OperativeProcessClient;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessListRequest;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class OperativeProcessClientImpl implements OperativeProcessClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public OperativeProcessClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Process get(ProcessId processId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes/" + processId.value())
                .build();
        return responseHandler.handle(execute(request), Process.class);
    }

    @Override
    public Page<Process> list(ProcessListRequest request) {
        ProcessListResponse body = responseHandler.handle(execute(buildListRequest(request)), ProcessListResponse.class);
        return new Page<>(body.processes() != null ? body.processes() : List.of(), null);
    }

    @Override
    public Iterable<Process> listAll(ProcessListRequest request) {
        return Pages.all(cursor -> {
            ProcessListRequest r = cursor == null ? request : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Page<Process> search(ProcessSearchRequest request) {
        return toSearchPage(responseHandler.handle(execute(buildSearchRequest(request)), ProcessSearchResponse.class));
    }

    @Override
    public Iterable<Process> searchAll(ProcessSearchRequest request) {
        return Pages.all(cursor -> {
            ProcessSearchRequest r = cursor == null ? request : request.toBuilder().after(cursor).build();
            return search(r);
        });
    }

    @Override
    public CompletableFuture<Process> getAsync(ProcessId processId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes/" + processId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Process.class));
    }

    @Override
    public CompletableFuture<Page<Process>> listAsync(ProcessListRequest request) {
        return transport.executeAsync(buildListRequest(request)).thenApply(response -> {
            ProcessListResponse body = responseHandler.handle(response, ProcessListResponse.class);
            return new Page<>(body.processes() != null ? body.processes() : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<Page<Process>> searchAsync(ProcessSearchRequest request) {
        return transport.executeAsync(buildSearchRequest(request))
                .thenApply(r -> toSearchPage(responseHandler.handle(r, ProcessSearchResponse.class)));
    }

    private SdkHttpRequest buildSearchRequest(ProcessSearchRequest request) {
        var body = new ProcessSearchBody(
                request.query(), request.size(), request.after(), request.before(), request.last());
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/processes/search")
                .body(responseHandler.encode(body))
                .build();
    }

    private Page<Process> toSearchPage(ProcessSearchResponse resp) {
        List<Process> items = resp.processes() != null ? resp.processes() : List.of();
        String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
        return new Page<>(items, cursor);
    }

    private SdkHttpRequest buildListRequest(ProcessListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.tenantOrderId() != null) builder.queryParam("tenantOrderId", request.tenantOrderId());
        if (request.searchTerm() != null) builder.queryParam("searchTerm", request.searchTerm());
        if (request.facilityRefs() != null) {
            request.facilityRefs().forEach(r -> builder.queryParam("facilityRefs", r));
        }
        if (request.status() != null) {
            request.status().forEach(s -> builder.queryParam("status", s));
        }
        if (request.operativeStatus() != null) {
            request.operativeStatus().forEach(s -> builder.queryParam("operativeStatus", s));
        }

        return builder.build();
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
