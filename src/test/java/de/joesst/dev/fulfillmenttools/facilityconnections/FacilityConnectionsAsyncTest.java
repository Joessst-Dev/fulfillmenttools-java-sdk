package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

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
        FacilityConnection conn = client.facilityConnections().getAsync(new FacilityId("fac-1"), new ConnectionId("conn-1")).get();

        // Then
        assertThat(conn.id()).isEqualTo("conn-1");
        assertThat(conn.sourceFacilityRef()).isEqualTo("fac-1");
        assertThat(conn.target()).isInstanceOf(ConnectionTarget.Customer.class);
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("""
                        {"interFacilityConnections":[
                          {"id":"conn-1","version":1,"sourceFacilityRef":"fac-1","target":{"type":"CUSTOMER"}},
                          {"id":"conn-2","version":1,"sourceFacilityRef":"fac-1","target":{"type":"MANAGED_FACILITY","facilityRef":"fac-2"}}
                        ],"total":2}
                        """)));

        // When
        Page<FacilityConnection> page = client.facilityConnections()
                .listAsync(new FacilityId("fac-1"), FacilityConnectionListRequest.builder().size(10).build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.hasMore()).isFalse();
        assertThat(page.items().get(0).target()).isInstanceOf(ConnectionTarget.Customer.class);
        assertThat(page.items().get(1).target()).isInstanceOf(ConnectionTarget.ManagedFacility.class);
    }

    @Test
    void createAsync_returnsCreatedConnection() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("{\"id\":\"conn-new\",\"version\":1,\"sourceFacilityRef\":\"fac-1\",\"target\":{\"type\":\"SUPPLIER\",\"facilityRef\":\"fac-3\"}}")));

        // When
        FacilityConnection conn = client.facilityConnections().createAsync(new FacilityId("fac-1"),
                CreateFacilityConnectionRequest.builder()
                        .target(ConnectionTarget.Supplier.of("fac-3"))
                        .build()).get();

        // Then
        assertThat(conn.id()).isEqualTo("conn-new");
        assertThat(conn.target()).isInstanceOf(ConnectionTarget.Supplier.class);
        assertThat(((ConnectionTarget.Supplier) conn.target()).facilityRef()).isEqualTo("fac-3");
    }

    @Test
    void updateAsync_returnsUpdatedConnection() throws Exception {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(okJson("{\"id\":\"conn-1\",\"version\":2,\"sourceFacilityRef\":\"fac-1\",\"target\":{\"type\":\"CUSTOMER\"}}")));

        // When
        FacilityConnection conn = client.facilityConnections().updateAsync(new FacilityId("fac-1"), new ConnectionId("conn-1"),
                UpdateFacilityConnectionRequest.builder()
                        .version(1)
                        .target(ConnectionTarget.Customer.of())
                        .build()).get();

        // Then
        assertThat(conn.version()).isEqualTo(2);
        assertThat(conn.target()).isInstanceOf(ConnectionTarget.Customer.class);
    }

    @Test
    void deleteAsync_sendsDeleteRequest() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(aResponse().withStatus(204)));

        // When
        client.facilityConnections().deleteAsync(new FacilityId("fac-1"), new ConnectionId("conn-1")).get();

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
