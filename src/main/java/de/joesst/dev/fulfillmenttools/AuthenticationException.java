package de.joesst.dev.fulfillmenttools;

import java.util.List;

public class AuthenticationException extends FulfillmenttoolsException {

    /** From ResponseHandler on HTTP 401. */
    public AuthenticationException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }

    /** From TokenProvider before an HTTP response is available. */
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
