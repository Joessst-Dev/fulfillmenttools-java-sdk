package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Thrown when a requested resource cannot be found.
 * This corresponds to an HTTP 404 response.
 */
public class NotFoundException extends FulfillmenttoolsException {

    /**
     * Constructs an exception from an HTTP 404 not found error response.
     *
     * @param statusCode the HTTP status code (typically 404).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public NotFoundException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
