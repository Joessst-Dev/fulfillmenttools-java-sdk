package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class SourcingOptionsAsyncTest {

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
    void evaluateAsync_returnsResult() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/sourcingoptions"))
                .willReturn(okJson("{\"orderId\":\"o-1\",\"facilityRefs\":[\"fac-1\",\"fac-2\"]}")));

        // When
        SourcingOptionsResult result = client.sourcingOptions()
                .evaluateAsync(EvaluateSourcingOptionsRequest.builder().orderId("o-1").build()).get();

        // Then
        assertThat(result.orderId()).isEqualTo("o-1");
        assertThat(result.facilityRefs()).containsExactly("fac-1", "fac-2");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
