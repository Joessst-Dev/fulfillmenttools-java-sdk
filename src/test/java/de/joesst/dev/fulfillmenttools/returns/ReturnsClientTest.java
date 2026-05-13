package de.joesst.dev.fulfillmenttools.returns;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.ReturnId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ReturnsClientTest {

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
    void get_returnsDeserializedReturn() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/itemreturnjobs/ret-1"))
                .willReturn(okJson("""
                        {
                          "id": "ret-1",
                          "status": "OPEN",
                          "shortId": "RJ12",
                          "created": "2024-03-01T10:00:00Z",
                          "lastModified": "2024-03-01T11:00:00Z"
                        }
                        """)));

        // When
        Return ret = client.returns().get(new ReturnId("ret-1"));

        // Then
        assertThat(ret.id().value()).isEqualTo("ret-1");
        assertThat(ret.status()).isEqualTo("OPEN");
        assertThat(ret.shortId()).isEqualTo("RJ12");
        assertThat(ret.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/itemreturnjobs/ret-1"))
                .willReturn(okJson("{\"id\":\"ret-1\"}")));

        // When
        client.returns().get(new ReturnId("ret-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/itemreturnjobs/ret-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/itemreturnjobs/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Return job not found","description":"No item return job with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.returns().get(new ReturnId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Return job not found");
    }

    // --- list ---

    @Test
    void list_returnsPageDeserializedFromRawArray() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/itemreturnjobs"))
                .willReturn(okJson("""
                        [
                          {"id":"ret-1","status":"OPEN"},
                          {"id":"ret-2","status":"IN_PROGRESS"}
                        ]
                        """)));

        // When
        Page<Return> page = client.returns().list(ReturnListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("ret-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsAllQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/itemreturnjobs"))
                .willReturn(okJson("[]")));

        // When
        client.returns().list(ReturnListRequest.builder()
                .size(5)
                .startAfterId("cursor-abc")
                .facilityId("fac-1")
                .itemReturnJobStatus(List.of("OPEN", "IN_PROGRESS"))
                .searchTerm("test")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/itemreturnjobs"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("facilityId", equalTo("fac-1"))
                .withQueryParam("itemReturnJobStatus", equalTo("OPEN"))
                .withQueryParam("itemReturnJobStatus", equalTo("IN_PROGRESS"))
                .withQueryParam("searchTerm", equalTo("test")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/itemreturnjobs"))
                .willReturn(okJson("""
                        [{"id":"ret-1"},{"id":"ret-2"},{"id":"ret-3"}]
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (Return r : client.returns().listAll(ReturnListRequest.builder().build())) {
            ids.add(r.id().value());
        }

        // Then
        assertThat(ids).containsExactly("ret-1", "ret-2", "ret-3");
    }

    // --- create ---

    @Test
    void create_returnsCreatedReturn() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/itemreturnjobs"))
                .willReturn(okJson("{\"id\":\"ret-new\",\"status\":\"OPEN\"}")));

        // When
        Return ret = client.returns().create(CreateReturnRequest.builder()
                .originFacilityRefs(List.of("fac-1"))
                .status("OPEN")
                .build());

        // Then
        assertThat(ret.id().value()).isEqualTo("ret-new");
        assertThat(ret.status()).isEqualTo("OPEN");
    }

    @Test
    void create_sendsOriginFacilityRefsAndStatus() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/itemreturnjobs"))
                .willReturn(okJson("{\"id\":\"ret-new\"}")));

        // When
        client.returns().create(CreateReturnRequest.builder()
                .originFacilityRefs(List.of("fac-1", "fac-2"))
                .status("OPEN")
                .tenantOrderId("ext-001")
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/itemreturnjobs"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.originFacilityRefs[0]", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.status", equalTo("OPEN")))
                .withRequestBody(matchingJsonPath("$.tenantOrderId", equalTo("ext-001"))));
    }

    @Test
    void create_requiresOriginFacilityRefs() {
        assertThatThrownBy(() -> CreateReturnRequest.builder().status("OPEN").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("originFacilityRefs");
    }

    @Test
    void create_requiresStatus() {
        assertThatThrownBy(() -> CreateReturnRequest.builder().originFacilityRefs(List.of("fac-1")).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("status");
    }

    // --- actions ---

    @Test
    void start_sendsStartAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/itemreturnjobs/ret-1/actions"))
                .willReturn(okJson("{\"id\":\"ret-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        Return ret = client.returns().start(new ReturnId("ret-1"), 1);

        // Then
        assertThat(ret.status()).isEqualTo("IN_PROGRESS");
        server.verify(postRequestedFor(urlPathEqualTo("/api/itemreturnjobs/ret-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("StartItemReturnJob")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1"))));
    }

    @Test
    void finish_sendsFinishAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/itemreturnjobs/ret-1/actions"))
                .willReturn(okJson("{\"id\":\"ret-1\",\"status\":\"FINISHED\"}")));

        // When
        client.returns().finish(new ReturnId("ret-1"), 2);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/itemreturnjobs/ret-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("FinishItemReturnJob"))));
    }

    @Test
    void restart_sendsRestartAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/itemreturnjobs/ret-1/actions"))
                .willReturn(okJson("{\"id\":\"ret-1\",\"status\":\"OPEN\"}")));

        // When
        client.returns().restart(new ReturnId("ret-1"), 3);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/itemreturnjobs/ret-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("RestartItemReturnJob"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
