package de.joesst.dev.fulfillmenttools.pickjobs;

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
                          "createdDate": "2024-03-01T10:00:00Z",
                          "lastModifiedDate": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        PickJob job = client.pickJobs().get("pj-1");

        // Then
        assertThat(job.id()).isEqualTo("pj-1");
        assertThat(job.facilityRef()).isEqualTo("fac-1");
        assertThat(job.status()).isEqualTo("OPEN");
        assertThat(job.createdDate()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\"}")));

        // When
        client.pickJobs().get("pj-1");

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
        assertThatThrownBy(() -> client.pickJobs().get("missing"))
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
        assertThat(page.items().get(0).id()).isEqualTo("pj-1");
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
                .size(5).startAfterId("cursor-abc").facilityRef("fac-1").status("OPEN").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/pickjobs"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRef", equalTo("fac-1"))
                .withQueryParam("status", equalTo("OPEN")));
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
            ids.add(j.id());
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
        PickJob job = client.pickJobs().update("pj-1",
                UpdatePickJobRequest.builder().status("IN_PROGRESS").build());

        // Then
        assertThat(job.id()).isEqualTo("pj-1");
        assertThat(job.status()).isEqualTo("IN_PROGRESS");
    }

    @Test
    void update_sendsJsonBodyWithPatch() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        client.pickJobs().update("pj-1", UpdatePickJobRequest.builder().status("IN_PROGRESS").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/pickjobs/pj-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.status", equalTo("IN_PROGRESS"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
