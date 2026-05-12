package de.joesst.dev.fulfillmenttools.internal.sourcingoptions;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.sourcingoptions.EvaluateSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsClient;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;

import java.io.IOException;
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
    public SourcingOptionsResult evaluate(EvaluateSourcingOptionsRequest request) {
        EvaluateSourcingOptionsBody body = new EvaluateSourcingOptionsBody(request.orderId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/sourcingoptions")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), SourcingOptionsResult.class);
    }

    @Override
    public CompletableFuture<SourcingOptionsResult> evaluateAsync(EvaluateSourcingOptionsRequest request) {
        EvaluateSourcingOptionsBody body = new EvaluateSourcingOptionsBody(request.orderId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/sourcingoptions")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, SourcingOptionsResult.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
