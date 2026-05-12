package de.joesst.dev.fulfillmenttools.handoverjobs;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class HandoverJobsAsyncTest {

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
    void getAsync_returnsHandoverJob() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .willReturn(okJson("{\"id\":\"hj-1\",\"status\":\"OPEN\"}")));

        // When
        HandoverJob job = client.handoverJobs().getAsync("hj-1").get();

        // Then
        assertThat(job.id()).isEqualTo("hj-1");
        assertThat(job.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/handoverjobs"))
                .willReturn(okJson("{\"handoverJobs\":[{\"id\":\"hj-1\"},{\"id\":\"hj-2\"}]}")));

        // When
        var page = client.handoverJobs().listAsync(HandoverJobListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void updateAsync_returnsUpdatedHandoverJob() throws Exception {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/handoverjobs/hj-1"))
                .willReturn(okJson("{\"id\":\"hj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        HandoverJob job = client.handoverJobs()
                .updateAsync("hj-1", UpdateHandoverJobRequest.builder().status("IN_PROGRESS").build()).get();

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
