package de.joesst.dev.fulfillmenttools.auth;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class EmailPasswordCredentialsBuilderTest {

    @Nested
    class WhenBuildingHappyPath {

        @Test
        void shouldReturnRecordWithAllFieldsSetViaBuilder() {
            // Given
            var email = "user@example.com";
            var password = "s3cr3t";
            var apiKey = "api-key-xyz";

            // When
            var result = EmailPasswordCredentials.builder()
                    .email(email)
                    .password(password)
                    .apiKey(apiKey)
                    .build();

            // Then
            assertThat(result.email()).isEqualTo(email);
            assertThat(result.password()).isEqualTo(password);
            assertThat(result.apiKey()).isEqualTo(apiKey);
        }
    }

    @Nested
    class WhenRequiredFieldIsNull {

        @Test
        void shouldThrowWhenEmailIsNull() {
            // Given – email explicitly set to null, other fields valid

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> EmailPasswordCredentials.builder()
                            .email(null)
                            .password("s3cr3t")
                            .apiKey("api-key-xyz")
                            .build())
                    .withMessageContaining("email");
        }

        @Test
        void shouldThrowWhenPasswordIsNull() {
            // Given – password explicitly set to null, other fields valid

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> EmailPasswordCredentials.builder()
                            .email("user@example.com")
                            .password(null)
                            .apiKey("api-key-xyz")
                            .build())
                    .withMessageContaining("password");
        }

        @Test
        void shouldThrowWhenApiKeyIsNull() {
            // Given – apiKey explicitly set to null, other fields valid

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> EmailPasswordCredentials.builder()
                            .email("user@example.com")
                            .password("s3cr3t")
                            .apiKey(null)
                            .build())
                    .withMessageContaining("apiKey");
        }
    }
}
