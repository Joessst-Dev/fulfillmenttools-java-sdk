package de.joesst.dev.fulfillmenttools.orders;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.OrderId;
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
                        .consumer(minimalConsumer())
                        .tenantOrderId("ext-new")
                        .build());

        // Then
        assertThat(order.id().value()).isEqualTo("ord-new");
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
                .consumer(minimalConsumer())
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

    @Test
    void create_requiresConsumer() {
        // When / Then
        assertThatThrownBy(() -> CreateOrderRequest.builder()
                .orderDate(Instant.parse("2024-03-01T10:00:00Z"))
                .orderLineItems(List.of(
                        OrderLineItemForCreation.builder()
                                .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-1").build())
                                .quantity(1)
                                .build()))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("consumer");
    }

    // --- update ---

    @Test
    void update_returnsUpdatedOrder() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("""
                        {"id":"ord-1","status":"LOCKED"}
                        """)));

        // When
        Order order = client.orders().update(new OrderId("ord-1"),
                UpdateOrderRequest.builder().version(1).comment("routing change").build());

        // Then
        assertThat(order.id().value()).isEqualTo("ord-1");
        assertThat(order.status()).isEqualTo("LOCKED");
    }

    @Test
    void update_sendsVersionAndCommentInBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("{\"id\":\"ord-1\"}")));

        // When
        client.orders().update(new OrderId("ord-1"), UpdateOrderRequest.builder().version(3).comment("reroute").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/orders/ord-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("3")))
                .withRequestBody(matchingJsonPath("$.comment", equalTo("reroute"))));
    }

    @Test
    void update_requiresVersion() {
        // When / Then
        assertThatThrownBy(() -> UpdateOrderRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
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
        assertThatThrownBy(() -> client.orders().update(new OrderId("missing"), UpdateOrderRequest.builder().version(1).build()))
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
        assertThatCode(() -> client.orders().delete(new OrderId("ord-1"))).doesNotThrowAnyException();
    }

    @Test
    void delete_sendsBearerTokenHeader() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(aResponse().withStatus(200)));

        // When
        client.orders().delete(new OrderId("ord-1"));

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
        assertThatThrownBy(() -> client.orders().delete(new OrderId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order not found");
    }

    // --- cancel ---

    @Test
    void cancel_sendsActionWithNameCancel() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/ord-1/actions"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"CANCELLED\"}")));

        // When
        Order order = client.orders().cancel(new OrderId("ord-1"), CancelOrderRequest.builder().version(2).build());

        // Then
        assertThat(order.status()).isEqualTo("CANCELLED");
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/ord-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("CANCEL")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2"))));
    }

    @Test
    void cancel_withReasonId_includesReasonInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/ord-1/actions"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"CANCELLED\"}")));

        // When
        client.orders().cancel(new OrderId("ord-1"), CancelOrderRequest.builder().version(1).cancelationReasonId("reason-abc").build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/ord-1/actions"))
                .withRequestBody(matchingJsonPath("$.cancelationReasonId", equalTo("reason-abc"))));
    }

    @Test
    void cancel_requiresVersion() {
        assertThatThrownBy(() -> CancelOrderRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- forceCancel ---

    @Test
    void forceCancel_sendsActionWithNameForceCancel() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/ord-1/actions"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"CANCELLED\"}")));

        // When
        client.orders().forceCancel(new OrderId("ord-1"), 3);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/ord-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("FORCE_CANCEL")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("3"))));
    }

    // --- unlock ---

    @Test
    void unlock_sendsActionWithNameUnlock() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/ord-1/actions"))
                .willReturn(okJson("{\"id\":\"ord-1\",\"status\":\"OPEN\"}")));

        // When
        client.orders().unlock(new OrderId("ord-1"), 5);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/ord-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("UNLOCK")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("5"))));
    }

    @Test
    void unlock_withTargetTime_includesTargetTimeInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/ord-1/actions"))
                .willReturn(okJson("{\"id\":\"ord-1\"}")));

        // When
        client.orders().unlock(new OrderId("ord-1"), 1, Instant.parse("2025-01-15T00:00:00Z"));

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/ord-1/actions"))
                .withRequestBody(matchingJsonPath("$.targetTime", equalTo("2025-01-15T00:00:00Z"))));
    }

    // --- search ---

    @Test
    void search_sendsPostToSearchEndpoint() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/search"))
                .willReturn(okJson("{\"orders\":[{\"id\":\"ord-1\"}],\"pageInfo\":{\"endCursor\":\"c1\",\"startCursor\":\"s1\",\"hasNextPage\":true,\"hasPreviousPage\":false}}")));

        // When
        var page = client.orders().search(
                OrderSearchRequest.builder()
                        .query(OrderSearchQuery.builder().statusEq("OPEN").build())
                        .size(10)
                        .build());

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).id().value()).isEqualTo("ord-1");
        assertThat(page.nextCursor()).isEqualTo("c1");
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/search"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void search_serializesQueryCorrectly() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/orders/search"))
                .willReturn(okJson("{\"orders\":[],\"pageInfo\":{\"endCursor\":null,\"startCursor\":null,\"hasNextPage\":false,\"hasPreviousPage\":false}}")));

        // When
        client.orders().search(OrderSearchRequest.builder()
                .query(OrderSearchQuery.builder()
                        .statusIn("OPEN", "LOCKED")
                        .tenantOrderIdEq("ext-001")
                        .build())
                .size(25)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/orders/search"))
                .withRequestBody(matchingJsonPath("$.query.status.in[0]", equalTo("OPEN")))
                .withRequestBody(matchingJsonPath("$.query.status.in[1]", equalTo("LOCKED")))
                .withRequestBody(matchingJsonPath("$.query.tenantOrderId.eq", equalTo("ext-001")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("25"))));
    }

    // --- list query params ---

    @Test
    void list_sendsTenantOrderIdAndConsumerIdQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .willReturn(okJson("{\"orders\":[]}")));

        // When
        client.orders().list(OrderListRequest.builder()
                .tenantOrderId("ext-001")
                .consumerId("con-1")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/orders"))
                .withQueryParam("tenantOrderId", equalTo("ext-001"))
                .withQueryParam("consumerId", equalTo("con-1")));
    }

    // --- Helpers ---

    private static OrderForCreationConsumer minimalConsumer() {
        return OrderForCreationConsumer.builder().consumerId("con-1").build();
    }

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
