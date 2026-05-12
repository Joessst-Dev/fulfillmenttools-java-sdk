package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilityConnectionsAsyncTest {

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
    void getAsync_returnsConnection() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(okJson("{\"id\":\"conn-1\",\"version\":1,\"sourceFacilityRef\":\"fac-1\",\"target\":{\"type\":\"CUSTOMER\"}}")));

        // When
        FacilityConnection conn = client.facilityConnections().getAsync("fac-1", "conn-1").get();

        // Then
        assertThat(conn.id()).isEqualTo("conn-1");
        assertThat(conn.sourceFacilityRef()).isEqualTo("fac-1");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("""
                        {"interFacilityConnections":[
                          {"id":"conn-1","version":1,"sourceFacilityRef":"fac-1","target":{}},
                          {"id":"conn-2","version":1,"sourceFacilityRef":"fac-1","target":{}}
                        ],"total":2}
                        """)));

        // When
        Page<FacilityConnection> page = client.facilityConnections()
                .listAsync("fac-1", FacilityConnectionListRequest.builder().size(10).build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void createAsync_returnsCreatedConnection() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("{\"id\":\"conn-new\",\"version\":1,\"sourceFacilityRef\":\"fac-1\",\"target\":{\"type\":\"SUPPLIER\"}}")));

        // When
        FacilityConnection conn = client.facilityConnections().createAsync("fac-1",
                CreateFacilityConnectionRequest.builder()
                        .type("SUPPLIER")
                        .target(Map.of("type", "SUPPLIER"))
                        .build()).get();

        // Then
        assertThat(conn.id()).isEqualTo("conn-new");
    }

    @Test
    void updateAsync_returnsUpdatedConnection() throws Exception {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(okJson("{\"id\":\"conn-1\",\"version\":2,\"sourceFacilityRef\":\"fac-1\",\"target\":{\"type\":\"CUSTOMER\"}}")));

        // When
        FacilityConnection conn = client.facilityConnections().updateAsync("fac-1", "conn-1",
                UpdateFacilityConnectionRequest.builder()
                        .version(1)
                        .type("CUSTOMER")
                        .target(Map.of("type", "CUSTOMER"))
                        .build()).get();

        // Then
        assertThat(conn.version()).isEqualTo(2);
    }

    @Test
    void deleteAsync_sendsDeleteRequest() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(aResponse().withStatus(204)));

        // When
        client.facilityConnections().deleteAsync("fac-1", "conn-1").get();

        // Then
        server.verify(deleteRequestedFor(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
