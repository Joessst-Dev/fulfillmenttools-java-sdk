package de.joesst.dev.fulfillmenttools.health;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class HealthClientTest {

    private static WireMockServer server;
    private FulfillmenttoolsClient client;

    @BeforeAll
    static void startServer() {
        server = new WireMockServer(wireMockConfig().dynamicPort());
        server.start();
    }

    @AfterAll
    static void stopServer() { server.stop(); }

    @BeforeEach
    void setUp() {
        server.resetAll();
        client = FulfillmenttoolsClient.builder()
                .baseUrl(server.baseUrl())
                .tokenProvider(fixedToken("test-bearer"))
                .build();
    }

    // --- health ---

    @Test
    void health_returnsDeserializedResult() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("""
                        {
                          "status": "UP",
                          "dependencies": [
                            {"name": "database", "status": "UP"},
                            {"name": "cache",    "status": "UP"}
                          ]
                        }
                        """)));

        // When
        HealthResult result = client.health().health();

        // Then
        assertThat(result.status()).isEqualTo("UP");
        assertThat(result.dependencies()).hasSize(2);
        assertThat(result.dependencies().get(0).name()).isEqualTo("database");
        assertThat(result.dependencies().get(0).status()).isEqualTo("UP");
    }

    @Test
    void health_downStatus_isDeserialised() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("""
                        {
                          "status": "DOWN",
                          "dependencies": [
                            {"name": "database", "status": "DOWN"}
                          ]
                        }
                        """)));

        // When
        HealthResult result = client.health().health();

        // Then
        assertThat(result.status()).isEqualTo("DOWN");
        assertThat(result.dependencies().get(0).status()).isEqualTo("DOWN");
    }

    @Test
    void health_sendsBearerToken() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("{\"status\":\"UP\",\"dependencies\":[]}")));

        // When
        client.health().health();

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/health"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void healthAsync_returnsResult() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("""
                        {"status":"UP","dependencies":[{"name":"db","status":"UP"}]}
                        """)));

        // When
        HealthResult result = client.health().healthAsync().get();

        // Then
        assertThat(result.status()).isEqualTo("UP");
        assertThat(result.dependencies()).hasSize(1);
    }

    // --- status ---

    @Test
    void status_returnsDeserializedStatus() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/status"))
                .willReturn(okJson("{\"status\":\"UP\"}")));

        // When
        SystemStatus result = client.health().status();

        // Then
        assertThat(result.status()).isEqualTo("UP");
    }

    @Test
    void status_degraded_isDeserialised() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/status"))
                .willReturn(okJson("{\"status\":\"DEGRADED\"}")));

        // When
        SystemStatus result = client.health().status();

        // Then
        assertThat(result.status()).isEqualTo("DEGRADED");
    }

    @Test
    void status_sendsBearerToken() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/status"))
                .willReturn(okJson("{\"status\":\"UP\"}")));

        // When
        client.health().status();

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/status"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void statusAsync_returnsStatus() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/status"))
                .willReturn(okJson("{\"status\":\"DOWN\"}")));

        // When
        SystemStatus result = client.health().statusAsync().get();

        // Then
        assertThat(result.status()).isEqualTo("DOWN");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
