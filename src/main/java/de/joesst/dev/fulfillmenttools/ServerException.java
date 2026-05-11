package de.joesst.dev.fulfillmenttools;

import java.util.List;

public class ServerException extends FulfillmenttoolsException {

    public ServerException(int statusCode, String requestId, List<ApiError> errors) {
        super(statusCode, requestId, errors);
    }
}
