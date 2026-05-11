package de.joesst.dev.fulfillmenttools;

import java.util.List;

public class ConflictException extends FulfillmenttoolsException {

    public ConflictException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
