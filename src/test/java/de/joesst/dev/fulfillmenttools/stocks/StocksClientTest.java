package de.joesst.dev.fulfillmenttools.stocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class StocksClientTest {

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

    // --- list ---

    @Test
    void list_returnsPageWithStockItems() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {
                          "stocks": [
                            {"facilityRef":"fac-1","tenantArticleId":"art-1","quantity":10,"unit":"PIECE"},
                            {"facilityRef":"fac-1","tenantArticleId":"art-2","quantity":5,"unit":"PIECE"}
                          ],
                          "nextCursor": "cursor-page-2"
                        }
                        """)));

        // When
        Page<StockItem> page = client.stocks().list(StockListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).facilityRef()).isEqualTo("fac-1");
        assertThat(page.items().get(0).tenantArticleId()).isEqualTo("art-1");
        assertThat(page.items().get(0).quantity()).isEqualTo(10);
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().list(StockListRequest.builder().build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stocks"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().list(StockListRequest.builder()
                .size(10)
                .startAfterId("cursor-abc")
                .facilityRef("fac-1")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stocks"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRef", equalTo("fac-1")));
    }

    @Test
    void list_withNoCursor_hasMoreIsFalse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[{\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"quantity\":1,\"unit\":\"PIECE\"}]}")));

        // When
        Page<StockItem> page = client.stocks().list(StockListRequest.builder().build());

        // Then
        assertThat(page.hasMore()).isFalse();
        assertThat(page.nextCursor()).isNull();
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given — page 1 has 2 items and a cursor; page 2 has 1 item and no cursor
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"stocks":[
                          {"facilityRef":"fac-1","tenantArticleId":"art-1","quantity":1,"unit":"PIECE"},
                          {"facilityRef":"fac-1","tenantArticleId":"art-2","quantity":2,"unit":"PIECE"}
                        ],"nextCursor":"cur-2"}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"stocks":[
                          {"facilityRef":"fac-1","tenantArticleId":"art-3","quantity":3,"unit":"PIECE"}
                        ]}
                        """)));

        // When
        List<String> articleIds = new ArrayList<>();
        for (StockItem s : client.stocks().listAll(StockListRequest.builder().size(2).build())) {
            articleIds.add(s.tenantArticleId());
        }

        // Then
        assertThat(articleIds).containsExactly("art-1", "art-2", "art-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/stocks")));
    }

    @Test
    void listAll_doesNotFetchExtraPageAfterLastCursor() {
        // Given — single page, no cursor
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[{\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"quantity\":1,\"unit\":\"PIECE\"}]}")));

        // When
        List<StockItem> items = new ArrayList<>();
        client.stocks().listAll(StockListRequest.builder().build()).forEach(items::add);

        // Then
        assertThat(items).hasSize(1);
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/stocks")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
