package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilityConnectionsClientTest {

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
    void get_returnsDeserializedConnection() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(okJson("""
                        {"id":"conn-1","version":1,"sourceFacilityRef":"fac-1",
                         "target":{"type":"CUSTOMER"},
                         "carrierKey":"DHL","carrierName":"DHL Express"}
                        """)));

        // When
        FacilityConnection conn = client.facilityConnections().get(new FacilityId("fac-1"), new ConnectionId("conn-1"));

        // Then
        assertThat(conn.id()).isEqualTo("conn-1");
        assertThat(conn.version()).isEqualTo(1);
        assertThat(conn.sourceFacilityRef()).isEqualTo("fac-1");
        assertThat(conn.carrierKey()).isEqualTo("DHL");
        assertThat(conn.carrierName()).isEqualTo("DHL Express");
        assertThat(conn.target()).isInstanceOf(ConnectionTarget.Customer.class);
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(okJson("{\"id\":\"conn-1\",\"version\":1,\"sourceFacilityRef\":\"fac-1\",\"target\":{\"type\":\"CUSTOMER\"}}")));

        // When
        client.facilityConnections().get(new FacilityId("fac-1"), new ConnectionId("conn-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_notFound_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections/missing"))
                .willReturn(aResponse().withStatus(404)));

        // Then
        assertThatThrownBy(() -> client.facilityConnections().get(new FacilityId("fac-1"), new ConnectionId("missing")))
                .isInstanceOf(NotFoundException.class);
    }

    // --- list ---

    @Test
    void list_returnsDeserializedPage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("""
                        {
                          "interFacilityConnections": [
                            {"id":"conn-1","version":1,"sourceFacilityRef":"fac-1","target":{"type":"CUSTOMER"}},
                            {"id":"conn-2","version":1,"sourceFacilityRef":"fac-1","target":{"type":"SUPPLIER","facilityRef":"fac-2"}}
                          ],
                          "total": 5
                        }
                        """)));

        // When
        Page<FacilityConnection> page = client.facilityConnections().list(new FacilityId("fac-1"),
                FacilityConnectionListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("conn-1");
        assertThat(page.items().get(1).id()).isEqualTo("conn-2");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("conn-2");
    }

    @Test
    void list_noMorePages_whenTotalEqualsItems() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("""
                        {
                          "interFacilityConnections": [
                            {"id":"conn-1","version":1,"sourceFacilityRef":"fac-1","target":{"type":"CUSTOMER"}}
                          ],
                          "total": 1
                        }
                        """)));

        // When
        Page<FacilityConnection> page = client.facilityConnections().list(new FacilityId("fac-1"),
                FacilityConnectionListRequest.builder().build());

        // Then
        assertThat(page.hasMore()).isFalse();
        assertThat(page.nextCursor()).isNull();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("{\"interFacilityConnections\":[]}")));

        // When
        client.facilityConnections().list(new FacilityId("fac-1"),
                FacilityConnectionListRequest.builder()
                        .size(10)
                        .startAfterId("cur-abc")
                        .targetFacilityRef("fac-2")
                        .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cur-abc"))
                .withQueryParam("targetFacilityRef", equalTo("fac-2")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"interFacilityConnections":[
                          {"id":"conn-1","version":1,"sourceFacilityRef":"fac-1","target":{"type":"CUSTOMER"}},
                          {"id":"conn-2","version":1,"sourceFacilityRef":"fac-1","target":{"type":"CUSTOMER"}}
                        ],"total":3}
                        """)));
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .withQueryParam("startAfterId", equalTo("conn-2"))
                .willReturn(okJson("""
                        {"interFacilityConnections":[
                          {"id":"conn-3","version":1,"sourceFacilityRef":"fac-1","target":{"type":"CUSTOMER"}}
                        ],"total":3}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        client.facilityConnections().listAll(new FacilityId("fac-1"),
                FacilityConnectionListRequest.builder().size(2).build()).forEach(c -> ids.add(c.id()));

        // Then
        assertThat(ids).containsExactly("conn-1", "conn-2", "conn-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/connections")));
    }

    // --- create ---

    @Test
    void create_returnsCreatedConnection() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("""
                        {"id":"conn-new","version":1,"sourceFacilityRef":"fac-1",
                         "target":{"type":"CUSTOMER"},"carrierKey":"DHL"}
                        """)));

        // When
        FacilityConnection conn = client.facilityConnections().create(new FacilityId("fac-1"),
                CreateFacilityConnectionRequest.builder()
                        .target(ConnectionTarget.Customer.of())
                        .carrierKey("DHL")
                        .build());

        // Then
        assertThat(conn.id()).isEqualTo("conn-new");
        assertThat(conn.carrierKey()).isEqualTo("DHL");
        assertThat(conn.target()).isInstanceOf(ConnectionTarget.Customer.class);
    }

    @Test
    void create_sendsCorrectBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .willReturn(okJson("{\"id\":\"conn-1\",\"version\":1,\"sourceFacilityRef\":\"fac-1\"," +
                        "\"target\":{\"type\":\"MANAGED_FACILITY\",\"facilityRef\":\"fac-2\"}}")));

        // When
        client.facilityConnections().create(new FacilityId("fac-1"),
                CreateFacilityConnectionRequest.builder()
                        .target(ConnectionTarget.ManagedFacility.of("fac-2"))
                        .carrierKey("UPS")
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities/fac-1/connections"))
                .withRequestBody(matchingJsonPath("$.type", equalTo("MANAGED_FACILITY")))
                .withRequestBody(matchingJsonPath("$.target.facilityRef", equalTo("fac-2")))
                .withRequestBody(matchingJsonPath("$.carrierKey", equalTo("UPS"))));
    }

    @Test
    void create_requiresTarget() {
        // Then
        assertThatNullPointerException().isThrownBy(() ->
                CreateFacilityConnectionRequest.builder()
                        .build()
        ).withMessageContaining("target");
    }

    // --- update ---

    @Test
    void update_sendsCorrectBody() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(okJson("""
                        {"id":"conn-1","version":2,"sourceFacilityRef":"fac-1",
                         "target":{"type":"CUSTOMER"},"carrierKey":"FedEx"}
                        """)));

        // When
        FacilityConnection conn = client.facilityConnections().update(new FacilityId("fac-1"), new ConnectionId("conn-1"),
                UpdateFacilityConnectionRequest.builder()
                        .version(1)
                        .target(ConnectionTarget.Customer.of())
                        .carrierKey("FedEx")
                        .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1")))
                .withRequestBody(matchingJsonPath("$.type", equalTo("CUSTOMER")))
                .withRequestBody(matchingJsonPath("$.carrierKey", equalTo("FedEx"))));
        assertThat(conn.version()).isEqualTo(2);
        assertThat(conn.target()).isInstanceOf(ConnectionTarget.Customer.class);
    }

    @Test
    void update_requiresVersion() {
        // Then
        assertThatNullPointerException().isThrownBy(() ->
                UpdateFacilityConnectionRequest.builder()
                        .target(ConnectionTarget.Customer.of())
                        .build()
        ).withMessageContaining("version");
    }

    @Test
    void update_requiresTarget() {
        // Then
        assertThatNullPointerException().isThrownBy(() ->
                UpdateFacilityConnectionRequest.builder()
                        .version(1)
                        .build()
        ).withMessageContaining("target");
    }

    // --- delete ---

    @Test
    void delete_sendsDeleteRequest() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/connections/conn-1"))
                .willReturn(aResponse().withStatus(204)));

        // When
        client.facilityConnections().delete(new FacilityId("fac-1"), new ConnectionId("conn-1"));

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
