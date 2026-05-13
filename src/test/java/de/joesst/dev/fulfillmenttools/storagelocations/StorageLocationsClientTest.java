package de.joesst.dev.fulfillmenttools.storagelocations;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class StorageLocationsClientTest {

    private static final String FAC = "fac-1";

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
    void get_returnsDeserializedStorageLocation() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .willReturn(okJson("""
                        {
                          "id": "sl-1",
                          "version": 1,
                          "name": "Shelf A1",
                          "type": "SHELF",
                          "facilityRef": "fac-1",
                          "scannableCodes": ["BC001"],
                          "created": "2024-01-01T00:00:00Z"
                        }
                        """)));

        // When
        StorageLocation loc = client.storageLocations().get(new FacilityId(FAC), new StorageLocationId("sl-1"));

        // Then
        assertThat(loc.id()).isEqualTo("sl-1");
        assertThat(loc.name()).isEqualTo("Shelf A1");
        assertThat(loc.type()).isEqualTo("SHELF");
        assertThat(loc.facilityRef()).isEqualTo("fac-1");
        assertThat(loc.version()).isEqualTo(1);
        assertThat(loc.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .willReturn(okJson("{\"id\":\"sl-1\"}")));

        // When
        client.storageLocations().get(new FacilityId(FAC), new StorageLocationId("sl-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Storage location not found","description":"No storage location with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.storageLocations().get(new FacilityId(FAC), new StorageLocationId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Storage location not found");
    }

    // --- list ---

    @Test
    void list_returnsPageDeserializedFromRawArray() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(okJson("""
                        [
                          {"id":"sl-1","name":"Shelf A1","type":"SHELF"},
                          {"id":"sl-2","name":"Shelf B2","type":"SHELF"}
                        ]
                        """)));

        // When
        Page<StorageLocation> page = client.storageLocations().list(new FacilityId(FAC),
                StorageLocationListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("sl-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(okJson("[]")));

        // When
        client.storageLocations().list(new FacilityId(FAC), StorageLocationListRequest.builder()
                .size(10)
                .startAfterId("cursor-abc")
                .scannableCode("BC001")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("scannableCode", equalTo("BC001")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(okJson("[{\"id\":\"sl-1\"},{\"id\":\"sl-2\"},{\"id\":\"sl-3\"}]")));

        // When
        List<String> ids = new ArrayList<>();
        for (StorageLocation s : client.storageLocations().listAll(new FacilityId(FAC), StorageLocationListRequest.builder().build())) {
            ids.add(s.id());
        }

        // Then
        assertThat(ids).containsExactly("sl-1", "sl-2", "sl-3");
    }

    // --- create ---

    @Test
    void create_returnsCreatedStorageLocation() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"sl-new\",\"name\":\"Shelf C3\",\"type\":\"SHELF\",\"facilityRef\":\"fac-1\"}")));

        // When
        StorageLocation loc = client.storageLocations().create(new FacilityId(FAC), CreateStorageLocationRequest.builder()
                .name("Shelf C3")
                .type("SHELF")
                .scannableCodes(List.of("BC999"))
                .runningSequences(List.of())
                .build());

        // Then
        assertThat(loc.id()).isEqualTo("sl-new");
        assertThat(loc.name()).isEqualTo("Shelf C3");
    }

    @Test
    void create_sendsNameTypeScannableCodesAndRunningSequences() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"sl-new\"}")));

        // When
        client.storageLocations().create(new FacilityId(FAC), CreateStorageLocationRequest.builder()
                .name("Shelf C3")
                .type("SHELF")
                .scannableCodes(List.of("BC999"))
                .runningSequences(List.of())
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("Shelf C3")))
                .withRequestBody(matchingJsonPath("$.type", equalTo("SHELF")))
                .withRequestBody(matchingJsonPath("$.scannableCodes[0]", equalTo("BC999"))));
    }

    @Test
    void create_requiresName() {
        assertThatThrownBy(() -> CreateStorageLocationRequest.builder()
                .type("SHELF").scannableCodes(List.of()).runningSequences(List.of()).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("name");
    }

    @Test
    void create_requiresType() {
        assertThatThrownBy(() -> CreateStorageLocationRequest.builder()
                .name("Shelf A").scannableCodes(List.of()).runningSequences(List.of()).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("type");
    }

    // --- update ---

    @Test
    void update_sendsPatchActionsFormat() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .willReturn(okJson("{\"id\":\"sl-1\",\"name\":\"Updated Shelf\",\"type\":\"SHELF\"}")));

        // When
        StorageLocation loc = client.storageLocations().update(new FacilityId(FAC), new StorageLocationId("sl-1"), UpdateStorageLocationRequest.builder()
                .version(2)
                .name("Updated Shelf")
                .build());

        // Then
        assertThat(loc.name()).isEqualTo("Updated Shelf");
        server.verify(patchRequestedFor(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyStorageLocation")))
                .withRequestBody(matchingJsonPath("$.actions[0].name", equalTo("Updated Shelf"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateStorageLocationRequest.builder().name("X").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- delete ---

    @Test
    void delete_sends200() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatCode(() -> client.storageLocations().delete(new FacilityId(FAC), new StorageLocationId("sl-1")))
                .doesNotThrowAnyException();
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
