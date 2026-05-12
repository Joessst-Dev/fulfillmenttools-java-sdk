package de.joesst.dev.fulfillmenttools.processes;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class OperativeProcessAsyncTest {

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
    void getAsync_returnsProcess() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes/proc-1"))
                .willReturn(okJson("{\"id\":\"proc-1\",\"status\":\"OPEN\",\"gdprCleanupDate\":\"2025-01-01T00:00:00Z\"}")));

        // When
        Process process = client.processes().getAsync("proc-1").get();

        // Then
        assertThat(process.id()).isEqualTo("proc-1");
        assertThat(process.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/processes"))
                .willReturn(okJson("{\"processes\":[{\"id\":\"proc-1\"},{\"id\":\"proc-2\"}],\"total\":2}")));

        // When
        Page<Process> page = client.processes().listAsync(ProcessListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void searchAsync_returnsMatchingProcesses() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/processes/search"))
                .willReturn(okJson("{\"processes\":[{\"id\":\"proc-1\",\"status\":\"OPEN\",\"facilityRefs\":[\"fac-1\"]}]}")));

        // When
        Page<Process> page = client.processes()
                .searchAsync(ProcessSearchRequest.builder()
                        .query(ProcessSearchQuery.builder()
                                .facilityRefsHasAny("fac-1")
                                .statusIn("OPEN")
                                .build())
                        .build()).get();

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).facilityRefs()).containsExactly("fac-1");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
