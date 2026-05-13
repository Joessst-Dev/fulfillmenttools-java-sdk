package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Thrown when an authorization error occurs, such as insufficient permissions to perform an action.
 * This typically corresponds to an HTTP 403 response.
 */
public class AuthorizationException extends FulfillmenttoolsException {

    /**
     * Constructs an exception from an HTTP 403 authorization error response.
     *
     * @param statusCode the HTTP status code (typically 403).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public AuthorizationException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
