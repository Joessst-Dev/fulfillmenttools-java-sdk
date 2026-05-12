package de.joesst.dev.fulfillmenttools.eventing;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class EventingAsyncTest {

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
    void getAsync_returnsSubscription() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-1"))
                .willReturn(okJson("{\"id\":\"s-1\",\"topic\":\"ORDER_CREATED\",\"callbackUrl\":\"https://example.com/hook\",\"status\":\"ACTIVE\"}")));

        // When
        Subscription subscription = client.eventing().getAsync("s-1").get();

        // Then
        assertThat(subscription.id()).isEqualTo("s-1");
        assertThat(subscription.topic()).isEqualTo("ORDER_CREATED");
        assertThat(subscription.callbackUrl()).isEqualTo("https://example.com/hook");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions"))
                .willReturn(okJson("{\"subscriptions\":[{\"id\":\"s-1\",\"topic\":\"ORDER_CREATED\"},{\"id\":\"s-2\",\"topic\":\"ORDER_UPDATED\"}]}")));

        // When
        var page = client.eventing().listAsync(SubscriptionListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedSubscription() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/subscriptions"))
                .willReturn(okJson("{\"id\":\"s-new\",\"topic\":\"ORDER_CREATED\",\"callbackUrl\":\"https://example.com/hook\",\"status\":\"ACTIVE\"}")));

        // When
        Subscription subscription = client.eventing()
                .createAsync(CreateSubscriptionRequest.builder()
                        .topic("ORDER_CREATED")
                        .callbackUrl("https://example.com/hook")
                        .build()).get();

        // Then
        assertThat(subscription.id()).isEqualTo("s-new");
        assertThat(subscription.topic()).isEqualTo("ORDER_CREATED");
    }

    @Test
    void deleteAsync_completesWithoutException() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/subscriptions/s-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.eventing().deleteAsync("s-1").get())
                .doesNotThrowAnyException();
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
