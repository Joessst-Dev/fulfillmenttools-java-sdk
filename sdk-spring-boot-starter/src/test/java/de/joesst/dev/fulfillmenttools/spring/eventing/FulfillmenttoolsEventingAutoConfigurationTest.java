package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class FulfillmenttoolsEventingAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(FulfillmenttoolsEventingAutoConfiguration.class));

    @Nested
    class WhenPubSubIsOnClasspath {

        @Test
        void shouldRegisterEventTypeRegistryAndDispatcherBeans() {
            // Given: a PubSubTemplate bean is present
            runner.withUserConfiguration(MockPubSubTemplateConfiguration.class)
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: both core eventing beans are registered
                        assertThat(context).hasSingleBean(FulfillmenttoolsEventTypeRegistry.class);
                        assertThat(context).hasSingleBean(FulfillmenttoolsEventDispatcher.class);
                    });
        }
    }

    @Nested
    class WhenSubscriptionsAreConfigured {

        @Test
        void shouldRegisterSubscriberManagerWithSingleSubscription() {
            // Given: PubSubTemplate is available and one subscription is configured
            runner.withUserConfiguration(MockPubSubTemplateWithSubscribeConfiguration.class)
                    .withPropertyValues(
                            "fulfillmenttools.eventing.subscriptions[0]=projects/p/subscriptions/s1")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: a subscriber manager bean is registered
                        assertThat(context).hasSingleBean(FulfillmenttoolsSubscriberManager.class);
                    });
        }

        @Test
        void shouldRegisterSubscriberManagerWithMultipleSubscriptions() {
            // Given: PubSubTemplate is available and two subscriptions are configured
            runner.withUserConfiguration(MockPubSubTemplateWithSubscribeConfiguration.class)
                    .withPropertyValues(
                            "fulfillmenttools.eventing.subscriptions[0]=projects/p/subscriptions/s1",
                            "fulfillmenttools.eventing.subscriptions[1]=projects/p/subscriptions/s2")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: exactly one manager bean handles both subscriptions
                        assertThat(context).hasSingleBean(FulfillmenttoolsSubscriberManager.class);
                    });
        }
    }

    @Nested
    class WhenSubscriptionsAreMissing {

        @Test
        void shouldNotRegisterSubscriberManagerBean() {
            // Given: PubSubTemplate is available but no subscriptions are configured
            runner.withUserConfiguration(MockPubSubTemplateConfiguration.class)
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: no subscriber manager is registered
                        assertThat(context).doesNotHaveBean(FulfillmenttoolsSubscriberManager.class);
                    });
        }
    }

    @Nested
    class WhenUserProvidesCustomRegistry {

        @Test
        void shouldUseUserRegistryAndNotRegisterDefault() {
            // Given: the user supplies their own FulfillmenttoolsEventTypeRegistry bean
            runner.withUserConfiguration(
                            MockPubSubTemplateConfiguration.class,
                            CustomRegistryConfiguration.class)
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: exactly one registry bean exists and it is the user's instance
                        assertThat(context).hasSingleBean(FulfillmenttoolsEventTypeRegistry.class);
                        assertThat(context.getBean(FulfillmenttoolsEventTypeRegistry.class))
                                .isSameAs(context.getBean(
                                        CustomRegistryConfiguration.CUSTOM_REGISTRY_BEAN_NAME,
                                        FulfillmenttoolsEventTypeRegistry.class));
                    });
        }
    }

    @Nested
    class WhenUserProvidesCustomEventHandler {

        @Test
        void shouldUseUserHandlerAndNotRegisterAnnotationDrivenDefault() {
            // Given: the user supplies their own FulfillmenttoolsEventHandler bean
            runner.withUserConfiguration(
                            MockPubSubTemplateConfiguration.class,
                            CustomEventHandlerConfiguration.class)
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: exactly one handler bean exists and it is the user's instance
                        assertThat(context).hasSingleBean(FulfillmenttoolsEventHandler.class);
                        assertThat(context.getBean(FulfillmenttoolsEventHandler.class))
                                .isSameAs(context.getBean(
                                        CustomEventHandlerConfiguration.CUSTOM_HANDLER_BEAN_NAME,
                                        FulfillmenttoolsEventHandler.class));
                    });
        }
    }

    @Configuration
    static class MockPubSubTemplateConfiguration {

        @Bean
        PubSubTemplate pubSubTemplate() {
            return Mockito.mock(PubSubTemplate.class);
        }
    }

    @Configuration
    static class MockPubSubTemplateWithSubscribeConfiguration {

        @Bean
        PubSubTemplate pubSubTemplate() {
            PubSubTemplate mock = Mockito.mock(PubSubTemplate.class);
            when(mock.subscribe(any(), any())).thenReturn(Mockito.mock(Subscriber.class));
            return mock;
        }
    }

    @Configuration
    static class CustomRegistryConfiguration {

        static final String CUSTOM_REGISTRY_BEAN_NAME = "customFulfillmenttoolsEventTypeRegistry";

        @Bean(CUSTOM_REGISTRY_BEAN_NAME)
        FulfillmenttoolsEventTypeRegistry customFulfillmenttoolsEventTypeRegistry() {
            FulfillmenttoolsEventTypeRegistry registry = new FulfillmenttoolsEventTypeRegistry();
            registry.register("CUSTOM_EVENT", String.class);
            return registry;
        }
    }

    @Configuration
    static class CustomEventHandlerConfiguration {

        static final String CUSTOM_HANDLER_BEAN_NAME = "customFulfillmenttoolsEventHandler";

        @Bean(CUSTOM_HANDLER_BEAN_NAME)
        FulfillmenttoolsEventHandler customFulfillmenttoolsEventHandler() {
            return mock(FulfillmenttoolsEventHandler.class);
        }
    }
}
