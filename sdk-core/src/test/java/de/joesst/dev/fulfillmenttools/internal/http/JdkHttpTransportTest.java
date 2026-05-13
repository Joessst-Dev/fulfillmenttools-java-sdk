package de.joesst.dev.fulfillmenttools.internal.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.internal.Version;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class JdkHttpTransportTest {

    private static WireMockServer server;
    private JdkHttpTransport transport;

    @BeforeAll
    static void startServer() {
        server = new WireMockServer(wireMockConfig().dynamicPort());
        server.start();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void setUp() {
        server.resetAll();
        transport = new JdkHttpTransport();
    }

    // --- Default headers ---

    @Test
    void get_setsUserAgentAndAcceptHeaders() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/test"))
                .willReturn(aResponse().withStatus(200).withBody("{}")));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(server.baseUrl() + "/test")
                .build();
        transport.execute(request);

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/test"))
                .withHeader("User-Agent", equalTo(Version.USER_AGENT))
                .withHeader("Accept", equalTo("application/json")));
    }

    // --- Query params ---

    @Test
    void get_appendsQueryParamsToUrl() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/orders"))
                .willReturn(aResponse().withStatus(200).withBody("[]")));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(server.baseUrl() + "/orders")
                .queryParam("size", "20")
                .queryParam("status", "OPEN")
                .build();
        transport.execute(request);

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/orders"))
                .withQueryParam("size", equalTo("20"))
                .withQueryParam("status", equalTo("OPEN")));
    }

    // --- Request body and custom headers ---

    @Test
    void post_sendsBodyAndCustomHeaders() throws Exception {
        // Given
        byte[] body = "{\"name\":\"test\"}".getBytes();
        server.stubFor(post(urlPathEqualTo("/facilities"))
                .willReturn(aResponse().withStatus(201).withBody("{\"id\":\"abc\"}")));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(server.baseUrl() + "/facilities")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer token123")
                .body(body)
                .build();
        SdkHttpResponse response = transport.execute(request);

        // Then
        assertThat(response.statusCode()).isEqualTo(201);
        server.verify(postRequestedFor(urlPathEqualTo("/facilities"))
                .withHeader("Authorization", equalTo("Bearer token123"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\"name\":\"test\"}")));
    }

    // --- Response mapping ---

    @Test
    void execute_returnsStatusCodeAndBodyBytes() throws Exception {
        // Given
        String responseBody = "{\"id\":\"order-1\",\"status\":\"OPEN\"}";
        server.stubFor(get(urlPathEqualTo("/orders/order-1"))
                .willReturn(aResponse().withStatus(200).withBody(responseBody)));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(server.baseUrl() + "/orders/order-1")
                .build();
        SdkHttpResponse response = transport.execute(request);

        // Then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.bodyAsString()).isEqualTo(responseBody);
    }

    @Test
    void execute_surfacesNon2xxWithoutThrowingAtTransportLayer() throws Exception {
        // Given — error mapping is ResponseHandler's job (P3), transport just passes through
        server.stubFor(get(urlPathEqualTo("/not-found"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("[{\"summary\":\"Not found\",\"description\":\"Resource does not exist\",\"requestVersion\":1,\"version\":2}]")));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(server.baseUrl() + "/not-found")
                .build();
        SdkHttpResponse response = transport.execute(request);

        // Then
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.bodyAsString()).contains("Not found");
    }

    @Test
    void execute_lowercasesResponseHeaderNames() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/headers"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Request-Id", "req-abc-123")
                        .withBody("{}")));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(server.baseUrl() + "/headers")
                .build();
        SdkHttpResponse response = transport.execute(request);

        // Then
        assertThat(response.header("x-request-id")).isEqualTo("req-abc-123");
    }

    // --- Async ---

    @Test
    void executeAsync_completesWithSameResultAsSyncExecute() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/async"))
                .willReturn(aResponse().withStatus(200).withBody("{\"ok\":true}")));

        // When
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(server.baseUrl() + "/async")
                .build();
        SdkHttpResponse response = transport.executeAsync(request).get();

        // Then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.bodyAsString()).isEqualTo("{\"ok\":true}");
    }
}
