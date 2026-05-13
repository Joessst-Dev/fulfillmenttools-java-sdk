package de.joesst.dev.fulfillmenttools.internal.sourcingoptions;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOption;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsClient;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class SourcingOptionsClientImpl implements SourcingOptionsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public SourcingOptionsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public SourcingOptionsResult evaluate(SourcingOptionsRequest request) {
        SourcingOptionsBody body = new SourcingOptionsBody(
                request.order(), request.additionalInfo(), request.optimizationHints(), request.resourceInvestment());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routing/sourcingoptions")
                .body(responseHandler.encode(body))
                .build();
        return toResult(responseHandler.handle(execute(httpRequest), SourcingOptionsResponseDto.class));
    }

    @Override
    public CompletableFuture<SourcingOptionsResult> evaluateAsync(SourcingOptionsRequest request) {
        SourcingOptionsBody body = new SourcingOptionsBody(
                request.order(), request.additionalInfo(), request.optimizationHints(), request.resourceInvestment());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/routing/sourcingoptions")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> toResult(responseHandler.handle(response, SourcingOptionsResponseDto.class)));
    }

    @Override
    public SourcingOptionsResult get(SourcingOptionsRequestId sourcingOptionsRequestId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routing/sourcingoptions/" + sourcingOptionsRequestId.value())
                .build();
        return toResult(responseHandler.handle(execute(request), SourcingOptionsResponseDto.class));
    }

    @Override
    public CompletableFuture<SourcingOptionsResult> getAsync(SourcingOptionsRequestId sourcingOptionsRequestId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/routing/sourcingoptions/" + sourcingOptionsRequestId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> toResult(responseHandler.handle(response, SourcingOptionsResponseDto.class)));
    }

    private SourcingOptionsResult toResult(SourcingOptionsResponseDto dto) {
        List<SourcingOption> options = dto.result() != null && dto.result().options() != null
                ? dto.result().options()
                : List.of();
        return new SourcingOptionsResult(dto.id(), options);
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
