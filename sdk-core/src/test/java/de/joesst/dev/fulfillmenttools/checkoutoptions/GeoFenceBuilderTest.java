package de.joesst.dev.fulfillmenttools.checkoutoptions;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class GeoFenceBuilderTest {

    @Nested
    class WhenBuildingHappyPath {

        @Test
        void shouldReturnRecordWithAllFieldsSetViaBuilder() {
            // Given
            var lat = 52.5200;
            var lon = 13.4050;
            var radius = 10.0;

            // When
            var result = GeoFence.builder()
                    .lat(lat)
                    .lon(lon)
                    .radius(radius)
                    .build();

            // Then
            assertThat(result.lat()).isCloseTo(lat, within(0.00001));
            assertThat(result.lon()).isCloseTo(lon, within(0.00001));
            assertThat(result.radius()).isCloseTo(radius, within(0.00001));
        }

        @Test
        void shouldBeEqualToDirectConstructorWithSameValues() {
            // Given
            var lat = 48.8566;
            var lon = 2.3522;
            var radius = 25.0;

            // When
            var viaBuilder = GeoFence.builder().lat(lat).lon(lon).radius(radius).build();
            var viaConstructor = new GeoFence(lat, lon, radius);

            // Then
            assertThat(viaBuilder).isEqualTo(viaConstructor);
        }
    }

    @Nested
    class WhenNoSettersCalled {

        @Test
        void shouldProduceRecordWithDefaultDoubleZeroValues() {
            // Given – builder with no setters called (double fields default to 0.0)

            // When
            var result = GeoFence.builder().build();

            // Then
            assertThat(result.lat()).isZero();
            assertThat(result.lon()).isZero();
            assertThat(result.radius()).isZero();
        }
    }
}
