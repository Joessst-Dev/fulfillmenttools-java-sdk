package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Thrown when a validation error occurs, such as invalid input parameters or malformed request data.
 * This corresponds to an HTTP 400 response.
 */
public class ValidationException extends FulfillmenttoolsException {

    /**
     * Constructs an exception from an HTTP 400 validation error response.
     *
     * @param statusCode the HTTP status code (typically 400).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public ValidationException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
