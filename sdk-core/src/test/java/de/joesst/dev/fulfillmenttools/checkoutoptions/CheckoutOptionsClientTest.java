package de.joesst.dev.fulfillmenttools.checkoutoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class CheckoutOptionsClientTest {

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

    // --- evaluate ---

    @Test
    void evaluate_returnsDeserializedFacilities() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("""
                        {
                          "facilities": [
                            {"facilityRef": "fac-1", "name": "Store Berlin"},
                            {"facilityRef": "fac-2", "name": "Store Munich"}
                          ]
                        }
                        """)));

        // When
        CheckoutOption option = client.checkoutOptions().evaluate(
                EvaluateCheckoutOptionsRequest.builder()
                        .deliveryPreferences(homeDelivery())
                        .orderLineItems(List.of(Map.of("tenantArticleId", "art-1", "quantity", 1)))
                        .build());

        // Then
        assertThat(option.facilities()).hasSize(2);
        assertThat(option.facilities().get(0)).containsEntry("facilityRef", "fac-1");
        assertThat(option.facilities().get(1)).containsEntry("name", "Store Munich");
    }

    @Test
    void evaluate_sendsBearerTokenHeader() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"facilities\":[]}")));

        // When
        client.checkoutOptions().evaluate(
                EvaluateCheckoutOptionsRequest.builder()
                        .deliveryPreferences(clickAndCollect())
                        .orderLineItems(List.of(Map.of("tenantArticleId", "art-1", "quantity", 1)))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/promises/checkoutoptions"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void evaluate_sendsDeliveryPreferencesAndOrderLineItems() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"facilities\":[]}")));

        // When
        client.checkoutOptions().evaluate(
                EvaluateCheckoutOptionsRequest.builder()
                        .deliveryPreferences(homeDelivery())
                        .orderLineItems(List.of(
                                Map.of("tenantArticleId", "art-1", "quantity", 2),
                                Map.of("tenantArticleId", "art-2", "quantity", 1)))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/promises/checkoutoptions"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.orderLineItems[0].tenantArticleId", equalTo("art-1")))
                .withRequestBody(matchingJsonPath("$.orderLineItems[1].tenantArticleId", equalTo("art-2"))));
    }

    @Test
    void evaluate_sendsOptionalFields() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"facilities\":[]}")));

        // When
        client.checkoutOptions().evaluate(
                EvaluateCheckoutOptionsRequest.builder()
                        .deliveryPreferences(clickAndCollect())
                        .orderLineItems(List.of(Map.of("tenantArticleId", "art-1", "quantity", 1)))
                        .consumerAddress(CheckoutOptionsConsumerAddress.builder()
                                .country("DE")
                                .city("Berlin")
                                .build())
                        .filterDuplicates(true)
                        .customAttributes(new CustomAttributes(Map.of("source", "web")))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/promises/checkoutoptions"))
                .withRequestBody(matchingJsonPath("$.consumerAddress.country", equalTo("DE")))
                .withRequestBody(matchingJsonPath("$.filterDuplicates", equalTo("true")))
                .withRequestBody(matchingJsonPath("$.customAttributes.source", equalTo("web"))));
    }

    @Test
    void evaluate_sendsGeoFenceAndTags() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/promises/checkoutoptions"))
                .willReturn(okJson("{\"facilities\":[]}")));

        // When
        client.checkoutOptions().evaluate(
                EvaluateCheckoutOptionsRequest.builder()
                        .deliveryPreferences(homeDelivery())
                        .orderLineItems(List.of(Map.of("tenantArticleId", "art-1", "quantity", 1)))
                        .geoFence(new GeoFence(52.52, 13.405, 50))
                        .tags(List.of(Map.of("value", "express")))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/promises/checkoutoptions"))
                .withRequestBody(matchingJsonPath("$.geoFence.radius", equalTo("50.0")))
                .withRequestBody(matchingJsonPath("$.tags[0].value", equalTo("express"))));
    }

    @Test
    void evaluate_requiresDeliveryPreferences() {
        assertThatThrownBy(() -> EvaluateCheckoutOptionsRequest.builder()
                .orderLineItems(List.of(Map.of("tenantArticleId", "art-1", "quantity", 1)))
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("deliveryPreferences");
    }

    @Test
    void evaluate_requiresOrderLineItems() {
        assertThatThrownBy(() -> EvaluateCheckoutOptionsRequest.builder()
                .deliveryPreferences(homeDelivery())
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("orderLineItems");
    }

    // --- Helpers ---

    private static DeliveryPreferences homeDelivery() {
        return new DeliveryPreferences(null, null, null, null, null, null);
    }

    private static DeliveryPreferences clickAndCollect() {
        return new DeliveryPreferences(null, null, null, null, null, null);
    }

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
