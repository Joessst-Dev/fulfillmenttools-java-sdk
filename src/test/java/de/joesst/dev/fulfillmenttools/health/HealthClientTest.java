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
    void health_returnsDeserializedStatus() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("""
                        {"status":"UP","details":{"db":{"status":"UP"},"redis":{"status":"UP"}}}
                        """)));

        // When
        HealthStatus result = client.health().health();

        // Then
        assertThat(result.status()).isEqualTo("UP");
        assertThat(result.details()).containsKey("db");
    }

    @Test
    void health_sendsBearerToken() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("{\"status\":\"UP\"}")));

        // When
        client.health().health();

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/health"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void health_noDetails_returnsNullDetails() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("{\"status\":\"DOWN\"}")));

        // When
        HealthStatus result = client.health().health();

        // Then
        assertThat(result.status()).isEqualTo("DOWN");
        assertThat(result.details()).isNull();
    }

    @Test
    void healthAsync_returnsStatus() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/health"))
                .willReturn(okJson("{\"status\":\"UP\"}")));

        // When
        HealthStatus result = client.health().healthAsync().get();

        // Then
        assertThat(result.status()).isEqualTo("UP");
    }

    // --- status ---

    @Test
    void status_returnsDeserializedStatus() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/status"))
                .willReturn(okJson("""
                        {"status":"UP","version":"1.2.3","components":{"orders":{"status":"UP"}}}
                        """)));

        // When
        SystemStatus result = client.health().status();

        // Then
        assertThat(result.status()).isEqualTo("UP");
        assertThat(result.version()).isEqualTo("1.2.3");
        assertThat(result.components()).containsKey("orders");
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
                .willReturn(okJson("{\"status\":\"UP\",\"version\":\"2.0.0\"}")));

        // When
        SystemStatus result = client.health().statusAsync().get();

        // Then
        assertThat(result.status()).isEqualTo("UP");
        assertThat(result.version()).isEqualTo("2.0.0");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
