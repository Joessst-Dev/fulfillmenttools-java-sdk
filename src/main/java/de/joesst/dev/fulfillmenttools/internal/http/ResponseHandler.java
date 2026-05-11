package de.joesst.dev.fulfillmenttools.internal.http;

import com.fasterxml.jackson.core.type.TypeReference;
import de.joesst.dev.fulfillmenttools.*;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;

import java.time.Duration;
import java.util.List;

public final class ResponseHandler {

    private static final TypeReference<List<ApiError>> ERROR_LIST_TYPE =
            new TypeReference<>() {};

    private final JsonCodec codec;

    public ResponseHandler(JsonCodec codec) {
        this.codec = codec;
    }

    public <T> T handle(SdkHttpResponse response, Class<T> bodyType) {
        if (response.isSuccessful()) {
            return codec.decode(response.body(), bodyType);
        }
        throw mapToException(response);
    }

    public <T> T handle(SdkHttpResponse response, TypeReference<T> typeRef) {
        if (response.isSuccessful()) {
            return codec.decode(response.body(), typeRef);
        }
        throw mapToException(response);
    }

    public byte[] encode(Object value) {
        return codec.encode(value);
    }

    public void handleVoid(SdkHttpResponse response) {
        if (!response.isSuccessful()) {
            throw mapToException(response);
        }
    }

    private FulfillmenttoolsException mapToException(SdkHttpResponse response) {
        List<ApiError> errors = parseErrors(response);
        String requestId = response.header("x-request-id");
        int status = response.statusCode();

        return switch (status) {
            case 400, 422 -> new ValidationException(status, requestId, errors);
            case 401      -> new AuthenticationException(status, requestId, errors);
            case 403      -> new AuthorizationException(status, requestId, errors);
            case 404      -> new NotFoundException(status, requestId, errors);
            case 409      -> new ConflictException(status, requestId, errors);
            case 429      -> new RateLimitException(status, requestId, errors, parseRetryAfter(response));
            default       -> {
                if (status >= 500) {
                    yield new ServerException(status, requestId, errors);
                }
                yield new FulfillmenttoolsException(status, requestId, errors);
            }
        };
    }

    private List<ApiError> parseErrors(SdkHttpResponse response) {
        byte[] body = response.body();
        if (body == null || body.length == 0) {
            return List.of();
        }
        try {
            return codec.decode(body, ERROR_LIST_TYPE);
        } catch (Exception e) {
            return List.of();
        }
    }

    private Duration parseRetryAfter(SdkHttpResponse response) {
        String header = response.header("retry-after");
        if (header == null) return null;
        try {
            return Duration.ofSeconds(Long.parseLong(header.trim()));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
