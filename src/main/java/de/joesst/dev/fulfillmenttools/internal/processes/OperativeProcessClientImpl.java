package de.joesst.dev.fulfillmenttools.internal.processes;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
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
    public Process get(String processId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes/" + processId)
                .build();
        return responseHandler.handle(execute(request), Process.class);
    }

    @Override
    public Page<Process> list(ProcessListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes");
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityRef() != null) builder.queryParam("facilityRef", request.facilityRef());
        if (request.status() != null) builder.queryParam("status", request.status());

        ProcessListResponse body = responseHandler.handle(execute(builder.build()), ProcessListResponse.class);
        return new Page<>(body.processes() != null ? body.processes() : List.of(), body.nextCursor());
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
        ProcessSearchBody body = new ProcessSearchBody(
                request.facilityRef(), request.status(), request.size(), request.startAfterId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/processes/search")
                .body(responseHandler.encode(body))
                .build();
        ProcessListResponse response = responseHandler.handle(execute(httpRequest), ProcessListResponse.class);
        return new Page<>(response.processes() != null ? response.processes() : List.of(), response.nextCursor());
    }

    @Override
    public CompletableFuture<Process> getAsync(String processId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes/" + processId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Process.class));
    }

    @Override
    public CompletableFuture<Page<Process>> listAsync(ProcessListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/processes");
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityRef() != null) builder.queryParam("facilityRef", request.facilityRef());
        if (request.status() != null) builder.queryParam("status", request.status());

        return transport.executeAsync(builder.build()).thenApply(response -> {
            ProcessListResponse body = responseHandler.handle(response, ProcessListResponse.class);
            return new Page<>(body.processes() != null ? body.processes() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Page<Process>> searchAsync(ProcessSearchRequest request) {
        ProcessSearchBody body = new ProcessSearchBody(
                request.facilityRef(), request.status(), request.size(), request.startAfterId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/processes/search")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest).thenApply(response -> {
            ProcessListResponse r = responseHandler.handle(response, ProcessListResponse.class);
            return new Page<>(r.processes() != null ? r.processes() : List.of(), r.nextCursor());
        });
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
