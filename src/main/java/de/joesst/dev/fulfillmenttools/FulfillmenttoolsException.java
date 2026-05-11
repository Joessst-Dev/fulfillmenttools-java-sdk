package de.joesst.dev.fulfillmenttools;

import java.util.List;

public class FulfillmenttoolsException extends RuntimeException {

    private final int statusCode;
    private final String requestId;
    private final List<ApiError> errors;

    /** Used by ResponseHandler when an HTTP error response is received. */
    public FulfillmenttoolsException(int statusCode, String requestId, List<ApiError> errors) {
        super(deriveMessage(statusCode, errors));
        this.statusCode = statusCode;
        this.requestId = requestId;
        this.errors = List.copyOf(errors);
    }

    /** Used internally for non-HTTP failures (token refresh, transport IO). */
    public FulfillmenttoolsException(String message) {
        super(message);
        this.statusCode = -1;
        this.requestId = null;
        this.errors = List.of();
    }

    public FulfillmenttoolsException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
        this.requestId = null;
        this.errors = List.of();
    }

    /** HTTP status code, or {@code -1} if the failure was not HTTP-based. */
    public int statusCode() { return statusCode; }

    /** Value of the {@code x-request-id} response header, or {@code null} if absent. */
    public String requestId() { return requestId; }

    /** Structured error details returned by the API. Empty list for non-HTTP failures. */
    public List<ApiError> errors() { return errors; }

    private static String deriveMessage(int statusCode, List<ApiError> errors) {
        if (!errors.isEmpty() && errors.get(0).summary() != null) {
            return errors.get(0).summary();
        }
        return "HTTP " + statusCode;
    }
}
