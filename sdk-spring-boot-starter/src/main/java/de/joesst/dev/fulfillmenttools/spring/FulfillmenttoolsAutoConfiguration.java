package de.joesst.dev.fulfillmenttools.spring;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot auto-configuration for the fulfillmenttools Java SDK.
 *
 * <p>Registers a {@link FulfillmenttoolsClient} bean when:
 * <ul>
 *   <li>{@code FulfillmenttoolsClient} is on the classpath (i.e. {@code sdk-core} is a dependency),</li>
 *   <li>no {@code FulfillmenttoolsClient} bean has been defined by the application, and</li>
 *   <li>the {@code fulfillmenttools.project-id} property is present.</li>
 * </ul>
 *
 * <p>The auto-configured bean constructs the client eagerly on context startup but does
 * <em>not</em> authenticate until the first API call is made — token acquisition is lazy.
 *
 * <p>To disable auto-configuration entirely, set:
 * <pre>{@code
 * spring.autoconfigure.exclude=de.joesst.dev.fulfillmenttools.spring.FulfillmenttoolsAutoConfiguration
 * }</pre>
 *
 * <p>To supply a customized client bean, simply declare your own {@code @Bean} of type
 * {@link FulfillmenttoolsClient} — the {@code @ConditionalOnMissingBean} guard will back off.
 */
@AutoConfiguration
@EnableConfigurationProperties(FulfillmenttoolsProperties.class)
@ConditionalOnClass(FulfillmenttoolsClient.class)
@ConditionalOnMissingBean(FulfillmenttoolsClient.class)
@ConditionalOnProperty(prefix = "fulfillmenttools", name = "project-id")
public class FulfillmenttoolsAutoConfiguration {

    /**
     * Creates a {@link FulfillmenttoolsClient} bean from the bound
     * {@link FulfillmenttoolsProperties}.
     *
     * <p>The base URL is derived as
     * {@code https://<fulfillmenttools.project-id>.api.fulfillmenttools.com}.
     *
     * @param props the bound configuration properties
     * @return a fully configured {@link FulfillmenttoolsClient}
     */
    @Bean
    @ConditionalOnMissingBean
    public FulfillmenttoolsClient fulfillmenttoolsClient(FulfillmenttoolsProperties props) {
        return FulfillmenttoolsClient.builder()
                .baseUrl("https://" + props.getProjectId() + ".api.fulfillmenttools.com")
                .credentials(new EmailPasswordCredentials(
                        props.getUsername(), props.getPassword(), props.getApiKey()))
                .build();
    }
}
