package de.joesst.dev.fulfillmenttools.health;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HealthResultBuilderTest {

    @Nested
    class WhenBuildingHappyPath {

        @Test
        void shouldReturnRecordWithAllFieldsSetViaBuilder() {
            // Given
            var status = "UP";
            var dependency = HealthDependencyStatus.builder()
                    .name("database")
                    .status("UP")
                    .build();
            var dependencies = List.of(dependency);

            // When
            var result = HealthResult.builder()
                    .status(status)
                    .dependencies(dependencies)
                    .build();

            // Then
            assertThat(result.status()).isEqualTo(status);
            assertThat(result.dependencies()).containsExactly(dependency);
        }

        @Test
        void shouldRoundTripMultipleDependencies() {
            // Given
            var dep1 = HealthDependencyStatus.builder().name("cache").status("UP").build();
            var dep2 = HealthDependencyStatus.builder().name("queue").status("DEGRADED").build();

            // When
            var result = HealthResult.builder()
                    .status("DEGRADED")
                    .dependencies(List.of(dep1, dep2))
                    .build();

            // Then
            assertThat(result.dependencies()).hasSize(2);
            assertThat(result.dependencies().get(1).status()).isEqualTo("DEGRADED");
        }
    }

    @Nested
    class WhenNullableFieldsAreNotSet {

        @Test
        void shouldProduceRecordWithNullFieldsWhenNoSettersCalled() {
            // Given – builder with no setters called

            // When
            var result = HealthResult.builder().build();

            // Then
            assertThat(result.status()).isNull();
            assertThat(result.dependencies()).isNull();
        }
    }
}
