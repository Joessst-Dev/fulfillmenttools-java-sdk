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

class FulfillmenttoolsEventingAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(FulfillmenttoolsEventingAutoConfiguration.class));

    @Nested
    class WhenPubSubIsOnClasspath {

        @Test
        void shouldRegisterEventTypeRegistryAndDispatcherBeans() {
            // Given: a PubSubTemplate bean is present (simulates PubSub on classpath + context)
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
    class WhenSubscriptionPropertyIsConfigured {

        @Test
        void shouldRegisterSubscriberBean() {
            // Given: PubSubTemplate is available and the subscription property is set
            runner.withUserConfiguration(MockPubSubTemplateWithSubscribeConfiguration.class)
                    .withPropertyValues(
                            "fulfillmenttools.eventing.subscription=projects/p/subscriptions/s")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: a subscriber bean is registered
                        assertThat(context).hasBean("fulfillmenttoolsPubSubSubscriber");
                    });
        }
    }

    @Nested
    class WhenSubscriptionPropertyIsMissing {

        @Test
        void shouldNotRegisterSubscriberBean() {
            // Given: PubSubTemplate is available but no subscription property is set
            runner.withUserConfiguration(MockPubSubTemplateConfiguration.class)
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: no subscriber bean is registered
                        assertThat(context).doesNotHaveBean("fulfillmenttoolsPubSubSubscriber");
                        assertThat(context).doesNotHaveBean(Subscriber.class);
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
}
