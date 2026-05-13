package de.joesst.dev.fulfillmenttools;

import java.util.List;

/**
 * Thrown when a conflict error occurs, typically when attempting to create or modify a resource
 * in a way that violates business rules or uniqueness constraints.
 * This corresponds to an HTTP 409 response.
 */
public class ConflictException extends FulfillmenttoolsException {

    /**
     * Constructs an exception from an HTTP 409 conflict error response.
     *
     * @param statusCode the HTTP status code (typically 409).
     * @param requestId the value of the {@code x-request-id} response header, or {@code null} if absent.
     * @param errors the list of structured error details from the API response.
     */
    public ConflictException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
