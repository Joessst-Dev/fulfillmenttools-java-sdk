package de.joesst.dev.fulfillmenttools.internal.http;

import de.joesst.dev.fulfillmenttools.AuthenticationException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public final class AuthenticatingTransport implements HttpTransport {

    private final HttpTransport delegate;
    private final TokenProvider tokenProvider;

    public AuthenticatingTransport(HttpTransport delegate, TokenProvider tokenProvider) {
        this.delegate = delegate;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public SdkHttpResponse execute(SdkHttpRequest request) throws IOException {
        SdkHttpResponse response = delegate.execute(withBearerToken(request));
        if (response.statusCode() == 401) {
            tokenProvider.invalidate();
            response = delegate.execute(withBearerToken(request));
        }
        return response;
    }

    @Override
    public CompletableFuture<SdkHttpResponse> executeAsync(SdkHttpRequest request) {
        SdkHttpRequest authedRequest;
        try {
            authedRequest = withBearerToken(request);
        } catch (AuthenticationException e) {
            return CompletableFuture.failedFuture(e);
        }
        return delegate.executeAsync(authedRequest)
                .thenCompose(response -> {
                    if (response.statusCode() == 401) {
                        tokenProvider.invalidate();
                        try {
                            return delegate.executeAsync(withBearerToken(request));
                        } catch (AuthenticationException e) {
                            return CompletableFuture.failedFuture(e);
                        }
                    }
                    return CompletableFuture.completedFuture(response);
                });
    }

    private SdkHttpRequest withBearerToken(SdkHttpRequest request) {
        return request.toBuilder()
                .header("Authorization", "Bearer " + tokenProvider.getAccessToken())
                .build();
    }
}
