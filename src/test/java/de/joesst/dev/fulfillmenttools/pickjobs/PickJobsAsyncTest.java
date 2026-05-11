package de.joesst.dev.fulfillmenttools.pickjobs;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class PickJobsAsyncTest {

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
    void getAsync_returnsPickJob() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"OPEN\"}")));

        // When
        PickJob job = client.pickJobs().getAsync("pj-1").get();

        // Then
        assertThat(job.id()).isEqualTo("pj-1");
        assertThat(job.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/pickjobs"))
                .willReturn(okJson("{\"pickJobs\":[{\"id\":\"pj-1\"},{\"id\":\"pj-2\"}]}")));

        // When
        var page = client.pickJobs().listAsync(PickJobListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void updateAsync_returnsUpdatedPickJob() throws Exception {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/pickjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        PickJob job = client.pickJobs()
                .updateAsync("pj-1", UpdatePickJobRequest.builder().status("IN_PROGRESS").build()).get();

        // Then
        assertThat(job.status()).isEqualTo("IN_PROGRESS");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
