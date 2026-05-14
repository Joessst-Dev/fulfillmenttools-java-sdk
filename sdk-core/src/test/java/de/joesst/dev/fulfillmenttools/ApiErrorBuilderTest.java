package de.joesst.dev.fulfillmenttools;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiErrorBuilderTest {

    @Nested
    class WhenBuildingHappyPath {

        @Test
        void shouldReturnRecordWithAllFieldsSetViaBuilder() {
            // Given
            var description = "The requested resource was not found.";
            var summary = "Not Found";
            var requestVersion = 1;
            var version = 2;

            // When
            var result = ApiError.builder()
                    .description(description)
                    .summary(summary)
                    .requestVersion(requestVersion)
                    .version(version)
                    .build();

            // Then
            assertThat(result.description()).isEqualTo(description);
            assertThat(result.summary()).isEqualTo(summary);
            assertThat(result.requestVersion()).isEqualTo(requestVersion);
            assertThat(result.version()).isEqualTo(version);
        }

        @Test
        void shouldBeEqualToDirectConstructorWithSameValues() {
            // Given
            var description = "Unauthorized";
            var summary = "Auth failed";
            var requestVersion = 1;
            var version = 1;

            // When
            var viaBuilder = ApiError.builder()
                    .description(description)
                    .summary(summary)
                    .requestVersion(requestVersion)
                    .version(version)
                    .build();
            var viaConstructor = new ApiError(description, summary, requestVersion, version);

            // Then
            assertThat(viaBuilder).isEqualTo(viaConstructor);
        }
    }

    @Nested
    class WhenNullableFieldsAreNotSet {

        @Test
        void shouldProduceRecordWithNullStringsAndZeroIntsWhenNoSettersCalled() {
            // Given – builder with no setters called

            // When
            var result = ApiError.builder().build();

            // Then
            assertThat(result.description()).isNull();
            assertThat(result.summary()).isNull();
            assertThat(result.requestVersion()).isZero();
            assertThat(result.version()).isZero();
        }
    }
}
