package de.joesst.dev.fulfillmenttools;

import java.util.List;

public class NotFoundException extends FulfillmenttoolsException {

    public NotFoundException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
