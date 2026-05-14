package de.joesst.dev.fulfillmenttools.stocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    void list_returnsDeserializedStockItems() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {
                          "stocks": [
                            {"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":10,"version":1,"available":8.0,"reserved":2.0,"traits":[],"serializedProperties":"{}"},
                            {"id":"s-2","facilityRef":"fac-1","tenantArticleId":"art-2","value":5,"version":1,"available":5.0,"reserved":0.0,"traits":[],"serializedProperties":"{}"}
                          ],
                          "pageInfo":{"endCursor":"cursor-page-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cursor-page-1"}
                        }
                        """)));

        // When
        Page<StockItem> page = client.stocks().list(StockListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("s-1");
        assertThat(page.items().get(0).facilityRef().value()).isEqualTo("fac-1");
        assertThat(page.items().get(0).tenantArticleId().value()).isEqualTo("art-1");
        assertThat(page.items().get(0).value()).isEqualTo(10);
        assertThat(page.items().get(0).available()).isEqualTo(8.0);
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[],\"pageInfo\":{\"endCursor\":null,\"hasNextPage\":false,\"hasPreviousPage\":false,\"startCursor\":null}}")));

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
                .facilityRef(new FacilityId("fac-1"))
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stocks"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRef", equalTo("fac-1")));
    }

    @Test
    void list_sendsTenantFacilityIdAndArticleIdParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().list(StockListRequest.builder()
                .tenantFacilityId(new TenantFacilityId("tenant-fac-1"))
                .tenantArticleId(List.of("art-1", "art-2"))
                .locationRef(List.of("loc-A"))
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stocks"))
                .withQueryParam("tenantFacilityId", equalTo("tenant-fac-1"))
                .withQueryParam("tenantArticleId", equalTo("art-1"))
                .withQueryParam("tenantArticleId", equalTo("art-2"))
                .withQueryParam("locationRef", equalTo("loc-A")));
    }

    @Test
    void list_withNoCursor_hasMoreIsFalse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[{\"id\":\"s-1\",\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":1}]}")));

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
                          {"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":1},
                          {"id":"s-2","facilityRef":"fac-1","tenantArticleId":"art-2","value":2}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"stocks":[
                          {"id":"s-3","facilityRef":"fac-1","tenantArticleId":"art-3","value":3}
                        ]}
                        """)));

        // When
        List<String> articleIds = new ArrayList<>();
        for (StockItem s : client.stocks().listAll(StockListRequest.builder().size(2).build())) {
            articleIds.add(s.tenantArticleId().value());
        }

        // Then
        assertThat(articleIds).containsExactly("art-1", "art-2", "art-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/stocks")));
    }

    @Test
    void listAll_doesNotFetchExtraPageAfterLastCursor() {
        // Given — single page, no pageInfo
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"stocks\":[{\"id\":\"s-1\",\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":1}]}")));

        // When
        List<StockItem> items = new ArrayList<>();
        client.stocks().listAll(StockListRequest.builder().build()).forEach(items::add);

        // Then
        assertThat(items).hasSize(1);
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/stocks")));
    }

    // --- typed field deserialization ---

    @Test
    void list_deserializesTypedPropertiesAsMapOfStrings() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"stocks":[{
                          "id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":3,
                          "properties":{"expiryDate":"2025-12-31","batchId":"B42"}
                        }]}
                        """)));

        // When
        StockItem item = client.stocks().list(StockListRequest.builder().build()).items().get(0);

        // Then
        assertThat(item.properties())
                .isInstanceOf(Map.class)
                .containsEntry("expiryDate", "2025-12-31")
                .containsEntry("batchId", "B42");
    }

    @Test
    void list_deserializesTraitConfigAsTypedList() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"stocks":[{
                          "id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":3,
                          "traitConfig":[
                            {"trait":"PICKABLE","enabled":true},
                            {"trait":"ACCESSIBLE","enabled":false}
                          ]
                        }]}
                        """)));

        // When
        StockItem item = client.stocks().list(StockListRequest.builder().build()).items().get(0);

        // Then
        assertThat(item.traitConfig()).hasSize(2);
        assertThat(item.traitConfig().get(0).trait()).isEqualTo("PICKABLE");
        assertThat(item.traitConfig().get(0).enabled()).isTrue();
        assertThat(item.traitConfig().get(1).trait()).isEqualTo("ACCESSIBLE");
        assertThat(item.traitConfig().get(1).enabled()).isFalse();
    }

    @Test
    void list_deserializesFacilityAsStockFacilityReferences() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"stocks":[{
                          "id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":3,
                          "facility":{"facilityRef":"fac-1","tenantFacilityId":"tenant-fac-1"}
                        }]}
                        """)));

        // When
        StockItem item = client.stocks().list(StockListRequest.builder().build()).items().get(0);

        // Then
        assertThat(item.facility()).isNotNull();
        assertThat(item.facility().facilityRef().value()).isEqualTo("fac-1");
        assertThat(item.facility().tenantFacilityId()).isEqualTo(new TenantFacilityId("tenant-fac-1"));
    }

    @Test
    void list_handlesNullFacilityAndPropertiesAndTraitConfig() {
        // Given — stock item with none of the new typed fields present
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"stocks":[{
                          "id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":3
                        }]}
                        """)));

        // When
        StockItem item = client.stocks().list(StockListRequest.builder().build()).items().get(0);

        // Then
        assertThat(item.facility()).isNull();
        assertThat(item.properties()).isNull();
        assertThat(item.traitConfig()).isNull();
    }

    @Test
    void list_deserializesTenantStockIdAndLocationRefAsTypedIds() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"stocks":[{
                          "id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":3,
                          "tenantStockId":"tenant-stock-42",
                          "locationRef":"loc-A1"
                        }]}
                        """)));

        // When
        StockItem item = client.stocks().list(StockListRequest.builder().build()).items().get(0);

        // Then
        assertThat(item.tenantStockId().value()).isEqualTo("tenant-stock-42");
        assertThat(item.locationRef().value()).isEqualTo("loc-A1");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
