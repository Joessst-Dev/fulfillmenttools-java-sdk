package de.joesst.dev.fulfillmenttools.internal.checkoutoptions;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.checkoutoptions.CheckoutOption;
import de.joesst.dev.fulfillmenttools.checkoutoptions.CheckoutOptionsClient;
import de.joesst.dev.fulfillmenttools.checkoutoptions.EvaluateCheckoutOptionsRequest;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public final class CheckoutOptionsClientImpl implements CheckoutOptionsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public CheckoutOptionsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public CheckoutOption get(String checkoutOptionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/promises/checkoutoptions/" + checkoutOptionId)
                .build();
        return responseHandler.handle(execute(request), CheckoutOption.class);
    }

    @Override
    public CheckoutOption evaluate(EvaluateCheckoutOptionsRequest request) {
        EvaluateCheckoutOptionsBody body = new EvaluateCheckoutOptionsBody(request.orderId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/promises/checkoutoptions")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), CheckoutOption.class);
    }

    @Override
    public CompletableFuture<CheckoutOption> getAsync(String checkoutOptionId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/promises/checkoutoptions/" + checkoutOptionId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, CheckoutOption.class));
    }

    @Override
    public CompletableFuture<CheckoutOption> evaluateAsync(EvaluateCheckoutOptionsRequest request) {
        EvaluateCheckoutOptionsBody body = new EvaluateCheckoutOptionsBody(request.orderId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/promises/checkoutoptions")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, CheckoutOption.class));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
