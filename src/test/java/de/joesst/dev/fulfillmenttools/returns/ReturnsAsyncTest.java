package de.joesst.dev.fulfillmenttools.returns;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ReturnsAsyncTest {

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
    void getAsync_returnsReturn() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/returns/ret-1"))
                .willReturn(okJson("{\"id\":\"ret-1\",\"status\":\"OPEN\"}")));

        // When
        Return ret = client.returns().getAsync("ret-1").get();

        // Then
        assertThat(ret.id()).isEqualTo("ret-1");
        assertThat(ret.status()).isEqualTo("OPEN");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/returns"))
                .willReturn(okJson("{\"returns\":[{\"id\":\"ret-1\"},{\"id\":\"ret-2\"}]}")));

        // When
        var page = client.returns().listAsync(ReturnListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedReturn() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/returns"))
                .willReturn(okJson("{\"id\":\"ret-new\",\"facilityRef\":\"fac-1\",\"status\":\"OPEN\"}")));

        // When
        Return ret = client.returns()
                .createAsync(CreateReturnRequest.builder().facilityRef("fac-1").build()).get();

        // Then
        assertThat(ret.id()).isEqualTo("ret-new");
        assertThat(ret.facilityRef()).isEqualTo("fac-1");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
