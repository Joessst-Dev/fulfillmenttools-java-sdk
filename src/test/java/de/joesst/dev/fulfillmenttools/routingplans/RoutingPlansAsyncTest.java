package de.joesst.dev.fulfillmenttools.routingplans;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class RoutingPlansAsyncTest {

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
    void getAsync_returnsRoutingPlan() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans/rp-1"))
                .willReturn(okJson("{\"id\":\"rp-1\",\"name\":\"Default Plan\",\"status\":\"ACTIVE\"}")));

        // When
        RoutingPlan plan = client.routingPlans().getAsync("rp-1").get();

        // Then
        assertThat(plan.id()).isEqualTo("rp-1");
        assertThat(plan.name()).isEqualTo("Default Plan");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingplans"))
                .willReturn(okJson("{\"routingPlans\":[{\"id\":\"rp-1\",\"name\":\"Plan A\"},{\"id\":\"rp-2\",\"name\":\"Plan B\"}]}")));

        // When
        var page = client.routingPlans().listAsync(RoutingPlanListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedRoutingPlan() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routingplans"))
                .willReturn(okJson("{\"id\":\"rp-new\",\"name\":\"New Plan\",\"status\":\"ACTIVE\"}")));

        // When
        RoutingPlan plan = client.routingPlans()
                .createAsync(CreateRoutingPlanRequest.builder().name("New Plan").build()).get();

        // Then
        assertThat(plan.id()).isEqualTo("rp-new");
        assertThat(plan.name()).isEqualTo("New Plan");
    }

    @Test
    void deleteAsync_completesWithoutException() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/routingplans/rp-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.routingPlans().deleteAsync("rp-1").get())
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
