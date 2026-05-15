package de.joesst.dev.fulfillmenttools.stocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
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
                .tenantArticleId(List.of(new TenantArticleId("art-1"), new TenantArticleId("art-2")))
                .locationRef(List.of(new StorageLocationId("loc-A")))
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

    // --- create ---

    @Test
    void create_returnsCreatedStockItem() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("""
                        {"id":"s-new","version":1,"facilityRef":"fac-1","tenantArticleId":"art-1","value":100,"available":100.0}
                        """)));

        // When
        StockItem item = client.stocks().create(CreateStockRequest.builder()
                .tenantArticleId(new TenantArticleId("art-1"))
                .facilityRef(new FacilityId("fac-1"))
                .value(100)
                .build());

        // Then
        assertThat(item.id().value()).isEqualTo("s-new");
        assertThat(item.tenantArticleId().value()).isEqualTo("art-1");
        assertThat(item.facilityRef().value()).isEqualTo("fac-1");
        assertThat(item.value()).isEqualTo(100);
        assertThat(item.available()).isEqualTo(100.0);
    }

    @Test
    void create_withTenantFacilityId_sendsTenantFacilityIdInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":1,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":10}")));

        // When
        client.stocks().create(CreateStockRequest.builder()
                .tenantArticleId(new TenantArticleId("art-1"))
                .tenantFacilityId(new TenantFacilityId("tenant-fac-1"))
                .value(10)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks"))
                .withRequestBody(matchingJsonPath("$.tenantFacilityId", equalTo("tenant-fac-1")))
                .withRequestBody(not(matchingJsonPath("$.facilityRef"))));
    }

    @Test
    void create_sendsBodyFields() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":1,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":50}")));

        // When
        client.stocks().create(CreateStockRequest.builder()
                .tenantArticleId(new TenantArticleId("art-1"))
                .facilityRef(new FacilityId("fac-1"))
                .value(50)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks"))
                .withRequestBody(matchingJsonPath("$.tenantArticleId", equalTo("art-1")))
                .withRequestBody(matchingJsonPath("$.value", equalTo("50")))
                .withRequestBody(matchingJsonPath("$.facilityRef", equalTo("fac-1"))));
    }

    @Test
    void create_sendsOptionalFields() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":1,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":10,\"locationRef\":\"loc-A\",\"conditions\":[\"DEFECTIVE\"]}")));

        // When
        StockItem item = client.stocks().create(CreateStockRequest.builder()
                .tenantArticleId(new TenantArticleId("art-1"))
                .facilityRef(new FacilityId("fac-1"))
                .value(10)
                .locationRef(new StorageLocationId("loc-A"))
                .conditions(List.of("DEFECTIVE"))
                .build());

        // Then: request body contains optional fields
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks"))
                .withRequestBody(matchingJsonPath("$.locationRef", equalTo("loc-A")))
                .withRequestBody(matchingJsonPath("$.conditions[0]", equalTo("DEFECTIVE"))));
        assertThat(item.locationRef().value()).isEqualTo("loc-A");
        assertThat(item.conditions()).containsExactly("DEFECTIVE");
    }

    // --- update ---

    @Test
    void update_returnsUpdatedStockItem() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/stocks/s-1"))
                .willReturn(okJson("""
                        {"id":"s-1","version":2,"facilityRef":"fac-1","tenantArticleId":"art-1","value":75,"available":75.0}
                        """)));

        // When
        StockItem item = client.stocks().update(new StockId("s-1"), UpdateStockRequest.builder()
                .version(1)
                .value(75)
                .build());

        // Then
        assertThat(item.id().value()).isEqualTo("s-1");
        assertThat(item.version()).isEqualTo(2);
        assertThat(item.value()).isEqualTo(75);
    }

    @Test
    void update_sendsVersionAndValue() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/stocks/s-1"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":3,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":20}")));

        // When
        client.stocks().update(new StockId("s-1"), UpdateStockRequest.builder()
                .version(2)
                .value(20)
                .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/stocks/s-1"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.value", equalTo("20"))));
    }

    @Test
    void update_sendsOptionalFields() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/stocks/s-1"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":2,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":10,\"conditions\":[\"DEFECTIVE\"]}")));

        // When
        StockItem item = client.stocks().update(new StockId("s-1"), UpdateStockRequest.builder()
                .version(1)
                .value(10)
                .conditions(List.of("DEFECTIVE"))
                .locationRef(new StorageLocationId("loc-B"))
                .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/stocks/s-1"))
                .withRequestBody(matchingJsonPath("$.conditions[0]", equalTo("DEFECTIVE")))
                .withRequestBody(matchingJsonPath("$.locationRef", equalTo("loc-B"))));
        assertThat(item.value()).isEqualTo(10);
        assertThat(item.conditions()).containsExactly("DEFECTIVE");
    }

    @Test
    void create_omitsUnsetOptionalFieldsFromBody() {
        // Given — only required fields set; optional fields must not appear in the body
        server.stubFor(post(urlPathEqualTo("/api/stocks"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":1,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":5}")));

        // When
        client.stocks().create(CreateStockRequest.builder()
                .tenantArticleId(new TenantArticleId("art-1"))
                .facilityRef(new FacilityId("fac-1"))
                .value(5)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks"))
                .withRequestBody(not(matchingJsonPath("$.locationRef")))
                .withRequestBody(not(matchingJsonPath("$.tenantStockId")))
                .withRequestBody(not(matchingJsonPath("$.conditions")))
                .withRequestBody(not(matchingJsonPath("$.availableUntil")))
                .withRequestBody(not(matchingJsonPath("$.receiptDate")))
                .withRequestBody(not(matchingJsonPath("$.traitConfig")))
                .withRequestBody(not(matchingJsonPath("$.properties"))));
    }

    @Test
    void update_omitsUnsetOptionalFieldsFromBody() {
        // Given — only required fields set; optional fields must not appear in the body
        server.stubFor(put(urlPathEqualTo("/api/stocks/s-1"))
                .willReturn(okJson("{\"id\":\"s-1\",\"version\":2,\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"value\":30}")));

        // When
        client.stocks().update(new StockId("s-1"), UpdateStockRequest.builder()
                .version(1)
                .value(30)
                .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/stocks/s-1"))
                .withRequestBody(not(matchingJsonPath("$.locationRef")))
                .withRequestBody(not(matchingJsonPath("$.tenantStockId")))
                .withRequestBody(not(matchingJsonPath("$.conditions")))
                .withRequestBody(not(matchingJsonPath("$.customAttributes"))));
    }

    // --- upsertStocks ---

    @Test
    void upsertStocks_sendsUpdateVersionlessAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("""
                        {"name":"UPDATE_VERSIONLESS","result":{"operationResults":[
                          {"stock":{"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":50},"status":"UPDATED"}
                        ]}}
                        """)));

        // When
        client.stocks().upsertStocks(List.of(
                VersionlessStockUpdate.builder()
                        .stockId(new StockId("s-1"))
                        .value(50)
                        .build()));

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("UPDATE_VERSIONLESS")))
                .withRequestBody(matchingJsonPath("$.stocks[0].operationType", equalTo("UPDATE")))
                .withRequestBody(matchingJsonPath("$.stocks[0].id", equalTo("s-1")))
                .withRequestBody(matchingJsonPath("$.stocks[0].value", equalTo("50"))));
    }

    @Test
    void upsertStocks_returnsResults() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("""
                        {"name":"UPDATE_VERSIONLESS","result":{"operationResults":[
                          {"stock":{"id":"s-new","facilityRef":"fac-1","tenantArticleId":"art-1","value":100},"status":"CREATED"},
                          {"stock":{"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-2","value":50},"status":"UPDATED"}
                        ]}}
                        """)));

        // When
        List<StockUpsertResult> results = client.stocks().upsertStocks(List.of(
                VersionlessStockCreate.builder()
                        .tenantArticleId(new TenantArticleId("art-1"))
                        .facilityRef(new FacilityId("fac-1"))
                        .value(100)
                        .build(),
                VersionlessStockUpdate.builder()
                        .stockId(new StockId("s-1"))
                        .value(50)
                        .build()));

        // Then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).status()).isEqualTo("CREATED");
        assertThat(results.get(0).stock().id().value()).isEqualTo("s-new");
        assertThat(results.get(1).status()).isEqualTo("UPDATED");
        assertThat(results.get(1).stock().value()).isEqualTo(50);
    }

    @Test
    void upsertStocks_sendsCreateOperationFields() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("""
                        {"name":"UPDATE_VERSIONLESS","result":{"operationResults":[
                          {"stock":{"id":"s-new","facilityRef":"fac-1","tenantArticleId":"art-1","value":10},"status":"CREATED"}
                        ]}}
                        """)));

        // When
        client.stocks().upsertStocks(List.of(
                VersionlessStockCreate.builder()
                        .tenantArticleId(new TenantArticleId("art-1"))
                        .facilityRef(new FacilityId("fac-1"))
                        .value(10)
                        .build()));

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/actions"))
                .withRequestBody(matchingJsonPath("$.stocks[0].operationType", equalTo("CREATE")))
                .withRequestBody(matchingJsonPath("$.stocks[0].tenantArticleId", equalTo("art-1")))
                .withRequestBody(matchingJsonPath("$.stocks[0].facilityRef", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.stocks[0].value", equalTo("10"))));
    }

    @Test
    void upsertStocks_createWithTenantFacilityId_sendsTenantFacilityIdInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("""
                        {"name":"UPDATE_VERSIONLESS","result":{"operationResults":[
                          {"stock":{"id":"s-new","facilityRef":"fac-1","tenantArticleId":"art-1","value":5},"status":"CREATED"}
                        ]}}
                        """)));

        // When
        client.stocks().upsertStocks(List.of(
                VersionlessStockCreate.builder()
                        .tenantArticleId(new TenantArticleId("art-1"))
                        .tenantFacilityId(new TenantFacilityId("tenant-fac-1"))
                        .value(5)
                        .build()));

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/actions"))
                .withRequestBody(matchingJsonPath("$.stocks[0].tenantFacilityId", equalTo("tenant-fac-1")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].facilityRef"))));
    }

    @Test
    void upsertStocks_returnsEmptyListWhenResultIsEmpty() {
        // Given — server returns a response with no operationResults
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("{\"name\":\"UPDATE_VERSIONLESS\",\"result\":{}}")));

        // When
        List<StockUpsertResult> results = client.stocks().upsertStocks(List.of(
                VersionlessStockUpdate.builder()
                        .stockId(new StockId("s-1"))
                        .value(0)
                        .build()));

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    void upsertStocks_omitsUnsetOptionalFieldsFromCreateOperation() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/actions"))
                .willReturn(okJson("""
                        {"name":"UPDATE_VERSIONLESS","result":{"operationResults":[
                          {"stock":{"id":"s-new","facilityRef":"fac-1","tenantArticleId":"art-1","value":5},"status":"CREATED"}
                        ]}}
                        """)));

        // When
        client.stocks().upsertStocks(List.of(
                VersionlessStockCreate.builder()
                        .tenantArticleId(new TenantArticleId("art-1"))
                        .facilityRef(new FacilityId("fac-1"))
                        .value(5)
                        .build()));

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/actions"))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].locationRef")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].tenantStockId")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].conditions")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].availableUntil")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].receiptDate")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].traitConfig")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].properties")))
                .withRequestBody(not(matchingJsonPath("$.stocks[0].customAttributes"))));
    }

    // --- search ---

    @Test
    void search_sendsQueryBodyWithFilters() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("""
                        {"stocks":[
                          {"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":10}
                        ],"pageInfo":{"endCursor":"c2","hasNextPage":false,"hasPreviousPage":false,"startCursor":"c1"}}
                        """)));

        // When
        client.stocks().search(StockSearchRequest.builder()
                .query(StockSearchQuery.builder()
                        .tenantArticleIdIn(new TenantArticleId("art-1"), new TenantArticleId("art-2"))
                        .facilityRefIn(new FacilityId("fac-1"))
                        .build())
                .size(25)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(matchingJsonPath("$.query.tenantArticleId.in[0]", equalTo("art-1")))
                .withRequestBody(matchingJsonPath("$.query.tenantArticleId.in[1]", equalTo("art-2")))
                .withRequestBody(matchingJsonPath("$.query.facilityRef.in[0]", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("25"))));
    }

    @Test
    void search_returnsDeserializedPage() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("""
                        {"stocks":[
                          {"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":10},
                          {"id":"s-2","facilityRef":"fac-1","tenantArticleId":"art-2","value":20}
                        ],"pageInfo":{"endCursor":"cursor-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cursor-1"}}
                        """)));

        // When
        Page<StockItem> page = client.stocks().search(StockSearchRequest.builder().build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("s-1");
        assertThat(page.items().get(1).value()).isEqualTo(20);
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-2");
    }

    @Test
    void search_omitsNullQueryFromBody() {
        // Given — no query set at all
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().search(StockSearchRequest.builder().size(10).build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(not(matchingJsonPath("$.query")))
                .withRequestBody(not(matchingJsonPath("$.after"))));
    }

    @Test
    void search_omitsUnsetFieldsFromQuery() {
        // Given — query with only one filter set
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().search(StockSearchRequest.builder()
                .query(StockSearchQuery.builder()
                        .tenantArticleIdIn(new TenantArticleId("art-1"))
                        .build())
                .build());

        // Then — unset filters must not appear
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(not(matchingJsonPath("$.query.id")))
                .withRequestBody(not(matchingJsonPath("$.query.tenantStockId")))
                .withRequestBody(not(matchingJsonPath("$.query.facilityRef")))
                .withRequestBody(not(matchingJsonPath("$.query.tenantFacilityId")))
                .withRequestBody(not(matchingJsonPath("$.query.locationRef")))
                .withRequestBody(not(matchingJsonPath("$.query.value")))
                .withRequestBody(not(matchingJsonPath("$.query.conditions"))));
    }

    @Test
    void search_sendsTenantFacilityIdAndLocationRef() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().search(StockSearchRequest.builder()
                .query(StockSearchQuery.builder()
                        .tenantFacilityIdIn(new TenantFacilityId("tenant-fac-1"))
                        .locationRefIn(new StorageLocationId("loc-A"))
                        .build())
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(matchingJsonPath("$.query.tenantFacilityId.in[0]", equalTo("tenant-fac-1")))
                .withRequestBody(matchingJsonPath("$.query.locationRef.in[0]", equalTo("loc-A"))));
    }

    @Test
    void search_sendsValueFilter() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().search(StockSearchRequest.builder()
                .query(StockSearchQuery.builder().valueGte(5).build())
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(matchingJsonPath("$.query.value.gte", equalTo("5"))));
    }

    @Test
    void search_sendsLogicalOrQuery() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().search(StockSearchRequest.builder()
                .query(StockSearchQuery.builder()
                        .or(
                                StockSearchQuery.builder().facilityRefEq(new FacilityId("fac-1")).build(),
                                StockSearchQuery.builder().tenantFacilityIdEq(new TenantFacilityId("tf-1")).build()
                        )
                        .build())
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(matchingJsonPath("$.query.or[0].facilityRef.eq", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.query.or[1].tenantFacilityId.eq", equalTo("tf-1"))));
    }

    @Test
    void search_sendsAfterCursorForPagination() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .willReturn(okJson("{\"stocks\":[]}")));

        // When
        client.stocks().search(StockSearchRequest.builder().after("cursor-abc").build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(matchingJsonPath("$.after", equalTo("cursor-abc"))));
    }

    @Test
    void searchAll_traversesMultiplePages() {
        // Given — page 1 returns a cursor; page 2 returns no cursor
        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(not(matchingJsonPath("$.after")))
                .willReturn(okJson("""
                        {"stocks":[
                          {"id":"s-1","facilityRef":"fac-1","tenantArticleId":"art-1","value":1}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));

        server.stubFor(post(urlPathEqualTo("/api/stocks/search"))
                .withRequestBody(matchingJsonPath("$.after", equalTo("cur-2")))
                .willReturn(okJson("""
                        {"stocks":[
                          {"id":"s-2","facilityRef":"fac-1","tenantArticleId":"art-2","value":2}
                        ]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (StockItem s : client.stocks().searchAll(StockSearchRequest.builder().build())) {
            ids.add(s.id().value());
        }

        // Then
        assertThat(ids).containsExactly("s-1", "s-2");
        server.verify(2, postRequestedFor(urlPathEqualTo("/api/stocks/search")));
    }

    @Test
    void upsertStocks_throwsOnNullOperations() {
        // When / Then
        assertThatThrownBy(() -> client.stocks().upsertStocks(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void upsertStocks_throwsOnEmptyOperations() {
        // When / Then
        assertThatThrownBy(() -> client.stocks().upsertStocks(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
