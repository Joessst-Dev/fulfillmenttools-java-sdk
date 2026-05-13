package de.joesst.dev.fulfillmenttools;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Exception thrown when the fulfillmenttools API returns a 429 (Too Many Requests) response.
 *
 * <p>In addition to the structured error details inherited from {@link FulfillmenttoolsException},
 * this exception provides a suggested retry delay via {@link #retryAfter()}.
 */
public class RateLimitException extends FulfillmenttoolsException {

    /** The suggested wait time from the Retry-After header, or null if absent. */
    private final Duration retryAfter;

    /**
     * Constructs a rate limit exception from an HTTP error response.
     *
     * @param statusCode the HTTP status code (typically 429).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     * @param retryAfter the suggested wait time from the {@code Retry-After} header, or {@code null} if absent.
     */
    public RateLimitException(int statusCode, String requestId, List<ApiError> errors, Duration retryAfter) {
        super(statusCode, requestId, errors);
        this.retryAfter = retryAfter;
    }

    /**
     * Returns the suggested wait time from the {@code Retry-After} header.
     *
     * @return the suggested retry delay, or empty if the header was absent.
     */
    public Optional<Duration> retryAfter() {
        return Optional.ofNullable(retryAfter);
    }
}
