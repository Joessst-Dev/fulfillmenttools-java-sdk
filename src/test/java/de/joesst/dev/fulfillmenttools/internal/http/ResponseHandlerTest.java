package de.joesst.dev.fulfillmenttools.internal.http;

import com.fasterxml.jackson.core.type.TypeReference;
import de.joesst.dev.fulfillmenttools.*;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ResponseHandlerTest {

    private static final String SINGLE_ERROR_BODY = """
            [{"description":"Missing name","summary":"Mandatory attribute missing","requestVersion":1,"version":2}]
            """;

    private ResponseHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ResponseHandler(new JsonCodec());
    }

    // --- 2xx happy path ---

    @Test
    void handle_on2xx_decodesBodyToTargetType() {
        // Given
        String json = "{\"id\":\"order-1\",\"status\":\"OPEN\"}";
        SdkHttpResponse response = response(200, json);

        // When
        TestOrder result = handler.handle(response, TestOrder.class);

        // Then
        assertThat(result.id()).isEqualTo("order-1");
        assertThat(result.status()).isEqualTo("OPEN");
    }

    @Test
    void handle_on2xx_decodesBodyWithTypeReference() {
        // Given
        String json = "[{\"id\":\"a\"},{\"id\":\"b\"}]";
        SdkHttpResponse response = response(200, json);

        // When
        List<TestOrder> result = handler.handle(response, new TypeReference<>() {});

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo("a");
    }

    @Test
    void handleVoid_on204_doesNotThrow() {
        // Given
        SdkHttpResponse response = response(204, "");

        // When / Then
        assertThatNoException().isThrownBy(() -> handler.handleVoid(response));
    }

    // --- Status-to-exception mapping ---

    @ParameterizedTest(name = "HTTP {0} → {1}")
    @CsvSource({
            "400, ValidationException",
            "401, AuthenticationException",
            "403, AuthorizationException",
            "404, NotFoundException",
            "409, ConflictException",
            "422, ValidationException",
            "429, RateLimitException",
            "500, ServerException",
            "503, ServerException",
    })
    void handle_mapsStatusCodeToCorrectExceptionType(int status, String exceptionSimpleName) {
        // Given
        SdkHttpResponse response = response(status, SINGLE_ERROR_BODY);

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(FulfillmenttoolsException.class)
                .satisfies(ex -> assertThat(ex.getClass().getSimpleName()).isEqualTo(exceptionSimpleName));
    }

    // --- Error body parsing ---

    @Test
    void handle_populatesApiErrorsFromResponseBody() {
        // Given
        SdkHttpResponse response = response(404, SINGLE_ERROR_BODY);

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(NotFoundException.class)
                .satisfies(ex -> {
                    FulfillmenttoolsException fte = (FulfillmenttoolsException) ex;
                    assertThat(fte.errors()).hasSize(1);
                    assertThat(fte.errors().get(0).summary()).isEqualTo("Mandatory attribute missing");
                    assertThat(fte.errors().get(0).description()).isEqualTo("Missing name");
                    assertThat(fte.errors().get(0).requestVersion()).isEqualTo(1);
                    assertThat(fte.errors().get(0).version()).isEqualTo(2);
                });
    }

    @Test
    void handle_usesFirstErrorSummaryAsExceptionMessage() {
        // Given
        SdkHttpResponse response = response(404, SINGLE_ERROR_BODY);

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .hasMessage("Mandatory attribute missing");
    }

    @Test
    void handle_toleratesEmptyErrorBody() {
        // Given
        SdkHttpResponse response = response(500, "");

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(ServerException.class)
                .satisfies(ex -> assertThat(((FulfillmenttoolsException) ex).errors()).isEmpty());
    }

    @Test
    void handle_toleratesNonJsonErrorBody() {
        // Given
        SdkHttpResponse response = response(500, "Internal Server Error");

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(ServerException.class)
                .satisfies(ex -> assertThat(((FulfillmenttoolsException) ex).errors()).isEmpty());
    }

    // --- Metadata ---

    @Test
    void handle_capturesRequestIdFromResponseHeader() {
        // Given
        SdkHttpResponse response = responseWithHeaders(
                404, SINGLE_ERROR_BODY, Map.of("x-request-id", "req-abc-123"));

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(NotFoundException.class)
                .satisfies(ex -> assertThat(((FulfillmenttoolsException) ex).requestId())
                        .isEqualTo("req-abc-123"));
    }

    @Test
    void handle_capturesStatusCode() {
        // Given
        SdkHttpResponse response = response(404, SINGLE_ERROR_BODY);

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(NotFoundException.class)
                .satisfies(ex -> assertThat(((FulfillmenttoolsException) ex).statusCode()).isEqualTo(404));
    }

    // --- RateLimitException ---

    @Test
    void handle_on429_parsesRetryAfterHeader() {
        // Given
        SdkHttpResponse response = responseWithHeaders(
                429, SINGLE_ERROR_BODY, Map.of("retry-after", "30"));

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(RateLimitException.class)
                .satisfies(ex -> assertThat(((RateLimitException) ex).retryAfter())
                        .contains(Duration.ofSeconds(30)));
    }

    @Test
    void handle_on429_withoutRetryAfterHeader_returnsEmptyOptional() {
        // Given
        SdkHttpResponse response = response(429, SINGLE_ERROR_BODY);

        // When / Then
        assertThatThrownBy(() -> handler.handle(response, TestOrder.class))
                .isInstanceOf(RateLimitException.class)
                .satisfies(ex -> assertThat(((RateLimitException) ex).retryAfter()).isEmpty());
    }

    // --- Helpers ---

    private static SdkHttpResponse response(int status, String body) {
        return responseWithHeaders(status, body, Collections.emptyMap());
    }

    private static SdkHttpResponse responseWithHeaders(int status, String body,
                                                        Map<String, String> headers) {
        return new SdkHttpResponse(status, headers,
                body.getBytes(StandardCharsets.UTF_8));
    }

    private record TestOrder(String id, String status) {}
}
