package de.joesst.dev.fulfillmenttools.spring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FulfillmenttoolsPropertiesTest {

    @Test
    void defaultValues_areNull() {
        // Given / When: a new properties instance with no values set
        FulfillmenttoolsProperties props = new FulfillmenttoolsProperties();

        // Then: all fields default to null
        assertThat(props.getProjectId()).isNull();
        assertThat(props.getApiKey()).isNull();
        assertThat(props.getUsername()).isNull();
        assertThat(props.getPassword()).isNull();
    }

    @Test
    void setterAndGetter_projectId() {
        // Given
        FulfillmenttoolsProperties props = new FulfillmenttoolsProperties();

        // When
        props.setProjectId("ocff-abc-pre");

        // Then
        assertThat(props.getProjectId()).isEqualTo("ocff-abc-pre");
    }

    @Test
    void setterAndGetter_apiKey() {
        // Given
        FulfillmenttoolsProperties props = new FulfillmenttoolsProperties();

        // When
        props.setApiKey("AIzaSyFake");

        // Then
        assertThat(props.getApiKey()).isEqualTo("AIzaSyFake");
    }

    @Test
    void setterAndGetter_username() {
        // Given
        FulfillmenttoolsProperties props = new FulfillmenttoolsProperties();

        // When
        props.setUsername("service@ocff-abc-pre.com");

        // Then
        assertThat(props.getUsername()).isEqualTo("service@ocff-abc-pre.com");
    }

    @Test
    void setterAndGetter_password() {
        // Given
        FulfillmenttoolsProperties props = new FulfillmenttoolsProperties();

        // When
        props.setPassword("s3cr3t");

        // Then
        assertThat(props.getPassword()).isEqualTo("s3cr3t");
    }
}
