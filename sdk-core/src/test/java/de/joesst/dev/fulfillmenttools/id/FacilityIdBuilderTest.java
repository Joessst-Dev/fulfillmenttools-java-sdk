package de.joesst.dev.fulfillmenttools.id;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class FacilityIdBuilderTest {

    @Nested
    class WhenBuildingHappyPath {

        @Test
        void shouldReturnRecordWithValueSetViaBuilder() {
            // Given
            var expected = "fac-123";

            // When
            var result = FacilityId.builder()
                    .value(expected)
                    .build();

            // Then
            assertThat(result.value()).isEqualTo(expected);
        }

        @Test
        void shouldBeEqualToDirectConstructorWithSameValue() {
            // Given
            var value = "fac-abc";

            // When
            var viaBuilder = FacilityId.builder().value(value).build();
            var viaConstructor = new FacilityId(value);

            // Then
            assertThat(viaBuilder).isEqualTo(viaConstructor);
        }
    }

    @Nested
    class WhenValueIsNull {

        @Test
        void shouldThrowNullPointerExceptionWhenValueIsNull() {
            // Given – builder with no value set

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> FacilityId.builder().value(null).build())
                    .withMessageContaining("value");
        }
    }
}
