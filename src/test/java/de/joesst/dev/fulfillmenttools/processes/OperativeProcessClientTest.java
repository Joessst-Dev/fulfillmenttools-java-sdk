package de.joesst.dev.fulfillmenttools.processes;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class OperativeProcessClientTest {

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
    void get_returnsDeserializedProcess() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes/proc-1"))
                .willReturn(okJson("""
                        {
                          "id": "proc-1",
                          "version": 3,
                          "status": "OPEN",
                          "domsStatus": "IN_PROGRESS",
                          "operativeStatus": "IN_PROGRESS",
                          "tenantOrderId": "order-abc",
                          "gdprCleanupDate": "2025-01-01T00:00:00Z",
                          "facilityRefs": ["fac-1"],
                          "serviceJobRefs": ["sj-1"],
                          "externalActionRefs": ["ea-1"],
                          "tags": [{"id": "tag-1"}]
                        }
                        """)));

        // When
        Process process = client.processes().get("proc-1");

        // Then
        assertThat(process.id()).isEqualTo("proc-1");
        assertThat(process.version()).isEqualTo(3);
        assertThat(process.status()).isEqualTo("OPEN");
        assertThat(process.tenantOrderId()).isEqualTo("order-abc");
        assertThat(process.facilityRefs()).containsExactly("fac-1");
        assertThat(process.serviceJobRefs()).containsExactly("sj-1");
        assertThat(process.externalActionRefs()).containsExactly("ea-1");
        assertThat(process.tags()).hasSize(1);
        assertThat(process.gdprCleanupDate()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes/proc-1"))
                .willReturn(okJson("{\"id\":\"proc-1\"}")));

        // When
        client.processes().get("proc-1");

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/processes/proc-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Process not found","description":"No process with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.processes().get("missing"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Process not found");
    }

    // --- list ---

    @Test
    void list_returnsPageFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes"))
                .willReturn(okJson("""
                        {
                          "processes": [{"id":"proc-1"},{"id":"proc-2"}],
                          "total": 2
                        }
                        """)));

        // When
        Page<Process> page = client.processes().list(ProcessListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("proc-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes"))
                .willReturn(okJson("{\"processes\":[], \"total\":0}")));

        // When
        client.processes().list(ProcessListRequest.builder()
                .size(10)
                .startAfterId("cursor-abc")
                .facilityRefs(List.of("fac-1", "fac-2"))
                .status(List.of("OPEN", "IN_PROGRESS"))
                .operativeStatus(List.of("IN_PROGRESS"))
                .tenantOrderId("order-123")
                .searchTerm("query")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/processes"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRefs", equalTo("fac-1"))
                .withQueryParam("facilityRefs", equalTo("fac-2"))
                .withQueryParam("status", equalTo("OPEN"))
                .withQueryParam("status", equalTo("IN_PROGRESS"))
                .withQueryParam("operativeStatus", equalTo("IN_PROGRESS"))
                .withQueryParam("tenantOrderId", equalTo("order-123"))
                .withQueryParam("searchTerm", equalTo("query")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes"))
                .willReturn(okJson("{\"processes\":[{\"id\":\"proc-1\"},{\"id\":\"proc-2\"}],\"total\":2}")));

        // When
        List<String> ids = new ArrayList<>();
        for (Process p : client.processes().listAll(ProcessListRequest.builder().build())) {
            ids.add(p.id());
        }

        // Then
        assertThat(ids).containsExactly("proc-1", "proc-2");
    }

    // --- search ---

    @Test
    void search_returnsMatchingProcesses() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/processes/search"))
                .willReturn(okJson("{\"processes\":[{\"id\":\"proc-1\",\"facilityRefs\":[\"fac-1\"]}],\"total\":1}")));

        // When
        Page<Process> page = client.processes().search(ProcessSearchRequest.builder()
                .facilityRefs(List.of("fac-1"))
                .status(List.of("OPEN"))
                .build());

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).facilityRefs()).containsExactly("fac-1");
    }

    @Test
    void search_sendsFacilityRefsAndStatus() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/processes/search"))
                .willReturn(okJson("{\"processes\":[], \"total\":0}")));

        // When
        client.processes().search(ProcessSearchRequest.builder()
                .facilityRefs(List.of("fac-1"))
                .status(List.of("OPEN"))
                .size(5)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/processes/search"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.facilityRefs[0]", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.status[0]", equalTo("OPEN")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("5"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
