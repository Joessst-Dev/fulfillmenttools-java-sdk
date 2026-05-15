package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import de.joesst.dev.fulfillmenttools.spring.FulfillmenttoolsAutoConfiguration;
import de.joesst.dev.fulfillmenttools.spring.FulfillmenttoolsProperties;
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
 *   <li>{@link FulfillmenttoolsSubscriberManager} — starts one {@code Subscriber} per
 *       configured subscription when {@code fulfillmenttools.eventing.subscriptions[0]}
 *       is present.</li>
 * </ul>
 *
 * <p>Example {@code application.yml}:
 * <pre>{@code
 * fulfillmenttools:
 *   eventing:
 *     subscriptions:
 *       - projects/my-project/subscriptions/orders-sub
 *       - projects/my-project/subscriptions/stocks-sub
 * }</pre>
 */
@AutoConfiguration(after = FulfillmenttoolsAutoConfiguration.class)
@ConditionalOnClass(PubSubTemplate.class)
@EnableConfigurationProperties(FulfillmenttoolsProperties.class)
public class FulfillmenttoolsEventingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FulfillmenttoolsEventTypeRegistry fulfillmenttoolsEventTypeRegistry() {
        return new FulfillmenttoolsEventTypeRegistry();
    }

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
     * Registers a {@link FulfillmenttoolsSubscriberManager} that starts one GCP Pub/Sub
     * subscriber per configured subscription. Only registered when at least one subscription
     * is present ({@code fulfillmenttools.eventing.subscriptions[0]}).
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "fulfillmenttools.eventing", name = "subscriptions[0]")
    public FulfillmenttoolsSubscriberManager fulfillmenttoolsSubscriberManager(
            PubSubTemplate pubSubTemplate,
            FulfillmenttoolsEventDispatcher dispatcher,
            FulfillmenttoolsProperties properties) {
        return new FulfillmenttoolsSubscriberManager(
                pubSubTemplate,
                properties.getEventing().getSubscriptions(),
                dispatcher);
    }
}
