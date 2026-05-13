package de.joesst.dev.fulfillmenttools.stocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class StocksAsyncTest {

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
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"stocks":[
                          {"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":5}
                        ],"pageInfo":{"endCursor":"c2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"c1"}}
                        """)));

        // When
        Page<StockItem> page = client.stocks()
                .listAsync(StockListRequest.builder().facilityRef(new FacilityId("fac-1")).build()).get();

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).tenantArticleId()).isEqualTo("art-1");
        assertThat(page.hasMore()).isTrue();
    }

    @Test
    void listAsync_sendsQueryParams() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().listAsync(
                StockListRequest.builder().size(5).facilityRef(new FacilityId("fac-1")).build()).get();

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stocks"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("facilityRef", equalTo("fac-1")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
