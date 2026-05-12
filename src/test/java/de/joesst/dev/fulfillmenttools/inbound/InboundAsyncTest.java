package de.joesst.dev.fulfillmenttools.inbound;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class InboundAsyncTest {

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
    void getAsync_returnsStowJob() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs/sj-1"))
                .willReturn(okJson("{\"id\":\"sj-1\",\"status\":\"OPEN\"}")));

        // When
        StowJob job = client.inbound().getAsync("sj-1").get();

        // Then
        assertThat(job.id()).isEqualTo("sj-1");
        assertThat(job.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("{\"stowJobs\":[{\"id\":\"sj-1\"},{\"id\":\"sj-2\"}]}")));

        // When
        var page = client.inbound().listAsync(StowJobListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedStowJob() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/stowjobs"))
                .willReturn(okJson("{\"id\":\"sj-new\",\"facilityRef\":\"fac-1\",\"status\":\"OPEN\"}")));

        // When
        StowJob job = client.inbound()
                .createAsync(CreateStowJobRequest.builder()
                        .facilityRef("fac-1")
                        .status("OPEN")
                        .stowLineItems(List.of())
                        .build()).get();

        // Then
        assertThat(job.id()).isEqualTo("sj-new");
        assertThat(job.facilityRef()).isEqualTo("fac-1");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
