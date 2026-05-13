package de.joesst.dev.fulfillmenttools.eventing;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

/**
 * BDD tests verifying that the polymorphic {@link SubscriptionTarget} sealed interface
 * is correctly deserialized from the JSON {@code type} discriminator field.
 */
class SubscriptionTargetDeserializationTest {

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

    // -------------------------------------------------------------------------
    // WebhookTarget
    // -------------------------------------------------------------------------

    @Nested
    class WhenTargetIsWebhook {

        @Test
        void get_deserializesWebhookTargetWithHeaders() {
            // Given
            server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-1"))
                    .willReturn(okJson("""
                            {
                              "id": "s-1",
                              "name": "order-hook",
                              "event": "ORDER_CREATED",
                              "created": "2024-01-01T00:00:00Z",
                              "target": {
                                "type": "WEBHOOK",
                                "callbackUrl": "https://example.com/hook",
                                "headers": [{"key": "X-Api-Key", "value": "secret"}]
                              }
                            }
                            """)));

            // When
            Subscription sub = client.eventing().get(new SubscriptionId("s-1"));

            // Then
            assertThat(sub.target()).isInstanceOf(WebhookTarget.class);
            WebhookTarget webhook = (WebhookTarget) sub.target();
            assertThat(webhook.type()).isEqualTo("WEBHOOK");
            assertThat(webhook.callbackUrl()).isEqualTo("https://example.com/hook");
            assertThat(webhook.headers()).hasSize(1);
            assertThat(webhook.headers().get(0).key()).isEqualTo("X-Api-Key");
            assertThat(webhook.headers().get(0).value()).isEqualTo("secret");
        }

        @Test
        void get_deserializesWebhookTargetWithoutHeaders() {
            // Given
            server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-2"))
                    .willReturn(okJson("""
                            {
                              "id": "s-2",
                              "name": "order-hook",
                              "event": "ORDER_CREATED",
                              "created": "2024-01-01T00:00:00Z",
                              "target": {
                                "type": "WEBHOOK",
                                "callbackUrl": "https://example.com/hook"
                              }
                            }
                            """)));

            // When
            Subscription sub = client.eventing().get(new SubscriptionId("s-2"));

            // Then
            assertThat(sub.target()).isInstanceOf(WebhookTarget.class);
            WebhookTarget webhook = (WebhookTarget) sub.target();
            assertThat(webhook.callbackUrl()).isEqualTo("https://example.com/hook");
            assertThat(webhook.headers()).isNull();
        }

        @Test
        void create_sendsWebhookTargetAsTypedJson() {
            // Given
            server.stubFor(post(urlPathEqualTo("/api/subscriptions"))
                    .willReturn(aResponse().withStatus(201)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\":\"s-new\"}")));

            // When
            client.eventing().create(CreateSubscriptionRequest.builder()
                    .name("order-hook")
                    .event("ORDER_CREATED")
                    .target(new WebhookTarget("WEBHOOK", "https://example.com/hook", null))
                    .build());

            // Then
            server.verify(postRequestedFor(urlPathEqualTo("/api/subscriptions"))
                    .withRequestBody(matchingJsonPath("$.target.type", equalTo("WEBHOOK")))
                    .withRequestBody(matchingJsonPath("$.target.callbackUrl", equalTo("https://example.com/hook"))));
        }
    }

    // -------------------------------------------------------------------------
    // AzureServiceBusTarget
    // -------------------------------------------------------------------------

    @Nested
    class WhenTargetIsAzureServiceBus {

        @Test
        void get_deserializesAzureServiceBusTarget() {
            // Given
            server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-3"))
                    .willReturn(okJson("""
                            {
                              "id": "s-3",
                              "name": "order-hook",
                              "event": "ORDER_CREATED",
                              "created": "2024-01-01T00:00:00Z",
                              "target": {
                                "type": "MICROSOFT_AZURE_SERVICE_BUS",
                                "tenantId": "tenant-1",
                                "clientId": "client-1",
                                "clientSecret": "secret-1",
                                "namespace": "my-namespace",
                                "queueOrTopicName": "my-queue"
                              }
                            }
                            """)));

            // When
            Subscription sub = client.eventing().get(new SubscriptionId("s-3"));

            // Then
            assertThat(sub.target()).isInstanceOf(AzureServiceBusTarget.class);
            AzureServiceBusTarget azure = (AzureServiceBusTarget) sub.target();
            assertThat(azure.type()).isEqualTo("MICROSOFT_AZURE_SERVICE_BUS");
            assertThat(azure.tenantId()).isEqualTo("tenant-1");
            assertThat(azure.clientId()).isEqualTo("client-1");
            assertThat(azure.clientSecret()).isEqualTo("secret-1");
            assertThat(azure.namespace()).isEqualTo("my-namespace");
            assertThat(azure.queueOrTopicName()).isEqualTo("my-queue");
        }

        @Test
        void create_sendsAzureServiceBusTargetAsTypedJson() {
            // Given
            server.stubFor(post(urlPathEqualTo("/api/subscriptions"))
                    .willReturn(aResponse().withStatus(201)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\":\"s-new\"}")));

            // When
            client.eventing().create(CreateSubscriptionRequest.builder()
                    .name("order-hook")
                    .event("ORDER_CREATED")
                    .target(new AzureServiceBusTarget(
                            "MICROSOFT_AZURE_SERVICE_BUS",
                            "tenant-1", "client-1", "secret-1",
                            "my-namespace", "my-queue"))
                    .build());

            // Then
            server.verify(postRequestedFor(urlPathEqualTo("/api/subscriptions"))
                    .withRequestBody(matchingJsonPath("$.target.type", equalTo("MICROSOFT_AZURE_SERVICE_BUS")))
                    .withRequestBody(matchingJsonPath("$.target.tenantId", equalTo("tenant-1")))
                    .withRequestBody(matchingJsonPath("$.target.queueOrTopicName", equalTo("my-queue"))));
        }
    }

    // -------------------------------------------------------------------------
    // GoogleCloudPubSubTarget
    // -------------------------------------------------------------------------

    @Nested
    class WhenTargetIsGoogleCloudPubSub {

        @Test
        void get_deserializesGoogleCloudPubSubTarget() {
            // Given
            server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-4"))
                    .willReturn(okJson("""
                            {
                              "id": "s-4",
                              "name": "order-hook",
                              "event": "ORDER_CREATED",
                              "created": "2024-01-01T00:00:00Z",
                              "target": {
                                "type": "GOOGLE_CLOUD_PUB_SUB",
                                "projectId": "my-project",
                                "topicId": "my-topic"
                              }
                            }
                            """)));

            // When
            Subscription sub = client.eventing().get(new SubscriptionId("s-4"));

            // Then
            assertThat(sub.target()).isInstanceOf(GoogleCloudPubSubTarget.class);
            GoogleCloudPubSubTarget pubSub = (GoogleCloudPubSubTarget) sub.target();
            assertThat(pubSub.type()).isEqualTo("GOOGLE_CLOUD_PUB_SUB");
            assertThat(pubSub.projectId()).isEqualTo("my-project");
            assertThat(pubSub.topicId()).isEqualTo("my-topic");
        }

        @Test
        void create_sendsGoogleCloudPubSubTargetAsTypedJson() {
            // Given
            server.stubFor(post(urlPathEqualTo("/api/subscriptions"))
                    .willReturn(aResponse().withStatus(201)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"id\":\"s-new\"}")));

            // When
            client.eventing().create(CreateSubscriptionRequest.builder()
                    .name("order-hook")
                    .event("ORDER_CREATED")
                    .target(new GoogleCloudPubSubTarget("GOOGLE_CLOUD_PUB_SUB", "my-project", "my-topic"))
                    .build());

            // Then
            server.verify(postRequestedFor(urlPathEqualTo("/api/subscriptions"))
                    .withRequestBody(matchingJsonPath("$.target.type", equalTo("GOOGLE_CLOUD_PUB_SUB")))
                    .withRequestBody(matchingJsonPath("$.target.projectId", equalTo("my-project")))
                    .withRequestBody(matchingJsonPath("$.target.topicId", equalTo("my-topic"))));
        }
    }

    // -------------------------------------------------------------------------
    // Null target (deprecated callbackUrl path)
    // -------------------------------------------------------------------------

    @Nested
    class WhenTargetIsAbsent {

        @Test
        void get_targetIsNullWhenNotPresentInResponse() {
            // Given
            server.stubFor(get(urlPathEqualTo("/api/subscriptions/s-5"))
                    .willReturn(okJson("""
                            {
                              "id": "s-5",
                              "name": "order-hook",
                              "event": "ORDER_CREATED",
                              "callbackUrl": "https://example.com/hook",
                              "created": "2024-01-01T00:00:00Z"
                            }
                            """)));

            // When
            Subscription sub = client.eventing().get(new SubscriptionId("s-5"));

            // Then
            assertThat(sub.target()).isNull();
            assertThat(sub.callbackUrl()).isEqualTo("https://example.com/hook");
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
