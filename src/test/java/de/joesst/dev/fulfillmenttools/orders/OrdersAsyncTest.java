package de.joesst.dev.fulfillmenttools.orders;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class OrdersAsyncTest {

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

    @Test
    void getAsync_returnsOrder() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"OPEN\"}")));

        // When
        Order order = client.orders().getAsync(new OrderId("ord-1")).get();

        // Then
        assertThat(order.id()).isEqualTo("ord-1");
        assertThat(order.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .willReturn(okJson("""
                        {"orders":[{"id":"ord-1"},{"id":"ord-2"}],"nextCursor":"c2"}
                        """)));

        // When
        Page<Order> page = client.orders().listAsync(OrderListRequest.builder().size(2).build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.hasMore()).isTrue();
    }

    @Test
    void createAsync_returnsCreatedOrder() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"ord-new\",\"tenantOrderId\":\"ext-1\",\"status\":\"OPEN\"}")));

        // When
        Order order = client.orders()
                .createAsync(CreateOrderRequest.builder()
                        .orderDate(Instant.parse("2024-03-01T10:00:00Z"))
                        .orderLineItems(List.of(
                                OrderLineItemForCreation.builder()
                                        .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-1").build())
                                        .quantity(1)
                                        .build()))
                        .consumer(OrderForCreationConsumer.builder().consumerId("con-1").build())
                        .tenantOrderId("ext-1")
                        .build()).get();

        // Then
        assertThat(order.id()).isEqualTo("ord-new");
    }

    @Test
    void updateAsync_returnsUpdatedOrder() throws Exception {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"LOCKED\"}")));

        // When
        Order order = client.orders()
                .updateAsync(new OrderId("ord-1"), UpdateOrderRequest.builder().version(1).comment("reroute").build()).get();

        // Then
        assertThat(order.status()).isEqualTo("LOCKED");
    }

    @Test
    void deleteAsync_completesWithoutException() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatCode(() -> client.orders().deleteAsync(new OrderId("ord-1")).get())
                .doesNotThrowAnyException();
    }

    @Test
    void getAsync_on404_wrapsNotFoundExceptionInExecutionException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Order not found","description":"No order","requestVersion":1,"version":2}]
                        """)));

        // When
        CompletableFuture<Order> future = client.orders().getAsync(new OrderId("missing"));

        // Then
        assertThatThrownBy(future::get)
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(NotFoundException.class);
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
