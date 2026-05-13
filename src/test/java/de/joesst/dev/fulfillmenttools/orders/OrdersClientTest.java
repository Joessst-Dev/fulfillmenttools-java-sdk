package de.joesst.dev.fulfillmenttools.orders;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class OrdersClientTest {

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

    // --- get ---

    @Test
    void get_returnsDeserializedOrder() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("""
                        {
                          "id": "ord-1",
                          "tenantOrderId": "ext-001",
                          "status": "OPEN",
                          "created": "2024-03-01T10:00:00Z",
                          "lastModified": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        Order order = client.orders().get(new OrderId("ord-1"));

        // Then
        assertThat(order.id().value()).isEqualTo("ord-1");
        assertThat(order.tenantOrderId()).isEqualTo("ext-001");
        assertThat(order.status()).isEqualTo("OPEN");
        assertThat(order.created()).isNotNull();
        assertThat(order.lastModified()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/ord-1"))
                .willReturn(okJson("{\"id\":\"ord-1\"}")));

        // When
        client.orders().get(new OrderId("ord-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/orders/ord-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Order not found","description":"No order with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.orders().get(new OrderId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order not found")
                .satisfies(ex -> assertThat(((NotFoundException) ex).statusCode()).isEqualTo(404));
    }

    // --- list ---

    @Test
    void list_returnsPageWithItemsAndCursor() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .willReturn(okJson("""
                        {
                          "orders": [
                            {"id":"ord-1","status":"OPEN"},
                            {"id":"ord-2","status":"LOCKED"}
                          ],
                          "nextCursor": "cursor-page-2"
                        }
                        """)));

        // When
        Page<Order> page = client.orders().list(OrderListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("ord-1");
        assertThat(page.items().get(1).id().value()).isEqualTo("ord-2");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .willReturn(okJson("{\"orders\":[]}")));

        // When
        client.orders().list(OrderListRequest.builder().size(10).startAfterId("cursor-abc").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/orders"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc")));
    }

    @Test
    void list_withNoCursor_hasMoreIsFalse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .willReturn(okJson("{\"orders\":[{\"id\":\"ord-1\"}]}")));

        // When
        Page<Order> page = client.orders().list(OrderListRequest.builder().build());

        // Then
        assertThat(page.hasMore()).isFalse();
        assertThat(page.nextCursor()).isNull();
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given — page 1 has 2 orders and a cursor; page 2 has 1 order and no cursor
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"orders":[{"id":"ord-1"},{"id":"ord-2"}],"nextCursor":"cur-2"}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"orders":[{"id":"ord-3"}]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (Order o : client.orders().listAll(OrderListRequest.builder().size(2).build())) {
            ids.add(o.id().value());
        }

        // Then
        assertThat(ids).containsExactly("ord-1", "ord-2", "ord-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/orders")));
    }

    @Test
    void listAll_doesNotFetchExtraPageAfterLastCursor() {
        // Given — single page, no cursor
        server.stubFor(get(urlPathEqualTo("/api/orders"))
                .willReturn(okJson("{\"orders\":[{\"id\":\"ord-1\"}]}")));

        // When
        List<Order> orders = new ArrayList<>();
        client.orders().listAll(OrderListRequest.builder().build()).forEach(orders::add);

        // Then — only one network call made
        assertThat(orders).hasSize(1);
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/orders")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
