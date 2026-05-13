package de.joesst.dev.fulfillmenttools.eventing;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class EventingClientTest {

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

    // --- get ---

    @Test
    void get_returnsDeserializedSubscription() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-1"))
                .willReturn(okJson("""
                        {
                          "id": "s-1",
                          "name": "order-hook",
                          "event": "ORDER_CREATED",
                          "callbackUrl": "https://example.com/hook",
                          "created": "2024-01-01T00:00:00Z"
                        }
                        """)));

        // When
        Subscription sub = client.eventing().get(new SubscriptionId("s-1"));

        // Then
        assertThat(sub.id().value()).isEqualTo("s-1");
        assertThat(sub.name()).isEqualTo("order-hook");
        assertThat(sub.event()).isEqualTo("ORDER_CREATED");
        assertThat(sub.callbackUrl()).isEqualTo("https://example.com/hook");
        assertThat(sub.created()).isNotNull();
    }

    @Test
    void get_deserializesContextsAndHeaders() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-1"))
                .willReturn(okJson("""
                        {
                          "id": "s-1",
                          "name": "order-hook",
                          "event": "ORDER_CREATED",
                          "contexts": [{"type":"FACILITY","values":["fac-1"]}],
                          "headers": [{"key":"Authorization","value":"Bearer token"}],
                          "created": "2024-01-01T00:00:00Z"
                        }
                        """)));

        // When
        Subscription sub = client.eventing().get(new SubscriptionId("s-1"));

        // Then
        assertThat(sub.contexts()).hasSize(1);
        assertThat(sub.contexts().get(0).type()).isEqualTo("FACILITY");
        assertThat(sub.contexts().get(0).values()).containsExactly("fac-1");
        assertThat(sub.headers()).hasSize(1);
        assertThat(sub.headers().get(0).key()).isEqualTo("Authorization");
        assertThat(sub.headers().get(0).value()).isEqualTo("Bearer token");
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-1"))
                .willReturn(okJson("{\"id\":\"s-1\"}")));

        // When
        client.eventing().get(new SubscriptionId("s-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/subscriptions/s-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Subscription not found","description":"No subscription with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.eventing().get(new SubscriptionId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Subscription not found");
    }

    // --- list ---

    @Test
    void list_returnsPageFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions"))
                .willReturn(okJson("""
                        {
                          "subscriptions": [
                            {"id":"s-1","event":"ORDER_CREATED"},
                            {"id":"s-2","event":"ORDER_UPDATED"}
                          ],
                          "total": 2
                        }
                        """)));

        // When
        Page<Subscription> page = client.eventing().list(SubscriptionListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("s-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions"))
                .willReturn(okJson("{\"subscriptions\":[], \"total\":0}")));

        // When
        client.eventing().list(SubscriptionListRequest.builder()
                .size(10)
                .startAfterId("cursor-abc")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/subscriptions"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/subscriptions"))
                .willReturn(okJson("{\"subscriptions\":[{\"id\":\"s-1\"},{\"id\":\"s-2\"}],\"total\":2}")));

        // When
        List<String> ids = new ArrayList<>();
        for (Subscription s : client.eventing().listAll(SubscriptionListRequest.builder().build())) {
            ids.add(s.id().value());
        }

        // Then
        assertThat(ids).containsExactly("s-1", "s-2");
    }

    // --- create ---

    @Test
    void create_returnsCreatedSubscription() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/subscriptions"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"s-new\",\"name\":\"order-hook\",\"event\":\"ORDER_CREATED\",\"callbackUrl\":\"https://example.com/hook\"}")));

        // When
        Subscription sub = client.eventing().create(CreateSubscriptionRequest.builder()
                .name("order-hook")
                .event("ORDER_CREATED")
                .callbackUrl("https://example.com/hook")
                .build());

        // Then
        assertThat(sub.id().value()).isEqualTo("s-new");
        assertThat(sub.event()).isEqualTo("ORDER_CREATED");
    }

    @Test
    void create_sendsNameAndEvent() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/subscriptions"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"s-new\"}")));

        // When
        client.eventing().create(CreateSubscriptionRequest.builder()
                .name("order-hook")
                .event("ORDER_CREATED")
                .callbackUrl("https://example.com/hook")
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/subscriptions"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("order-hook")))
                .withRequestBody(matchingJsonPath("$.event", equalTo("ORDER_CREATED")))
                .withRequestBody(matchingJsonPath("$.callbackUrl", equalTo("https://example.com/hook"))));
    }

    @Test
    void create_requiresName() {
        assertThatThrownBy(() -> CreateSubscriptionRequest.builder()
                .event("ORDER_CREATED").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("name");
    }

    @Test
    void create_requiresEvent() {
        assertThatThrownBy(() -> CreateSubscriptionRequest.builder()
                .name("hook").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("event");
    }

    // --- delete ---

    @Test
    void delete_sends200() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/subscriptions/s-1"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"s-1\"}")));

        // When / Then
        assertThatCode(() -> client.eventing().delete(new SubscriptionId("s-1")))
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
