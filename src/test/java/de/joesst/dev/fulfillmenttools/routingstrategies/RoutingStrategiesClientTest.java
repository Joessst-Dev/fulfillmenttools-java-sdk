package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class RoutingStrategiesClientTest {

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
    void get_returnsDeserializedRoutingStrategy() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies/rs-1"))
                .willReturn(okJson("""
                        {
                          "id": "rs-1",
                          "version": 3,
                          "name": "Nearest Facility",
                          "nameLocalized": {"en_US": "Nearest Facility"},
                          "inUse": true,
                          "revision": 2
                        }
                        """)));

        // When
        RoutingStrategy strategy = client.routingStrategies().get("rs-1");

        // Then
        assertThat(strategy.id()).isEqualTo("rs-1");
        assertThat(strategy.version()).isEqualTo(3);
        assertThat(strategy.name()).isEqualTo("Nearest Facility");
        assertThat(strategy.inUse()).isTrue();
        assertThat(strategy.revision()).isEqualTo(2);
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies/rs-1"))
                .willReturn(okJson("{\"id\":\"rs-1\"}")));

        // When
        client.routingStrategies().get("rs-1");

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/routing/strategies/rs-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Routing strategy not found","description":"No strategy with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.routingStrategies().get("missing"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Routing strategy not found");
    }

    // --- list ---

    @Test
    void list_returnsPageFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies"))
                .willReturn(okJson("""
                        {
                          "routingStrategies": [{"id":"rs-1"},{"id":"rs-2"}],
                          "total": 2
                        }
                        """)));

        // When
        Page<RoutingStrategy> page = client.routingStrategies().list(RoutingStrategyListRequest.builder().build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("rs-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsSizeQueryParam() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies"))
                .willReturn(okJson("{\"routingStrategies\":[], \"total\":0}")));

        // When
        client.routingStrategies().list(RoutingStrategyListRequest.builder().size(10).build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/routing/strategies"))
                .withQueryParam("size", equalTo("10")));
    }

    @Test
    void list_sendsStartAfterIdQueryParam() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies"))
                .willReturn(okJson("{\"routingStrategies\":[], \"total\":0}")));

        // When
        client.routingStrategies().list(RoutingStrategyListRequest.builder().startAfterId("rs-5").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/routing/strategies"))
                .withQueryParam("startAfterId", equalTo("rs-5")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/strategies"))
                .willReturn(okJson("{\"routingStrategies\":[{\"id\":\"rs-1\"},{\"id\":\"rs-2\"}],\"total\":2}")));

        // When
        List<String> ids = new ArrayList<>();
        for (RoutingStrategy rs : client.routingStrategies().listAll(RoutingStrategyListRequest.builder().build())) {
            ids.add(rs.id());
        }

        // Then
        assertThat(ids).containsExactly("rs-1", "rs-2");
    }

    // --- create ---

    @Test
    void create_sendsNameLocalizedInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/strategies"))
                .willReturn(okJson("{\"id\":\"rs-new\",\"name\":\"New Strategy\"}")));

        // When
        RoutingStrategy strategy = client.routingStrategies().create(
                CreateRoutingStrategyRequest.builder()
                        .nameLocalized(Map.of("en_US", "New Strategy"))
                        .build());

        // Then
        assertThat(strategy.id()).isEqualTo("rs-new");
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/strategies"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.nameLocalized.en_US", equalTo("New Strategy"))));
    }

    @Test
    void create_requiresNameLocalized() {
        assertThatThrownBy(() -> CreateRoutingStrategyRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("nameLocalized");
    }

    // --- update ---

    @Test
    void update_sendsPutWithVersionAndNameLocalized() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/routing/strategies/rs-1"))
                .willReturn(okJson("{\"id\":\"rs-1\",\"version\":2}")));

        // When
        RoutingStrategy strategy = client.routingStrategies().update("rs-1",
                UpdateRoutingStrategyRequest.builder()
                        .version(1)
                        .nameLocalized(Map.of("en_US", "Updated Strategy"))
                        .rootNode(minimalNode(true))
                        .build());

        // Then
        assertThat(strategy.id()).isEqualTo("rs-1");
        server.verify(putRequestedFor(urlPathEqualTo("/api/routing/strategies/rs-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1")))
                .withRequestBody(matchingJsonPath("$.nameLocalized.en_US", equalTo("Updated Strategy")))
                .withRequestBody(matchingJsonPath("$.rootNode.active", equalTo("true"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateRoutingStrategyRequest.builder()
                .nameLocalized(Map.of("en_US", "x"))
                .rootNode(minimalNode(true))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    @Test
    void update_requiresNameLocalized() {
        assertThatThrownBy(() -> UpdateRoutingStrategyRequest.builder()
                .version(1)
                .rootNode(minimalNode(true))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("nameLocalized");
    }

    @Test
    void update_requiresRootNode() {
        assertThatThrownBy(() -> UpdateRoutingStrategyRequest.builder()
                .version(1)
                .nameLocalized(Map.of("en_US", "x"))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("rootNode");
    }

    // --- Helpers ---

    /**
     * Builds a minimal {@link RoutingStrategyNode} suitable for use in tests that only care
     * about the {@code active} flag being serialised correctly.
     */
    private static RoutingStrategyNode minimalNode(boolean active) {
        RoutingStrategyNodeConfig config = new RoutingStrategyNodeConfig(
                List.of(), List.of(), null, null, null);
        return new RoutingStrategyNode(
                null, active, config,
                Map.of("en_US", "Test Node"),
                null, null, null, null, null, null);
    }

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
