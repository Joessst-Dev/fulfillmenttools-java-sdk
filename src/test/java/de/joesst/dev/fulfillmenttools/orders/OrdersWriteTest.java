package de.joesst.dev.fulfillmenttools.orders;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class OrdersWriteTest {

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
                .build();
    }

    // --- create ---

    @Test
    void create_returnsCreatedOrder() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json").withBody("""
                        {"id":"ord-new","tenantOrderId":"ext-new","status":"OPEN"}
                        """)));

        // When
        Order order = client.orders().create(
                CreateOrderRequest.builder()
                        .orderDate(Instant.parse("2024-03-01T10:00:00Z"))
                        .orderLineItems(List.of(
                                OrderLineItemForCreation.builder()
                                        .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-1").build())
                                        .quantity(1)
                                        .build()))
                        .tenantOrderId("ext-new")
                        .build());

        // Then
        assertThat(order.id()).isEqualTo("ord-new");
        assertThat(order.tenantOrderId()).isEqualTo("ext-new");
        assertThat(order.status()).isEqualTo("OPEN");
    }

    @Test
    void create_sendsJsonBodyAndBearerToken() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"ord-1\",\"tenantOrderId\":\"ext-1\",\"status\":\"OPEN\"}")));

        // When
        client.orders().create(CreateOrderRequest.builder()
                .orderDate(Instant.parse("2024-03-01T10:00:00Z"))
                .orderLineItems(List.of(
                        OrderLineItemForCreation.builder()
                                .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-1").build())
                                .quantity(1)
                                .build()))
                .tenantOrderId("ext-1")
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders"))
                .withHeader("Authorization", equalTo("Bearer test-bearer"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.tenantOrderId", equalTo("ext-1"))));
    }

    @Test
    void create_requiresOrderDate() {
        // When / Then
        assertThatThrownBy(() -> CreateOrderRequest.builder()
                .orderLineItems(List.of(
                        OrderLineItemForCreation.builder()
                                .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-1").build())
                                .quantity(1)
                                .build()))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("orderDate");
    }

    @Test
    void create_requiresOrderLineItems() {
        // When / Then
        assertThatThrownBy(() -> CreateOrderRequest.builder()
                .orderDate(Instant.parse("2024-03-01T10:00:00Z"))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("orderLineItems");
    }

    // --- update ---

    @Test
    void update_returnsUpdatedOrder() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("""
                        {"id":"ord-1","tenantOrderId":"ext-updated","status":"LOCKED"}
                        """)));

        // When
        Order order = client.orders().update("ord-1",
                UpdateOrderRequest.builder().tenantOrderId("ext-updated").status("LOCKED").build());

        // Then
        assertThat(order.id()).isEqualTo("ord-1");
        assertThat(order.tenantOrderId()).isEqualTo("ext-updated");
        assertThat(order.status()).isEqualTo("LOCKED");
    }

    @Test
    void update_sendsJsonBodyWithPatch() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"LOCKED\"}")));

        // When
        client.orders().update("ord-1", UpdateOrderRequest.builder().status("LOCKED").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/orders/ord-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.status", equalTo("LOCKED"))));
    }

    @Test
    void update_on404_throwsNotFoundException() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/orders/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Order not found","description":"No order with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.orders().update("missing", UpdateOrderRequest.builder().build()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order not found");
    }

    // --- delete ---

    @Test
    void delete_succeeds() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then — no exception
        assertThatCode(() -> client.orders().delete("ord-1")).doesNotThrowAnyException();
    }

    @Test
    void delete_sendsBearerTokenHeader() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(aResponse().withStatus(200)));

        // When
        client.orders().delete("ord-1");

        // Then
        server.verify(deleteRequestedFor(urlPathEqualTo("/api/orders/ord-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void delete_on404_throwsNotFoundException() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/orders/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Order not found","description":"No order with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.orders().delete("missing"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order not found");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
