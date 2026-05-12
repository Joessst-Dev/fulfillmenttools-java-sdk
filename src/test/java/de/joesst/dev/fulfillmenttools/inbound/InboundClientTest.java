package de.joesst.dev.fulfillmenttools.inbound;

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

class InboundClientTest {

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
    void get_returnsDeserializedStowJob() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs/sj-1"))
                .willReturn(okJson("""
                        {
                          "id": "sj-1",
                          "version": 1,
                          "status": "OPEN",
                          "facilityRef": "fac-1",
                          "shortId": "SJ42",
                          "created": "2024-03-01T10:00:00Z",
                          "lastModified": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        StowJob job = client.inbound().get("sj-1");

        // Then
        assertThat(job.id()).isEqualTo("sj-1");
        assertThat(job.status()).isEqualTo("OPEN");
        assertThat(job.facilityRef()).isEqualTo("fac-1");
        assertThat(job.version()).isEqualTo(1);
        assertThat(job.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs/sj-1"))
                .willReturn(okJson("{\"id\":\"sj-1\"}")));

        // When
        client.inbound().get("sj-1");

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stowjobs/sj-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Stow job not found","description":"No stow job with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.inbound().get("missing"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Stow job not found");
    }

    // --- list ---

    @Test
    void list_returnsPageDeserializedFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("""
                        {
                          "stowJobs": [
                            {"id":"sj-1","status":"OPEN"},
                            {"id":"sj-2","status":"IN_PROGRESS"}
                          ]
                        }
                        """)));

        // When
        Page<StowJob> page = client.inbound().list(StowJobListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("sj-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsAllQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("{\"stowJobs\":[]}")));

        // When
        client.inbound().list(StowJobListRequest.builder()
                .size(5)
                .startAfterId("cursor-abc")
                .sort("LAST_MODIFIED_DESC")
                .facilityRef(List.of("fac-1", "fac-2"))
                .status(List.of("OPEN", "IN_PROGRESS"))
                .tenantArticleId(List.of("art-1"))
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/stowjobs"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("sort", equalTo("LAST_MODIFIED_DESC"))
                .withQueryParam("facilityRef", equalTo("fac-1"))
                .withQueryParam("facilityRef", equalTo("fac-2"))
                .withQueryParam("status", equalTo("OPEN"))
                .withQueryParam("status", equalTo("IN_PROGRESS"))
                .withQueryParam("tenantArticleId", equalTo("art-1")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("{\"stowJobs\":[{\"id\":\"sj-1\"},{\"id\":\"sj-2\"},{\"id\":\"sj-3\"}]}")));

        // When
        List<String> ids = new ArrayList<>();
        for (StowJob j : client.inbound().listAll(StowJobListRequest.builder().build())) {
            ids.add(j.id());
        }

        // Then
        assertThat(ids).containsExactly("sj-1", "sj-2", "sj-3");
    }

    // --- create ---

    @Test
    void create_returnsCreatedStowJob() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("{\"id\":\"sj-new\",\"facilityRef\":\"fac-1\",\"status\":\"OPEN\"}")));

        // When
        StowJob job = client.inbound().create(CreateStowJobRequest.builder()
                .facilityRef("fac-1")
                .status("OPEN")
                .stowLineItems(List.of())
                .build());

        // Then
        assertThat(job.id()).isEqualTo("sj-new");
        assertThat(job.facilityRef()).isEqualTo("fac-1");
    }

    @Test
    void create_sendsFacilityRefStatusAndStowLineItems() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("{\"id\":\"sj-new\"}")));

        // When
        client.inbound().create(CreateStowJobRequest.builder()
                .facilityRef("fac-1")
                .status("OPEN")
                .stowLineItems(List.of())
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stowjobs"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.facilityRef", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.status", equalTo("OPEN"))));
    }

    @Test
    void create_requiresFacilityRef() {
        assertThatThrownBy(() -> CreateStowJobRequest.builder()
                .status("OPEN").stowLineItems(List.of()).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("facilityRef");
    }

    @Test
    void create_requiresStatus() {
        assertThatThrownBy(() -> CreateStowJobRequest.builder()
                .facilityRef("fac-1").stowLineItems(List.of()).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("status");
    }

    @Test
    void create_requiresStowLineItems() {
        assertThatThrownBy(() -> CreateStowJobRequest.builder()
                .facilityRef("fac-1").status("OPEN").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("stowLineItems");
    }

    // --- update ---

    @Test
    void update_sendsFlatPatchBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/stowjobs/sj-1"))
                .willReturn(okJson("{\"id\":\"sj-1\",\"status\":\"OPEN\",\"priority\":5}")));

        // When
        StowJob job = client.inbound().update("sj-1", UpdateStowJobRequest.builder()
                .version(2)
                .priority(5)
                .build());

        // Then
        assertThat(job.id()).isEqualTo("sj-1");
        server.verify(patchRequestedFor(urlPathEqualTo("/api/stowjobs/sj-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.priority", equalTo("5"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateStowJobRequest.builder().priority(1).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- actions ---

    @Test
    void start_sendsStartStowJobAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stowjobs/sj-1/actions"))
                .willReturn(okJson("{\"id\":\"sj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        StowJob job = client.inbound().start("sj-1", 1);

        // Then
        assertThat(job.status()).isEqualTo("IN_PROGRESS");
        server.verify(postRequestedFor(urlPathEqualTo("/api/stowjobs/sj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("START_STOW_JOB")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1"))));
    }

    @Test
    void cancel_sendsCancelStowJobAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stowjobs/sj-1/actions"))
                .willReturn(okJson("{\"id\":\"sj-1\",\"status\":\"CANCELED\"}")));

        // When
        client.inbound().cancel("sj-1", 2);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stowjobs/sj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("CANCEL_STOW_JOB"))));
    }

    @Test
    void close_sendsCloseStowJobAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stowjobs/sj-1/actions"))
                .willReturn(okJson("{\"id\":\"sj-1\",\"status\":\"CLOSED\"}")));

        // When
        client.inbound().close("sj-1", 3);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/stowjobs/sj-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("CLOSE_STOW_JOB"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
