package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import de.joesst.dev.fulfillmenttools.spring.FulfillmenttoolsAutoConfiguration;
import de.joesst.dev.fulfillmenttools.spring.FulfillmenttoolsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot auto-configuration for fulfillmenttools GCP Pub/Sub event receiving.
 *
 * <p>Activates when {@code com.google.cloud.spring.pubsub.core.PubSubTemplate} is on the
 * classpath. Registers:
 * <ul>
 *   <li>{@link FulfillmenttoolsEventTypeRegistry} — default event-type-to-entity mappings,
 *       user-extensible via {@code @ConditionalOnMissingBean}.</li>
 *   <li>{@link FulfillmenttoolsEventDispatcher} — parses and publishes events.</li>
 *   <li>A {@link Subscriber} named {@code fulfillmenttoolsPubSubSubscriber} — subscribes to
 *       the configured Pub/Sub subscription and feeds messages to the dispatcher. Only
 *       registered when {@code fulfillmenttools.eventing.subscription} is set.</li>
 * </ul>
 *
 * <p>Example {@code application.properties}:
 * <pre>{@code
 * fulfillmenttools.eventing.subscription=projects/my-project/subscriptions/my-subscription
 * }</pre>
 */
@AutoConfiguration(after = FulfillmenttoolsAutoConfiguration.class)
@ConditionalOnClass(PubSubTemplate.class)
@EnableConfigurationProperties(FulfillmenttoolsProperties.class)
public class FulfillmenttoolsEventingAutoConfiguration {

    private static final Logger log =
            LoggerFactory.getLogger(FulfillmenttoolsEventingAutoConfiguration.class);

    /**
     * Registers the default event type registry.
     *
     * <p>Applications can supply a custom {@link FulfillmenttoolsEventTypeRegistry} bean
     * to override or extend the defaults.
     *
     * @return a pre-populated {@link FulfillmenttoolsEventTypeRegistry}
     */
    @Bean
    @ConditionalOnMissingBean
    public FulfillmenttoolsEventTypeRegistry fulfillmenttoolsEventTypeRegistry() {
        return new FulfillmenttoolsEventTypeRegistry();
    }

    /**
     * Registers the event dispatcher with a correctly configured {@link ObjectMapper}.
     *
     * <p>The mapper configuration matches {@code JsonCodec.defaultMapper()} in {@code sdk-core}:
     * JavaTime support, no unknown-property failures, no timestamp serialization, field-only
     * visibility.
     *
     * @param registry       the event type registry
     * @param eventPublisher the Spring event publisher
     * @return a configured {@link FulfillmenttoolsEventDispatcher}
     */
    @Bean
    @ConditionalOnMissingBean
    public FulfillmenttoolsEventDispatcher fulfillmenttoolsEventDispatcher(
            FulfillmenttoolsEventTypeRegistry registry,
            ApplicationEventPublisher eventPublisher) {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new FulfillmenttoolsEventDispatcher(mapper, registry, eventPublisher);
    }

    /**
     * Registers a GCP Pub/Sub {@link Subscriber} that feeds raw messages to the
     * {@link FulfillmenttoolsEventDispatcher}.
     *
     * <p>Only registered when {@code fulfillmenttools.eventing.subscription} is set and no
     * bean named {@code fulfillmenttoolsPubSubSubscriber} has been declared by the application.
     *
     * <p>On successful dispatch the message is acknowledged. On any exception the error is
     * logged and the message is negatively acknowledged so it can be redelivered.
     *
     * @param pubSubTemplate the GCP Pub/Sub template used to subscribe
     * @param dispatcher     the event dispatcher
     * @param properties     the SDK configuration properties (supplies the subscription name)
     * @return a running {@link Subscriber} instance
     */
    @Bean(name = "fulfillmenttoolsPubSubSubscriber", destroyMethod = "stopAsync")
    @ConditionalOnMissingBean(name = "fulfillmenttoolsPubSubSubscriber")
    @ConditionalOnProperty(prefix = "fulfillmenttools.eventing", name = "subscription")
    public Subscriber fulfillmenttoolsPubSubSubscriber(
            PubSubTemplate pubSubTemplate,
            FulfillmenttoolsEventDispatcher dispatcher,
            FulfillmenttoolsProperties properties) {
        String subscription = properties.getEventing().getSubscription();
        return pubSubTemplate.subscribe(subscription, message -> {
            try {
                dispatcher.dispatch(message.getPubsubMessage().getData().toByteArray());
                message.ack();
            } catch (Exception e) {
                log.error("Failed to dispatch fulfillmenttools event from subscription '{}': {}",
                        subscription, e.getMessage(), e);
                message.nack();
            }
        });
    }
}
