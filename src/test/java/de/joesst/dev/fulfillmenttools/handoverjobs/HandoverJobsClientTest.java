package de.joesst.dev.fulfillmenttools.handoverjobs;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.id.HandoverJobId;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class HandoverJobsClientTest {

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
    void get_returnsDeserializedHandoverJob() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .willReturn(okJson("""
                        {
                          "id": "hj-1",
                          "facilityRef": "fac-1",
                          "status": "OPEN",
                          "channel": "SHIPPING",
                          "shortIdentifier": "HJ12",
                          "created": "2024-03-01T10:00:00Z",
                          "lastModified": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        HandoverJob job = client.handoverJobs().get(new HandoverJobId("hj-1"));

        // Then
        assertThat(job.id()).isEqualTo("hj-1");
        assertThat(job.facilityRef()).isEqualTo("fac-1");
        assertThat(job.status()).isEqualTo("OPEN");
        assertThat(job.channel()).isEqualTo("SHIPPING");
        assertThat(job.shortIdentifier()).isEqualTo("HJ12");
        assertThat(job.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .willReturn(okJson("{\"id\":\"hj-1\"}")));

        // When
        client.handoverJobs().get(new HandoverJobId("hj-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Handover job not found","description":"No handover job with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.handoverJobs().get(new HandoverJobId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Handover job not found");
    }

    // --- list ---

    @Test
    void list_returnsPageWithItemsAndCursor() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs"))
                .willReturn(okJson("""
                        {
                          "handoverJobs": [
                            {"id":"hj-1","status":"OPEN"},
                            {"id":"hj-2","status":"HANDED_OVER"}
                          ],
                          "nextCursor": "cursor-page-2"
                        }
                        """)));

        // When
        Page<HandoverJob> page = client.handoverJobs().list(HandoverJobListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("hj-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsAllQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs"))
                .willReturn(okJson("{\"handoverJobs\":[]}")));

        // When
        client.handoverJobs().list(HandoverJobListRequest.builder()
                .size(5)
                .startAfterId("cursor-abc")
                .facilityRef("fac-1")
                .status(List.of("OPEN", "HANDED_OVER"))
                .channel("SHIPPING")
                .tenantOrderId("ext-001")
                .searchTerm("test")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/handoverjobs"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityRef", equalTo("fac-1"))
                .withQueryParam("status", equalTo("OPEN"))
                .withQueryParam("status", equalTo("HANDED_OVER"))
                .withQueryParam("channel", equalTo("SHIPPING"))
                .withQueryParam("tenantOrderId", equalTo("ext-001"))
                .withQueryParam("searchTerm", equalTo("test")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"handoverJobs":[{"id":"hj-1"},{"id":"hj-2"}],"nextCursor":"cur-2"}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/handoverjobs"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"handoverJobs":[{"id":"hj-3"}]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (HandoverJob j : client.handoverJobs().listAll(HandoverJobListRequest.builder().size(2).build())) {
            ids.add(j.id());
        }

        // Then
        assertThat(ids).containsExactly("hj-1", "hj-2", "hj-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/handoverjobs")));
    }

    // --- update ---

    @Test
    void update_returnsUpdatedHandoverJob() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .willReturn(okJson("""
                        {"id":"hj-1","status":"HANDED_OVER"}
                        """)));

        // When
        HandoverJob job = client.handoverJobs().update(new HandoverJobId("hj-1"),
                UpdateHandoverJobRequest.builder().version(2).status("HANDED_OVER").build());

        // Then
        assertThat(job.id()).isEqualTo("hj-1");
        assertThat(job.status()).isEqualTo("HANDED_OVER");
    }

    @Test
    void update_sendsVersionAndActionInBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .willReturn(okJson("{\"id\":\"hj-1\",\"status\":\"HANDED_OVER\"}")));

        // When
        client.handoverJobs().update(new HandoverJobId("hj-1"), UpdateHandoverJobRequest.builder().version(3).status("HANDED_OVER").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("3")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyHandoverjob")))
                .withRequestBody(matchingJsonPath("$.actions[0].status", equalTo("HANDED_OVER"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateHandoverJobRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- cancel ---

    @Test
    void cancel_sendsActionNameCancelWithReason() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/handoverjobs/hj-1/actions"))
                .willReturn(okJson("{\"id\":\"hj-1\",\"status\":\"CANCELED\"}")));

        // When
        HandoverJob job = client.handoverJobs().cancel(new HandoverJobId("hj-1"), 2, "CONSUMER_NO_SHOW");

        // Then
        assertThat(job.status()).isEqualTo("CANCELED");
        server.verify(postRequestedFor(urlPathEqualTo("/api/handoverjobs/hj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("CANCEL")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.payload.handoverJobCancelReason", equalTo("CONSUMER_NO_SHOW"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
