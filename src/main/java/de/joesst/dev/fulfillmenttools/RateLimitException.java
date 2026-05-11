package de.joesst.dev.fulfillmenttools;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class RateLimitException extends FulfillmenttoolsException {

    private final Duration retryAfter;

    public RateLimitException(int statusCode, String requestId, List<ApiError> errors, Duration retryAfter) {
        super(statusCode, requestId, errors);
        this.retryAfter = retryAfter;
    }

    /** Suggested wait time from the {@code Retry-After} header, or empty if the header was absent. */
    public Optional<Duration> retryAfter() {
        return Optional.ofNullable(retryAfter);
    }
}
