package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Thrown when an authentication error occurs, such as invalid credentials or an expired token.
 * This typically corresponds to an HTTP 401 response or a failure during token refresh.
 */
public class AuthenticationException extends FulfillmenttoolsException {

    /**
     * Constructs an exception from an HTTP 401 authentication error response.
     *
     * @param statusCode the HTTP status code (typically 401).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public AuthenticationException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }

    /**
     * Constructs an exception for authentication failures that occur before an HTTP response,
     * such as during token refresh or credential validation.
     *
     * @param message a message describing the authentication error.
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs an exception for authentication failures with an underlying cause.
     *
     * @param message a message describing the authentication error.
     * @param cause the underlying exception that caused this failure.
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
