package de.joesst.dev.fulfillmenttools.internal.http;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public final class RetryingTransport implements HttpTransport {

    public static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final long BASE_DELAY_MS = 500;
    private static final long MAX_DELAY_MS = 30_000;

    private final HttpTransport delegate;
    private final int maxAttempts;
    private final BiFunction<SdkHttpResponse, Integer, Long> delayFn;

    public RetryingTransport(HttpTransport delegate) {
        this(delegate, DEFAULT_MAX_ATTEMPTS, RetryingTransport::defaultDelay);
    }

    public RetryingTransport(HttpTransport delegate, int maxAttempts) {
        this(delegate, maxAttempts, RetryingTransport::defaultDelay);
    }

    /** Package-private: allows injecting a zero-delay function in tests. */
    RetryingTransport(HttpTransport delegate, int maxAttempts,
                      BiFunction<SdkHttpResponse, Integer, Long> delayFn) {
        if (maxAttempts < 1) throw new IllegalArgumentException("maxAttempts must be >= 1");
        this.delegate = delegate;
        this.maxAttempts = maxAttempts;
        this.delayFn = delayFn;
    }

    @Override
    public SdkHttpResponse execute(SdkHttpRequest request) throws IOException {
        SdkHttpResponse response = delegate.execute(request);
        for (int attempt = 2; attempt <= maxAttempts && shouldRetry(response); attempt++) {
            sleep(delayFn.apply(response, attempt));
            response = delegate.execute(request);
        }
        return response;
    }

    @Override
    public CompletableFuture<SdkHttpResponse> executeAsync(SdkHttpRequest request) {
        return executeAsync(request, 2);
    }

    private CompletableFuture<SdkHttpResponse> executeAsync(SdkHttpRequest request, int nextAttempt) {
        return delegate.executeAsync(request).thenCompose(response -> {
            if (!shouldRetry(response) || nextAttempt > maxAttempts) {
                return CompletableFuture.completedFuture(response);
            }
            long delayMs = delayFn.apply(response, nextAttempt);
            Executor delayed = CompletableFuture.delayedExecutor(delayMs, TimeUnit.MILLISECONDS);
            return CompletableFuture.supplyAsync(() -> null, delayed)
                    .thenCompose(ignored -> executeAsync(request, nextAttempt + 1));
        });
    }

    private static boolean shouldRetry(SdkHttpResponse response) {
        int status = response.statusCode();
        return status == 429 || status >= 500;
    }

    private static long defaultDelay(SdkHttpResponse response, int attempt) {
        if (response.statusCode() == 429) {
            String retryAfter = response.header("retry-after");
            if (retryAfter != null) {
                try {
                    return Long.parseLong(retryAfter.trim()) * 1_000L;
                } catch (NumberFormatException ignored) {
                    // fall through to exponential backoff
                }
            }
        }
        // Exponential backoff: 500 ms, 1000 ms, 2000 ms, ... capped at 30 s, with jitter
        long base = BASE_DELAY_MS * (1L << (attempt - 2)); // attempt 2 → 500ms, 3 → 1000ms
        long jitter = (long) (Math.random() * BASE_DELAY_MS);
        return Math.min(base + jitter, MAX_DELAY_MS);
    }

    private static void sleep(long ms) throws IOException {
        if (ms <= 0) return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Retry sleep interrupted", e);
        }
    }
}
