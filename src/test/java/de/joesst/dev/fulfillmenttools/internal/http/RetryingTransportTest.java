package de.joesst.dev.fulfillmenttools.internal.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.ServerException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.orders.Order;
import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.*;

class RetryingTransportTest {

    private static WireMockServer server;
    private FulfillmenttoolsClient client;

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
        client = FulfillmenttoolsClient.builder()
                .baseUrl(server.baseUrl())
                .tokenProvider(fixedToken("test-bearer"))
                .retryMaxAttempts(3)
                .build();
    }

    @Test
    void get_succeedsOnFirstAttempt_makesOneRequest() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .willReturn(okJson("{\"id\":\"order-1\",\"status\":\"OPEN\"}")));

        // When
        Order order = client.orders().get(new OrderId("order-1"));

        // Then
        assertThat(order.id()).isEqualTo("order-1");
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    @Test
    void get_retriesOn503_succeedsOnSecondAttempt() throws Exception {
        // Given — first call returns 503, second returns 200
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .inScenario("retry").whenScenarioStateIs(STARTED)
                .willReturn(aResponse().withStatus(503))
                .willSetStateTo("ok"));

        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .inScenario("retry").whenScenarioStateIs("ok")
                .willReturn(okJson("{\"id\":\"order-1\",\"status\":\"OPEN\"}")));

        // When
        Order order = client.orders().get(new OrderId("order-1"));

        // Then
        assertThat(order.id()).isEqualTo("order-1");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    @Test
    void get_retriesOn429_succeedsOnSecondAttempt() throws Exception {
        // Given — first call returns 429, second returns 200
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .inScenario("ratelimit").whenScenarioStateIs(STARTED)
                .willReturn(aResponse().withStatus(429).withHeader("Retry-After", "0"))
                .willSetStateTo("ok"));

        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .inScenario("ratelimit").whenScenarioStateIs("ok")
                .willReturn(okJson("{\"id\":\"order-1\",\"status\":\"OPEN\"}")));

        // When
        Order order = client.orders().get(new OrderId("order-1"));

        // Then
        assertThat(order.id()).isEqualTo("order-1");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    @Test
    void get_exhaustsRetries_throwsServerException() {
        // Given — all attempts return 503
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .willReturn(aResponse().withStatus(503)));

        // When / Then
        assertThatThrownBy(() -> client.orders().get(new OrderId("order-1")))
                .isInstanceOf(ServerException.class);
        server.verify(3, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    @Test
    void get_doesNotRetryOn404() {
        // Given — 404 is a client error, should not be retried
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .willReturn(aResponse().withStatus(404)));

        // When / Then
        assertThatThrownBy(() -> client.orders().get(new OrderId("order-1")))
                .isInstanceOf(de.joesst.dev.fulfillmenttools.NotFoundException.class);
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    @Test
    void getAsync_retriesOn503_succeedsOnSecondAttempt() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .inScenario("async-retry").whenScenarioStateIs(STARTED)
                .willReturn(aResponse().withStatus(503))
                .willSetStateTo("ok"));

        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .inScenario("async-retry").whenScenarioStateIs("ok")
                .willReturn(okJson("{\"id\":\"order-1\",\"status\":\"OPEN\"}")));

        // When
        Order order = client.orders().getAsync(new OrderId("order-1")).get();

        // Then
        assertThat(order.id()).isEqualTo("order-1");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    @Test
    void getAsync_exhaustsRetries_completesExceptionally() {
        // Given — all attempts return 503
        server.stubFor(get(urlPathEqualTo("/api/orders/order-1"))
                .willReturn(aResponse().withStatus(503)));

        // When / Then
        assertThatThrownBy(() -> client.orders().getAsync(new OrderId("order-1")).get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(ServerException.class);
        server.verify(3, getRequestedFor(urlPathEqualTo("/api/orders/order-1")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
