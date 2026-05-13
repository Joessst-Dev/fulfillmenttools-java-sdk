package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Thrown when a server error occurs on the fulfillmenttools platform.
 * This corresponds to an HTTP 5xx response.
 */
public class ServerException extends FulfillmenttoolsException {

    /**
     * Constructs an exception from an HTTP 5xx server error response.
     *
     * @param statusCode the HTTP status code (typically 500-599).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public ServerException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
