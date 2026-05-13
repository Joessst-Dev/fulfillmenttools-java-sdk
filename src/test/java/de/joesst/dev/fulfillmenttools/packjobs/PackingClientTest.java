package de.joesst.dev.fulfillmenttools.packjobs;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class PackingClientTest {

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
    void get_returnsDeserializedPackJob() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs/pj-1"))
                .willReturn(okJson("""
                        {
                          "id": "pj-1",
                          "facilityRef": "fac-1",
                          "status": "OPEN",
                          "shortId": "PK12",
                          "created": "2024-03-01T10:00:00Z",
                          "lastModified": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        PackJob job = client.packing().get(new PackJobId("pj-1"));

        // Then
        assertThat(job.id().value()).isEqualTo("pj-1");
        assertThat(job.facilityRef().value()).isEqualTo("fac-1");
        assertThat(job.status()).isEqualTo("OPEN");
        assertThat(job.shortId()).isEqualTo("PK12");
        assertThat(job.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\"}")));

        // When
        client.packing().get(new PackJobId("pj-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/packjobs/pj-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Pack job not found","description":"No pack job with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.packing().get(new PackJobId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Pack job not found");
    }

    // --- list ---

    @Test
    void list_returnsPageWithItemsAndCursor() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs"))
                .willReturn(okJson("""
                        {
                          "packJobs": [
                            {"id":"pj-1","status":"OPEN"},
                            {"id":"pj-2","status":"IN_PROGRESS"}
                          ],
                          "nextCursor": "cursor-page-2"
                        }
                        """)));

        // When
        Page<PackJob> page = client.packing().list(PackJobListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("pj-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsAllQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs"))
                .willReturn(okJson("{\"packJobs\":[]}")));

        // When
        client.packing().list(PackJobListRequest.builder()
                .size(5)
                .startAfterId("cursor-abc")
                .facilityRef(new FacilityId("fac-1"))
                .status(List.of("OPEN", "IN_PROGRESS"))
                .orderRef(new OrderId("ord-1"))
                .tenantOrderId(new TenantOrderId("ext-001"))
                .orderBy("TARGET_TIME_ASC")
                .shortId("PK99")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/packjobs"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRef", equalTo("fac-1"))
                .withQueryParam("status", equalTo("OPEN"))
                .withQueryParam("status", equalTo("IN_PROGRESS"))
                .withQueryParam("orderRef", equalTo("ord-1"))
                .withQueryParam("tenantOrderId", equalTo("ext-001"))
                .withQueryParam("orderBy", equalTo("TARGET_TIME_ASC"))
                .withQueryParam("shortId", equalTo("PK99")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"packJobs":[{"id":"pj-1"},{"id":"pj-2"}],"nextCursor":"cur-2"}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/packjobs"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"packJobs":[{"id":"pj-3"}]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (PackJob j : client.packing().listAll(PackJobListRequest.builder().size(2).build())) {
            ids.add(j.id().value());
        }

        // Then
        assertThat(ids).containsExactly("pj-1", "pj-2", "pj-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/packjobs")));
    }

    // --- update ---

    @Test
    void update_returnsUpdatedPackJob() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/packjobs/pj-1"))
                .willReturn(okJson("""
                        {"id":"pj-1","status":"IN_PROGRESS"}
                        """)));

        // When
        PackJob job = client.packing().update(new PackJobId("pj-1"),
                UpdatePackJobRequest.builder().version(2).status("IN_PROGRESS").build());

        // Then
        assertThat(job.id().value()).isEqualTo("pj-1");
        assertThat(job.status()).isEqualTo("IN_PROGRESS");
    }

    @Test
    void update_sendsVersionAndActionInBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/packjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        client.packing().update(new PackJobId("pj-1"), UpdatePackJobRequest.builder().version(3).status("IN_PROGRESS").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/packjobs/pj-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("3")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyPackJob")))
                .withRequestBody(matchingJsonPath("$.actions[0].status", equalTo("IN_PROGRESS"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdatePackJobRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
