package de.joesst.dev.fulfillmenttools.storagelocations;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class StorageLocationsAsyncTest {

    private static final String FACILITY_ID = "fac-1";

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
    void getAsync_returnsStorageLocation() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .willReturn(okJson("{\"id\":\"sl-1\",\"name\":\"Shelf A1\",\"type\":\"SHELF\",\"facilityRef\":\"fac-1\"}")));

        // When
        StorageLocation location = client.storageLocations().getAsync(FACILITY_ID, "sl-1").get();

        // Then
        assertThat(location.id()).isEqualTo("sl-1");
        assertThat(location.name()).isEqualTo("Shelf A1");
        assertThat(location.facilityRef()).isEqualTo("fac-1");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(okJson("{\"storageLocations\":[{\"id\":\"sl-1\",\"name\":\"Shelf A1\"},{\"id\":\"sl-2\",\"name\":\"Shelf B2\"}]}")));

        // When
        var page = client.storageLocations()
                .listAsync(FACILITY_ID, StorageLocationListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedStorageLocation() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/storagelocations"))
                .willReturn(okJson("{\"id\":\"sl-new\",\"name\":\"New Shelf\",\"type\":\"SHELF\",\"facilityRef\":\"fac-1\"}")));

        // When
        StorageLocation location = client.storageLocations()
                .createAsync(FACILITY_ID, CreateStorageLocationRequest.builder().name("New Shelf").type("SHELF").build()).get();

        // Then
        assertThat(location.id()).isEqualTo("sl-new");
        assertThat(location.name()).isEqualTo("New Shelf");
    }

    @Test
    void deleteAsync_completesWithoutException() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/storagelocations/sl-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.storageLocations().deleteAsync(FACILITY_ID, "sl-1").get())
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
