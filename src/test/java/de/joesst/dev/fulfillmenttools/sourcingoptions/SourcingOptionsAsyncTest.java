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
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[]}}")));

        ConsumerAddressesForSourcingOptions consumer = ConsumerAddressesForSourcingOptions.builder()
                .consumerId("consumer-1")
                .build();

        SourcingOptionsRequest request = SourcingOptionsRequest.builder()
                .order(OrderForSourcingOptionsRequest.builder()
                        .consumer(consumer)
                        .build())
                .build();

        // When
        SourcingOptionsResult result = client.sourcingOptions().evaluateAsync(request).get();

        // Then
        assertThat(result.id()).isEqualTo("run-1");
        assertThat(result.options()).isEmpty();
    }

    @Test
    void evaluateAsync_sendsFullRequestInBody() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-2\",\"result\":{\"options\":[]}}")));

        ConsumerAddressesForSourcingOptions consumer = ConsumerAddressesForSourcingOptions.builder()
                .consumerId("consumer-42")
                .build();

        OrderLineItemForCreation lineItem = OrderLineItemForCreation.builder()
                .article(OrderLineItemArticleForCreation.builder().tenantArticleId("art-42").build())
                .quantity(3)
                .build();

        SourcingOptionsRequest request = SourcingOptionsRequest.builder()
                .order(OrderForSourcingOptionsRequest.builder()
                        .consumer(consumer)
                        .orderLineItems(List.of(lineItem))
                        .tenantOrderId("tenant-ord-1")
                        .build())
                .additionalInfo(new SourcingOptionsRequestAdditionalInfo(true))
                .optimizationHints(new OptimizationHints(false, 3))
                .build();

        // When
        client.sourcingOptions().evaluateAsync(request).get();

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions"))
                .withHeader("Authorization", equalTo("Bearer test-bearer"))
                .withRequestBody(matchingJsonPath("$.order.tenantOrderId", equalTo("tenant-ord-1")))
                .withRequestBody(matchingJsonPath("$.order.consumer.consumerId", equalTo("consumer-42")))
                .withRequestBody(matchingJsonPath("$.additionalInfo.includeListingCustomAttributes", equalTo("true")))
                .withRequestBody(matchingJsonPath("$.optimizationHints.requestedResultCount", equalTo("3"))));
    }

    @Test
    void evaluateAsync_sendsResourceInvestment() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/routing/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-3\",\"result\":{\"options\":[]}}")));

        ConsumerAddressesForSourcingOptions consumer = ConsumerAddressesForSourcingOptions.builder().build();

        SourcingOptionsRequest request = SourcingOptionsRequest.builder()
                .order(OrderForSourcingOptionsRequest.builder()
                        .consumer(consumer)
                        .build())
                .resourceInvestment(ResourceInvestment.builder().level(0.8).build())
                .build();

        // When
        client.sourcingOptions().evaluateAsync(request).get();

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/routing/sourcingoptions"))
                .withRequestBody(matchingJsonPath("$.resourceInvestment.level", equalTo("0.8"))));
    }

    @Test
    void getAsync_returnsResult() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/routing/sourcingoptions/run-1"))
                .willReturn(okJson("{\"id\":\"run-1\",\"result\":{\"options\":[{\"id\":\"opt-1\",\"runId\":\"run-1\",\"totalPenalty\":5.0,\"nodes\":[],\"transfers\":[],\"ratingResults\":[]}]}}")));

        // When
        SourcingOptionsResult result = client.sourcingOptions().getAsync(new SourcingOptionsRequestId("run-1")).get();

        // Then
        assertThat(result.id()).isEqualTo("run-1");
        assertThat(result.options()).hasSize(1);
        assertThat(result.options().get(0).id()).isEqualTo("opt-1");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
