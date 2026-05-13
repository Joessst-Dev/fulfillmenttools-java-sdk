package de.joesst.dev.fulfillmenttools.packjobs;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class PackingAsyncTest {

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
    void getAsync_returnsPackJob() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"OPEN\"}")));

        // When
        PackJob job = client.packing().getAsync(new PackJobId("pj-1")).get();

        // Then
        assertThat(job.id()).isEqualTo("pj-1");
        assertThat(job.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/packjobs"))
                .willReturn(okJson("{\"packJobs\":[{\"id\":\"pj-1\"},{\"id\":\"pj-2\"}]}")));

        // When
        var page = client.packing().listAsync(PackJobListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void updateAsync_returnsUpdatedPackJob() throws Exception {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/packjobs/pj-1"))
                .willReturn(okJson("{\"id\":\"pj-1\",\"status\":\"IN_PROGRESS\"}")));

        // When
        PackJob job = client.packing()
                .updateAsync(new PackJobId("pj-1"), UpdatePackJobRequest.builder().version(1).status("IN_PROGRESS").build()).get();

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
