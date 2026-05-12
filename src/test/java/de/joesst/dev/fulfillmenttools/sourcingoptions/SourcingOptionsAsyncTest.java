package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
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
        server.stubFor(post(urlPathEqualTo("/api/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-1\",\"options\":[]}")));

        SourcingOptionsRequest request = SourcingOptionsRequest.builder()
                .order(OrderForSourcingOptionsRequest.builder()
                        .orderLineItems(List.of(new SourcingOrderLineItem("article-1", 2)))
                        .build())
                .build();

        // When
        SourcingOptionsResult result = client.sourcingOptions().evaluateAsync(request).get();

        // Then
        assertThat(result.id()).isEqualTo("run-1");
        assertThat(result.options()).isEmpty();
    }

    @Test
    void evaluateAsync_sendsOrderInBody() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/sourcingoptions"))
                .willReturn(okJson("{\"id\":\"run-2\",\"options\":[]}")));

        SourcingOptionsRequest request = SourcingOptionsRequest.builder()
                .order(OrderForSourcingOptionsRequest.builder()
                        .orderLineItems(List.of(new SourcingOrderLineItem("art-42", 1)))
                        .tenantOrderId("tenant-ord-1")
                        .build())
                .includeListingCustomAttributes(true)
                .build();

        // When
        client.sourcingOptions().evaluateAsync(request).get();

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/sourcingoptions"))
                .withHeader("Authorization", equalTo("Bearer test-bearer"))
                .withRequestBody(matchingJsonPath("$.order.tenantOrderId", equalTo("tenant-ord-1")))
                .withRequestBody(matchingJsonPath("$.includeListingCustomAttributes", equalTo("true"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
