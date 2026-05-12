package de.joesst.dev.fulfillmenttools.checkoutoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class CheckoutOptionsAsyncTest {

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
    void getAsync_returnsCheckoutOption() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/promises/checkoutoptions/co-1"))
                .willReturn(okJson("{\"id\":\"co-1\",\"status\":\"AVAILABLE\"}")));

        // When
        CheckoutOption option = client.checkoutOptions().getAsync("co-1").get();

        // Then
        assertThat(option.id()).isEqualTo("co-1");
        assertThat(option.status()).isEqualTo("AVAILABLE");
    }

    @Test
    void evaluateAsync_returnsResult() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"id\":\"co-new\",\"status\":\"AVAILABLE\"}")));

        EvaluateCheckoutOptionsRequest request = EvaluateCheckoutOptionsRequest.builder()
                .deliveryPreferences(Map.of("type", "HOME_DELIVERY"))
                .orderLineItems(List.of(new CheckoutOrderLineItem("article-1", 1)))
                .build();

        // When
        CheckoutOption option = client.checkoutOptions().evaluateAsync(request).get();

        // Then
        assertThat(option.id()).isEqualTo("co-new");
        assertThat(option.status()).isEqualTo("AVAILABLE");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
