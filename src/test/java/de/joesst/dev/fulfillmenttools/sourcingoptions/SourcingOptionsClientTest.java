package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticleForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class SourcingOptionsClientTest {

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
    void evaluate_returnsDeserializedResult() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("""
                        {
                          "id": "run-1",
                          "result": {
                            "options": [
                              {
                                "id": "opt-1",
                                "runId": "run-1",
                                "totalPenalty": 42.5,
                                "nodes": [],
                                "transfers": [],
                                "ratingResults": []
                              }
                            ]
                          }
                        }
                        """)));

        // When
        SourcingOptionsResult result = client.sourcingOptions().evaluate(
                SourcingOptionsRequest.builder()
                        .order(OrderForSourcingOptionsRequest.builder()
                                .consumer(ConsumerAddressesForSourcingOptions.builder().consumerId("c-1").build())
                                .build())
                        .build());

        // Then
        assertThat(result.id()).isEqualTo("run-1");
        assertThat(result.options()).hasSize(1);
        assertThat(result.options().get(0).id()).isEqualTo("opt-1");
        assertThat(result.options().get(0).totalPenalty()).isEqualTo(42.5);
    }

    @Test
    void evaluate_sendsBearerTokenHeader() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[]}}")));

        // When
        client.sourcingOptions().evaluate(
                SourcingOptionsRequest.builder()
                        .order(OrderForSourcingOptionsRequest.builder()
                                .consumer(ConsumerAddressesForSourcingOptions.builder().build())
                                .build())
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void evaluate_sendsOrderInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[]}}")));

        OrderLineItemForCreation lineItem = OrderLineItemForCreation.builder()
                .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-1").build())
                .quantity(2)
                .build();

        // When
        client.sourcingOptions().evaluate(
                SourcingOptionsRequest.builder()
                        .order(OrderForSourcingOptionsRequest.builder()
                                .consumer(ConsumerAddressesForSourcingOptions.builder()
                                        .consumerId("consumer-99")
                                        .build())
                                .tenantOrderId("order-abc")
                                .orderLineItems(List.of(lineItem))
                                .build())
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.order.consumer.consumerId", equalTo("consumer-99")))
                .withRequestBody(matchingJsonPath("$.order.tenantOrderId", equalTo("order-abc")))
                .withRequestBody(matchingJsonPath("$.order.orderLineItems[0].article.tenantArticleId", equalTo("art-1"))));
    }

    @Test
    void evaluate_sendsOptimizationHintsAndAdditionalInfo() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[]}}")));

        // When
        client.sourcingOptions().evaluate(
                SourcingOptionsRequest.builder()
                        .order(OrderForSourcingOptionsRequest.builder()
                                .consumer(ConsumerAddressesForSourcingOptions.builder().build())
                                .build())
                        .optimizationHints(new OptimizationHints(true, 5))
                        .additionalInfo(new SourcingOptionsRequestAdditionalInfo(true))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions"))
                .withRequestBody(matchingJsonPath("$.optimizationHints.includeCalculationHints", equalTo("true")))
                .withRequestBody(matchingJsonPath("$.optimizationHints.requestedResultCount", equalTo("5")))
                .withRequestBody(matchingJsonPath("$.additionalInfo.includeListingCustomAttributes", equalTo("true"))));
    }

    @Test
    void evaluate_sendsResourceInvestment() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[]}}")));

        // When
        client.sourcingOptions().evaluate(
                SourcingOptionsRequest.builder()
                        .order(OrderForSourcingOptionsRequest.builder()
                                .consumer(ConsumerAddressesForSourcingOptions.builder().build())
                                .build())
                        .resourceInvestment(ResourceInvestment.builder().level(0.5).build())
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions"))
                .withRequestBody(matchingJsonPath("$.resourceInvestment.level", equalTo("0.5"))));
    }

    @Test
    void evaluate_requiresOrder() {
        assertThatThrownBy(() -> SourcingOptionsRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("order");
    }

    // --- get ---

    @Test
    void get_returnsDeserializedResult() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/sourcingoptions/run-42"))
                .willReturn(okJson("{\"id\":\"run-42\",\"result\":{\"options\":[{\"id\":\"opt-1\",\"runId\":\"run-42\",\"totalPenalty\":10.0,\"nodes\":[],\"transfers\":[],\"ratingResults\":[]}]}}")));

        // When
        SourcingOptionsResult result = client.sourcingOptions().get(new SourcingOptionsRequestId("run-42"));

        // Then
        assertThat(result.id()).isEqualTo("run-42");
        assertThat(result.options()).hasSize(1);
        assertThat(result.options().get(0).runId()).isEqualTo("run-42");
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/sourcingoptions/run-1"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[]}}")));

        // When
        client.sourcingOptions().get(new SourcingOptionsRequestId("run-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions/run-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
