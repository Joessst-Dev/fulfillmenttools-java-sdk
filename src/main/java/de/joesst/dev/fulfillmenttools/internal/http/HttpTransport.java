package de.joesst.dev.fulfillmenttools.internal.http;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface HttpTransport {
    SdkHttpResponse execute(SdkHttpRequest request) throws IOException;
    CompletableFuture<SdkHttpResponse> executeAsync(SdkHttpRequest request);
}
