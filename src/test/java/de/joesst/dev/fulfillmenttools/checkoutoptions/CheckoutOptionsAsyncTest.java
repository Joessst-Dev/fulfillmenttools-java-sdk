package de.joesst.dev.fulfillmenttools.checkoutoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;
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
    void evaluateAsync_returnsResult() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"facilities\":[{\"facilityRef\":\"fac-1\",\"name\":\"Facility 1\"}]}")));

        EvaluateCheckoutOptionsRequest request = EvaluateCheckoutOptionsRequest.builder()
                .deliveryPreferences(homeDelivery())
                .orderLineItems(List.of(Map.of("tenantArticleId", "article-1", "quantity", 1)))
                .build();

        // When
        CheckoutOption option = client.checkoutOptions().evaluateAsync(request).get();

        // Then
        assertThat(option.facilities()).hasSize(1);
        assertThat(option.facilities().get(0)).containsEntry("facilityRef", "fac-1");
    }

    @Test
    void evaluateAsync_sendsBodyFields() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"facilities\":[]}")));

        EvaluateCheckoutOptionsRequest request = EvaluateCheckoutOptionsRequest.builder()
                .deliveryPreferences(homeDelivery())
                .orderLineItems(List.of(Map.of("tenantArticleId", "art-1", "quantity", 2)))
                .filterDuplicates(true)
                .consumerAddress(CheckoutOptionsConsumerAddress.ofCountry("DE"))
                .build();

        // When
        client.checkoutOptions().evaluateAsync(request).get();

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/promises/checkoutoptions"))
                .withHeader("Authorization", equalTo("Bearer test-bearer"))
                .withRequestBody(matchingJsonPath("$.filterDuplicates", equalTo("true")))
                .withRequestBody(matchingJsonPath("$.consumerAddress.country", equalTo("DE"))));
    }

    // --- Helpers ---

    private static DeliveryPreferences homeDelivery() {
        return new DeliveryPreferences(null, null, null, null, null, null);
    }

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
