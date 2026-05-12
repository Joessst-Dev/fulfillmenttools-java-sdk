package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class RoutingStrategiesAsyncTest {

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
    void getAsync_returnsRoutingStrategy() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingstrategies/rs-1"))
                .willReturn(okJson("{\"id\":\"rs-1\",\"name\":\"Nearest Facility\",\"status\":\"ACTIVE\"}")));

        // When
        RoutingStrategy strategy = client.routingStrategies().getAsync("rs-1").get();

        // Then
        assertThat(strategy.id()).isEqualTo("rs-1");
        assertThat(strategy.name()).isEqualTo("Nearest Facility");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routingstrategies"))
                .willReturn(okJson("{\"routingStrategies\":[{\"id\":\"rs-1\",\"name\":\"Strategy A\"},{\"id\":\"rs-2\",\"name\":\"Strategy B\"}]}")));

        // When
        var page = client.routingStrategies().listAsync(RoutingStrategyListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedRoutingStrategy() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routingstrategies"))
                .willReturn(okJson("{\"id\":\"rs-new\",\"name\":\"New Strategy\",\"status\":\"ACTIVE\"}")));

        // When
        RoutingStrategy strategy = client.routingStrategies()
                .createAsync(CreateRoutingStrategyRequest.builder().name("New Strategy").build()).get();

        // Then
        assertThat(strategy.id()).isEqualTo("rs-new");
        assertThat(strategy.name()).isEqualTo("New Strategy");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
