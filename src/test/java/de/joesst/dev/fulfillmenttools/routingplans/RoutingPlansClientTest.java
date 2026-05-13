package de.joesst.dev.fulfillmenttools.routingplans;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class RoutingPlansClientTest {

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
    void get_returnsDeserializedRoutingPlan() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans/rp-1"))
                .willReturn(okJson("""
                        {
                          "id": "rp-1",
                          "version": 2,
                          "status": "ROUTED",
                          "consolidatedStatus": "ROUTED",
                          "processId": "proc-1",
                          "orderRef": "order-1",
                          "facilityRef": "fac-1",
                          "priority": 10.0,
                          "routingRun": 1.0,
                          "finalizeRun": 0.0,
                          "predecessorRerouteRoutingPlanRefs": [],
                          "successorRerouteRoutingPlanRefs": [],
                          "decisionLogs": []
                        }
                        """)));

        // When
        RoutingPlan plan = client.routingPlans().get(new RoutingPlanId("rp-1"));

        // Then
        assertThat(plan.id().value()).isEqualTo("rp-1");
        assertThat(plan.version()).isEqualTo(2);
        assertThat(plan.status()).isEqualTo("ROUTED");
        assertThat(plan.consolidatedStatus()).isEqualTo("ROUTED");
        assertThat(plan.processId().value()).isEqualTo("proc-1");
        assertThat(plan.facilityRef().value()).isEqualTo("fac-1");
        assertThat(plan.priority()).isEqualTo(10.0);
        assertThat(plan.predecessorRerouteRoutingPlanRefs()).isEmpty();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans/rp-1"))
                .willReturn(okJson("{\"id\":\"rp-1\"}")));

        // When
        client.routingPlans().get(new RoutingPlanId("rp-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/routingplans/rp-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Routing plan not found","description":"No routing plan with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.routingPlans().get(new RoutingPlanId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Routing plan not found");
    }

    // --- list ---

    @Test
    void list_returnsPageFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans"))
                .willReturn(okJson("""
                        {
                          "routingPlans": [{"id":"rp-1"},{"id":"rp-2"}],
                          "total": 2
                        }
                        """)));

        // When
        Page<RoutingPlan> page = client.routingPlans().list(RoutingPlanListRequest.builder().build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("rp-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsOrderRefQueryParam() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans"))
                .willReturn(okJson("{\"routingPlans\":[], \"total\":0}")));

        // When
        client.routingPlans().list(RoutingPlanListRequest.builder().orderRef("order-abc").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/routingplans"))
                .withQueryParam("orderRef", equalTo("order-abc")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans"))
                .willReturn(okJson("{\"routingPlans\":[{\"id\":\"rp-1\"},{\"id\":\"rp-2\"}],\"total\":2}")));

        // When
        List<String> ids = new ArrayList<>();
        for (RoutingPlan rp : client.routingPlans().listAll(RoutingPlanListRequest.builder().build())) {
            ids.add(rp.id().value());
        }

        // Then
        assertThat(ids).containsExactly("rp-1", "rp-2");
    }

    // --- update ---

    @Test
    void update_sendsPatchActionsFormat() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/routingplans/rp-1"))
                .willReturn(okJson("{\"id\":\"rp-1\",\"status\":\"CANCELED\"}")));

        // When
        RoutingPlan plan = client.routingPlans().update(new RoutingPlanId("rp-1"), UpdateRoutingPlanRequest.builder()
                .version(2)
                .status("CANCELED")
                .build());

        // Then
        assertThat(plan.id().value()).isEqualTo("rp-1");
        server.verify(patchRequestedFor(urlPathEqualTo("/api/routingplans/rp-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyRoutingPlan")))
                .withRequestBody(matchingJsonPath("$.actions[0].status", equalTo("CANCELED"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateRoutingPlanRequest.builder().status("CANCELED").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- delete ---

    @Test
    void delete_sends204() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/routingplans/rp-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.routingPlans().delete(new RoutingPlanId("rp-1")))
                .doesNotThrowAnyException();
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
