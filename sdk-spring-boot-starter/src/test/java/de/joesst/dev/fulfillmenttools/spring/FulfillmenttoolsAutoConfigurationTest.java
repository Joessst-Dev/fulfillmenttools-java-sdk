package de.joesst.dev.fulfillmenttools.spring;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BDD tests for {@link FulfillmenttoolsAutoConfiguration}.
 *
 * <p>Uses {@link ApplicationContextRunner} to verify bean registration conditions without
 * starting a full Spring Boot application or making any real HTTP calls.
 * Token acquisition in the SDK is lazy (first API call), so constructing the client
 * with fake credentials succeeds at context load time.
 */
class FulfillmenttoolsAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(FulfillmenttoolsAutoConfiguration.class));

    @Nested
    class WhenAllPropertiesAreProvided {

        @Test
        void shouldRegisterFulfillmenttoolsClientBean() {
            // Given: all required properties are present
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: a FulfillmenttoolsClient bean is present
                        assertThat(context).hasSingleBean(FulfillmenttoolsClient.class);
                    });
        }

        @Test
        void shouldRegisterFulfillmenttoolsPropertiesBean() {
            // Given: all required properties are present
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: bound properties are accessible
                        assertThat(context).hasSingleBean(FulfillmenttoolsProperties.class);
                        FulfillmenttoolsProperties props =
                                context.getBean(FulfillmenttoolsProperties.class);
                        assertThat(props.getProjectId()).isEqualTo("ocff-test-pre");
                    });
        }

        @Test
        void shouldBindAllPropertyFields() {
            // Given: all required properties are present
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: all four fields are bound correctly
                        FulfillmenttoolsProperties props =
                                context.getBean(FulfillmenttoolsProperties.class);
                        assertThat(props.getProjectId()).isEqualTo("ocff-test-pre");
                        assertThat(props.getApiKey()).isEqualTo("fake-api-key");
                        assertThat(props.getUsername()).isEqualTo("service@ocff-test-pre.com");
                        assertThat(props.getPassword()).isEqualTo("fake-password");
                    });
        }

        @Test
        void shouldDeriveBaseUrlFromProjectId() throws Exception {
            // Given: a project-id of "ocff-myproject-pre"
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-myproject-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-myproject-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: the client's baseUrl is derived as https://<project-id>.api.fulfillmenttools.com
                        FulfillmenttoolsClient client = context.getBean(FulfillmenttoolsClient.class);
                        Field baseUrlField = FulfillmenttoolsClient.class.getDeclaredField("baseUrl");
                        baseUrlField.setAccessible(true);
                        assertThat(baseUrlField.get(client))
                                .isEqualTo("https://ocff-myproject-pre.api.fulfillmenttools.com");
                    });
        }
    }

    @Nested
    class WhenProjectIdIsMissing {

        @Test
        void shouldNotRegisterFulfillmenttoolsClientBean() {
            // Given: project-id is absent (only api-key, username, password provided)
            runner.withPropertyValues(
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: no FulfillmenttoolsClient bean is registered
                        assertThat(context).doesNotHaveBean(FulfillmenttoolsClient.class);
                    });
        }

        @Test
        void shouldNotRegisterFulfillmenttoolsClientBeanWhenNoPropertiesPresent() {
            // Given: no fulfillmenttools properties at all
            // When: the application context is loaded
            runner.run(context -> {
                // Then: no FulfillmenttoolsClient bean is registered
                assertThat(context).doesNotHaveBean(FulfillmenttoolsClient.class);
            });
        }
    }

    @Nested
    class WhenCredentialIsMissing {

        @Test
        void shouldFailToStartWhenApiKeyIsAbsent() {
            // Given: project-id, username, and password are present but api-key is absent
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: the context fails because EmailPasswordCredentials rejects a null apiKey
                        assertThat(context).hasFailed();
                    });
        }

        @Test
        void shouldFailToStartWhenUsernameIsAbsent() {
            // Given: project-id, api-key, and password are present but username is absent
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: the context fails because EmailPasswordCredentials rejects a null email
                        assertThat(context).hasFailed();
                    });
        }

        @Test
        void shouldFailToStartWhenPasswordIsAbsent() {
            // Given: project-id, api-key, and username are present but password is absent
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com")
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: the context fails because EmailPasswordCredentials rejects a null password
                        assertThat(context).hasFailed();
                    });
        }
    }

    @Nested
    class WhenAutoConfigurationIsExcluded {

        @Test
        void shouldNotRegisterBeanWhenAutoConfigurationIsAbsent() {
            // Given: all properties are set but the auto-configuration is not registered
            // (equivalent to excluding it via spring.autoconfigure.exclude in production)
            new ApplicationContextRunner()
                    .withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    // When: the application context is loaded without the auto-configuration
                    .run(context -> {
                        // Then: no FulfillmenttoolsClient bean is registered
                        assertThat(context).doesNotHaveBean(FulfillmenttoolsClient.class);
                    });
        }
    }

    @Nested
    class WhenUserProvidesCustomBean {

        @Test
        void shouldNotOverrideUserDefinedFulfillmenttoolsClientBean() {
            // Given: all properties are set AND the application registers its own client bean
            runner.withPropertyValues(
                            "fulfillmenttools.project-id=ocff-test-pre",
                            "fulfillmenttools.api-key=fake-api-key",
                            "fulfillmenttools.username=service@ocff-test-pre.com",
                            "fulfillmenttools.password=fake-password")
                    .withUserConfiguration(CustomClientConfiguration.class)
                    // When: the application context is loaded
                    .run(context -> {
                        // Then: exactly one bean exists and it is the user-defined one
                        assertThat(context).hasSingleBean(FulfillmenttoolsClient.class);
                        assertThat(context.getBean(FulfillmenttoolsClient.class))
                                .isSameAs(context.getBean(
                                        CustomClientConfiguration.CUSTOM_CLIENT_BEAN_NAME,
                                        FulfillmenttoolsClient.class));
                    });
        }
    }

    /**
     * A user-supplied configuration that registers its own {@link FulfillmenttoolsClient}.
     * The auto-configuration must back off and not register a second bean.
     */
    @Configuration
    static class CustomClientConfiguration {

        static final String CUSTOM_CLIENT_BEAN_NAME = "customFulfillmenttoolsClient";

        @Bean(CUSTOM_CLIENT_BEAN_NAME)
        FulfillmenttoolsClient customFulfillmenttoolsClient() {
            return FulfillmenttoolsClient.builder()
                    .baseUrl("https://custom.api.fulfillmenttools.com")
                    .credentials(new de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials(
                            "custom@example.com", "custom-password", "custom-api-key"))
                    .build();
        }
    }
}
