package de.joesst.dev.fulfillmenttools;

import java.util.List;

public class AuthorizationException extends FulfillmenttoolsException {

    public AuthorizationException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
