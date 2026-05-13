package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Base exception for all fulfillmenttools SDK errors. This is a runtime exception that wraps
 * both HTTP and non-HTTP failures, providing structured access to error details when available.
 */
public class FulfillmenttoolsException extends RuntimeException {

    /** The HTTP status code, or -1 if not HTTP-based. */
    private final int statusCode;

    /** The request ID from the x-request-id header, or null if absent. */
    private final String requestId;

    /** Structured error details from the API response. */
    private final List<ApiError> errors;

    /**
     * Constructs an exception from an HTTP error response.
     *
     * @param statusCode the HTTP status code of the error response.
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public FulfillmenttoolsException(int statusCode, String requestId, List<ApiError> errors) {
        super(deriveMessage(statusCode, errors));
        this.statusCode = statusCode;
        this.requestId = requestId;
        this.errors = List.copyOf(errors);
    }

    /**
     * Constructs an exception for non-HTTP failures such as token refresh or transport IO errors.
     *
     * @param message a message describing the error.
     */
    public FulfillmenttoolsException(String message) {
        super(message);
        this.statusCode = -1;
        this.requestId = null;
        this.errors = List.of();
    }

    /**
     * Constructs an exception for non-HTTP failures with an underlying cause.
     *
     * @param message a message describing the error.
     * @param cause the underlying exception that caused this failure.
     */
    public FulfillmenttoolsException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
        this.requestId = null;
        this.errors = List.of();
    }

    /**
     * Returns the HTTP status code of the error response, or {@code -1} if the failure was not HTTP-based.
     *
     * @return the HTTP status code, or {@code -1} for non-HTTP failures.
     */
    public int statusCode() { return statusCode; }

    /**
     * Returns the value of the {@code x-request-id} response header.
     *
     * @return the request ID, or {@code null} if absent.
     */
    public String requestId() { return requestId; }

    /**
     * Returns the structured error details returned by the API.
     *
     * @return the list of API error objects; empty list for non-HTTP failures.
     */
    public List<ApiError> errors() { return errors; }

    private static String deriveMessage(int statusCode, List<ApiError> errors) {
        if (!errors.isEmpty() && errors.get(0).summary() != null) {
            return errors.get(0).summary();
        }
        return "HTTP " + statusCode;
    }
}
