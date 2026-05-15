package de.joesst.dev.fulfillmenttools.stocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.stocks.StockUpsertResult;
import de.joesst.dev.fulfillmenttools.stocks.VersionlessStockUpdate;
import org.junit.jupiter.api.*;

import java.util.List;

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
        assertThat(page.items().get(0).tenantArticleId().value()).isEqualTo("art-1");
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

    // --- createAsync ---

    @Test
    void createAsync_returnsCreatedStockItem() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"id\":\"s-new\",\"version\":1,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":100}")));

        // When
        StockItem item = client.stocks().createAsync(CreateStockRequest.builder()
                .tenantArticleId(new TenantArticleId("art-1"))
                .facilityRef(new FacilityId("fac-1"))
                .value(100)
                .build()).get();

        // Then
        assertThat(item.id().value()).isEqualTo("s-new");
        assertThat(item.value()).isEqualTo(100);
    }

    // --- updateAsync ---

    @Test
    void updateAsync_returnsUpdatedStockItem() throws Exception {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/stocks/s-1"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":2,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":50}")));

        // When
        StockItem item = client.stocks().updateAsync(new StockId("s-1"), UpdateStockRequest.builder()
                .version(1)
                .value(50)
                .build()).get();

        // Then
        assertThat(item.id().value()).isEqualTo("s-1");
        assertThat(item.version()).isEqualTo(2);
        assertThat(item.value()).isEqualTo(50);
    }

    // --- upsertStocksAsync ---

    @Test
    void upsertStocksAsync_returnsResults() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("""
                        {"name":"UPDATE_VERSIONLESS","result":{"operationResults":[
                          {"stock":{"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":30},"status":"UPDATED"}
                        ]}}
                        """)));

        // When
        List<StockUpsertResult> results = client.stocks().upsertStocksAsync(List.of(
                VersionlessStockUpdate.builder()
                        .stockId(new StockId("s-1"))
                        .value(30)
                        .build())).get();

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("UPDATE_VERSIONLESS")))
                .withRequestBody(matchingJsonPath("$.stocks[0].operationType", equalTo("UPDATE"))));
        assertThat(results).hasSize(1);
        assertThat(results.get(0).status()).isEqualTo("UPDATED");
        assertThat(results.get(0).stock().id().value()).isEqualTo("s-1");
        assertThat(results.get(0).stock().value()).isEqualTo(30);
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
