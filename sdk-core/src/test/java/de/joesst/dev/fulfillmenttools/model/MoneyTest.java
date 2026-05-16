package de.joesst.dev.fulfillmenttools.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

    @Nested
    class ToAmount {

        @Test
        void shiftsValueByDecimalPlaces() {
            // Given
            Money money = Money.builder().value(499.0).currency("EUR").decimalPlaces(2.0).build();

            // When / Then
            assertThat(money.toAmount()).isEqualTo(4.99);
        }

        @Test
        void returnsValueUnchangedWhenDecimalPlacesIsZero() {
            // Given
            Money money = Money.builder().value(500.0).currency("JPY").decimalPlaces(0.0).build();

            // When / Then
            assertThat(money.toAmount()).isEqualTo(500.0);
        }

        @Test
        void returnsValueUnchangedWhenDecimalPlacesIsNull() {
            // Given
            Money money = Money.builder().value(10.0).currency("EUR").build();

            // When / Then
            assertThat(money.toAmount()).isEqualTo(10.0);
        }

        @Test
        void returnsNullWhenValueIsNull() {
            // Given
            Money money = Money.builder().currency("EUR").decimalPlaces(2.0).build();

            // When / Then
            assertThat(money.toAmount()).isNull();
        }

        @Test
        void handlesThreeDecimalPlaces() {
            // Given
            Money money = Money.builder().value(1000.0).currency("KWD").decimalPlaces(3.0).build();

            // When / Then
            assertThat(money.toAmount()).isEqualTo(1.0);
        }

        @Test
        void returnsZeroWhenValueIsZero() {
            // Given
            Money money = Money.builder().value(0.0).currency("EUR").decimalPlaces(2.0).build();

            // When / Then
            assertThat(money.toAmount()).isEqualTo(0.0);
        }
    }
}
