package de.joesst.dev.fulfillmenttools.internal.http;

import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticatingTransportTest {

    // --- Happy path ---

    @Test
    void execute_injectsBearerTokenFromProvider() throws Exception {
        // Given
        CapturingTransport inner = new CapturingTransport(200);
        AuthenticatingTransport transport = new AuthenticatingTransport(inner, fixedToken("my-token"));

        // When
        transport.execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET).url("https://api.example.com/orders").build());

        // Then
        assertThat(inner.lastRequest().headers().get("Authorization")).isEqualTo("Bearer my-token");
    }

    // --- 401 retry ---

    @Test
    void execute_on401_invalidatesAndRetiesOnceWithFreshToken() throws Exception {
        // Given — first call returns 401, second returns 200
        AtomicInteger callCount = new AtomicInteger();
        CapturingTransport inner = new CapturingTransport(() -> callCount.incrementAndGet() == 1 ? 401 : 200);

        AtomicInteger invalidateCount = new AtomicInteger();
        AtomicInteger tokenIndex = new AtomicInteger();
        String[] tokenPool = {"stale-token", "fresh-token"};
        TokenProvider tokenProvider = new TokenProvider() {
            @Override
            public String getAccessToken() {
                return tokenPool[Math.min(tokenIndex.get(), tokenPool.length - 1)];
            }
            @Override
            public void invalidate() {
                invalidateCount.incrementAndGet();
                tokenIndex.incrementAndGet();
            }
        };
        AuthenticatingTransport transport = new AuthenticatingTransport(inner, tokenProvider);

        // When
        SdkHttpResponse response = transport.execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET).url("https://api.example.com/orders").build());

        // Then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(inner.requestCount()).isEqualTo(2);
        assertThat(invalidateCount.get()).isEqualTo(1);
        assertThat(inner.requestAt(0).headers().get("Authorization")).isEqualTo("Bearer stale-token");
        assertThat(inner.requestAt(1).headers().get("Authorization")).isEqualTo("Bearer fresh-token");
    }

    @Test
    void execute_doesNotRetryMoreThanOnceOn401() throws Exception {
        // Given — every response is 401
        CapturingTransport inner = new CapturingTransport(401);
        AuthenticatingTransport transport = new AuthenticatingTransport(inner, fixedToken("token"));

        // When
        SdkHttpResponse response = transport.execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET).url("https://api.example.com/x").build());

        // Then — exactly two attempts, no infinite loop
        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(inner.requestCount()).isEqualTo(2);
    }

    // --- Async ---

    @Test
    void executeAsync_injectsBearerTokenAndCompletesSuccessfully() throws Exception {
        // Given
        CapturingTransport inner = new CapturingTransport(200);
        AuthenticatingTransport transport = new AuthenticatingTransport(inner, fixedToken("async-token"));

        // When
        SdkHttpResponse response = transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.GET).url("https://api.example.com/async").build()).get();

        // Then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(inner.lastRequest().headers().get("Authorization")).isEqualTo("Bearer async-token");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }

    private static final class CapturingTransport implements HttpTransport {

        private final IntSupplier statusSupplier;
        private final List<SdkHttpRequest> requests = new ArrayList<>();

        CapturingTransport(int fixedStatus) {
            this(() -> fixedStatus);
        }

        CapturingTransport(IntSupplier statusSupplier) {
            this.statusSupplier = statusSupplier;
        }

        @Override
        public SdkHttpResponse execute(SdkHttpRequest request) {
            requests.add(request);
            return new SdkHttpResponse(statusSupplier.getAsInt(), Collections.emptyMap(), new byte[0]);
        }

        @Override
        public CompletableFuture<SdkHttpResponse> executeAsync(SdkHttpRequest request) {
            requests.add(request);
            return CompletableFuture.completedFuture(
                    new SdkHttpResponse(statusSupplier.getAsInt(), Collections.emptyMap(), new byte[0]));
        }

        SdkHttpRequest lastRequest() { return requests.get(requests.size() - 1); }
        SdkHttpRequest requestAt(int index) { return requests.get(index); }
        int requestCount() { return requests.size(); }
    }
}
