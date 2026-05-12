package de.joesst.dev.fulfillmenttools.externalactions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ExternalActionsAsyncTest {

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
    void getAsync_returnsExternalAction() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions/ea-1"))
                .willReturn(okJson("{\"id\":\"ea-1\",\"name\":\"Notify Warehouse\",\"processRef\":\"proc-1\",\"groups\":[]}")));

        // When
        ExternalAction action = client.externalActions().getAsync("ea-1").get();

        // Then
        assertThat(action.id()).isEqualTo("ea-1");
        assertThat(action.name()).isEqualTo("Notify Warehouse");
        assertThat(action.processRef()).isEqualTo("proc-1");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions"))
                .willReturn(okJson("[{\"id\":\"ea-1\",\"name\":\"Action A\"},{\"id\":\"ea-2\",\"name\":\"Action B\"}]")));

        // When
        var page = client.externalActions().listAsync(ExternalActionListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedExternalAction() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/externalactions"))
                .willReturn(okJson("{\"id\":\"ea-new\",\"name\":\"My Action\",\"processRef\":\"proc-1\",\"groups\":[]}")));

        // When
        ExternalAction action = client.externalActions()
                .createAsync(CreateExternalActionRequest.builder()
                        .processRef("proc-1")
                        .nameLocalized(Map.of("en_US", "My Action"))
                        .groups(List.of())
                        .action(Map.of("type", "BLANK_LINK", "url", "https://example.com"))
                        .build()).get();

        // Then
        assertThat(action.id()).isEqualTo("ea-new");
        assertThat(action.name()).isEqualTo("My Action");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
