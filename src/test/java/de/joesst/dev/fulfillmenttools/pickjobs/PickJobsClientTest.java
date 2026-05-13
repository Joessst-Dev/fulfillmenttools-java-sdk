package de.joesst.dev.fulfillmenttools.pickjobs;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class PickJobsClientTest {

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
    void get_returnsDeserializedPickJob() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("""
                        {
                          "id": "pj-1",
                          "facilityRef": "fac-1",
                          "status": "OPEN",
                          "shortId": "AS12",
                          "created": "2024-03-01T10:00:00Z",
                          "lastModified": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        PickJob job = client.pickJobs().get(new PickJobId("pj-1"));

        // Then
        assertThat(job.id().value()).isEqualTo("pj-1");
        assertThat(job.facilityRef().value()).isEqualTo("fac-1");
        assertThat(job.status()).isEqualTo("OPEN");
        assertThat(job.shortId()).isEqualTo("AS12");
        assertThat(job.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\"}")));

        // When
        client.pickJobs().get(new PickJobId("pj-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Pick job not found","description":"No pick job with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.pickJobs().get(new PickJobId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Pick job not found");
    }

    // --- list ---

    @Test
    void list_returnsPageWithItemsAndCursor() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs"))
                .willReturn(okJson("""
                        {
                          "pickJobs": [
                            {"id":"pj-1","status":"OPEN"},
                            {"id":"pj-2","status":"IN_PROGRESS"}
                          ],
                          "nextCursor": "cursor-page-2"
                        }
                        """)));

        // When
        Page<PickJob> page = client.pickJobs().list(PickJobListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("pj-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsAllQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs"))
                .willReturn(okJson("{\"pickJobs\":[]}")));

        // When
        client.pickJobs().list(PickJobListRequest.builder()
                .size(5)
                .startAfterId("cursor-abc")
                .facilityRef(new FacilityId("fac-1"))
                .status(List.of("OPEN", "IN_PROGRESS"))
                .tenantOrderId(new TenantOrderId("ext-001"))
                .orderBy("TARGET_TIME_ASC")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/pickjobs"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRef", equalTo("fac-1"))
                .withQueryParam("status", equalTo("OPEN"))
                .withQueryParam("status", equalTo("IN_PROGRESS"))
                .withQueryParam("tenantOrderId", equalTo("ext-001"))
                .withQueryParam("orderBy", equalTo("TARGET_TIME_ASC")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"pickJobs":[{"id":"pj-1"},{"id":"pj-2"}],"nextCursor":"cur-2"}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/pickjobs"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"pickJobs":[{"id":"pj-3"}]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (PickJob j : client.pickJobs().listAll(PickJobListRequest.builder().size(2).build())) {
            ids.add(j.id().value());
        }

        // Then
        assertThat(ids).containsExactly("pj-1", "pj-2", "pj-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/pickjobs")));
    }

    // --- update ---

    @Test
    void update_returnsUpdatedPickJob() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("""
                        {"id":"pj-1","status":"IN_PROGRESS"}
                        """)));

        // When
        PickJob job = client.pickJobs().update(new PickJobId("pj-1"),
                UpdatePickJobRequest.builder().version(2).status("IN_PROGRESS").build());

        // Then
        assertThat(job.id().value()).isEqualTo("pj-1");
        assertThat(job.status()).isEqualTo("IN_PROGRESS");
    }

    @Test
    void update_sendsVersionAndActionInBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        client.pickJobs().update(new PickJobId("pj-1"), UpdatePickJobRequest.builder().version(3).status("IN_PROGRESS").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("3")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyPickJob")))
                .withRequestBody(matchingJsonPath("$.actions[0].status", equalTo("IN_PROGRESS"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdatePickJobRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- actions ---

    @Test
    void abort_sendsActionNameAbort() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"ABORTED\"}")));

        // When
        PickJob job = client.pickJobs().abort(new PickJobId("pj-1"), 2);

        // Then
        assertThat(job.status()).isEqualTo("ABORTED");
        server.verify(postRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("ABORT")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2"))));
    }

    @Test
    void start_sendsActionNameStart() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        client.pickJobs().start(new PickJobId("pj-1"), 1);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("START"))));
    }

    @Test
    void pause_sendsActionNamePause() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"PAUSED\"}")));

        // When
        client.pickJobs().pause(new PickJobId("pj-1"), 1);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("PAUSE"))));
    }

    @Test
    void restart_sendsActionNameRestart() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"OPEN\"}")));

        // When
        client.pickJobs().restart(new PickJobId("pj-1"), 1);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("RESTART"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
