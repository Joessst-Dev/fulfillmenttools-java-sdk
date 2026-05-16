package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomAttributesTest {

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    @Nested
    class WhenConstructed {

        @Test
        void storesAttributes() {
            // Given / When
            CustomAttributes attrs = new CustomAttributes(Map.of("color", "red", "priority", 1));

            // Then
            assertThat(attrs.attributes()).containsEntry("color", "red").containsEntry("priority", 1);
        }

        @Test
        void treatsNullMapAsEmpty() {
            // Given / When
            CustomAttributes attrs = new CustomAttributes(null);

            // Then
            assertThat(attrs.attributes()).isEmpty();
        }

        @Test
        void attributesMapIsUnmodifiable() {
            // Given
            CustomAttributes attrs = new CustomAttributes(Map.of("k", "v"));

            // When / Then
            assertThatThrownBy(() -> attrs.attributes().put("new", "val"))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    // -------------------------------------------------------------------------
    // as()
    // -------------------------------------------------------------------------

    @Nested
    class WhenConvertingWithAs {

        record Config(String region, int timeout) {}

        @Test
        void convertsAttributesToTypedPojo() {
            // Given
            CustomAttributes attrs = new CustomAttributes(Map.of("region", "EU", "timeout", 30));

            // When
            Config config = attrs.as(Config.class);

            // Then
            assertThat(config.region()).isEqualTo("EU");
            assertThat(config.timeout()).isEqualTo(30);
        }

        @Test
        void throwsWhenTypeIsIncompatible() {
            // Given
            CustomAttributes attrs = new CustomAttributes(Map.of("foo", "bar"));

            // When / Then
            assertThatThrownBy(() -> attrs.as(Integer.class))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    // -------------------------------------------------------------------------
    // JSON round-trip (@JsonCreator / @JsonValue)
    // -------------------------------------------------------------------------

    @Nested
    class WhenSerializedAndDeserialized {

        @Test
        void deserializesFromJson() {
            // Given
            byte[] json = "{\"color\":\"red\",\"count\":3}".getBytes();

            // When
            CustomAttributes attrs = new JsonCodec().decode(json, CustomAttributes.class);

            // Then
            assertThat(attrs.attributes()).containsEntry("color", "red").containsEntry("count", 3);
        }

        @Test
        void serializesBackToFlatJson() {
            // Given
            CustomAttributes attrs = new CustomAttributes(Map.of("x", 1));

            // When
            String json = new String(new JsonCodec().encode(attrs));

            // Then
            assertThat(json).isEqualTo("{\"x\":1}");
        }

        @Test
        void customMapperIsUsedByAsAfterDeserialization() {
            // Given — codec with a custom mapper
            ObjectMapper customMapper = new ObjectMapper();
            byte[] json = "{\"region\":\"EU\",\"timeout\":30}".getBytes();

            // When
            CustomAttributes attrs = new JsonCodec(customMapper).decode(json, CustomAttributes.class);
            record Config(String region, int timeout) {}
            Config config = attrs.as(Config.class);

            // Then
            assertThat(config.region()).isEqualTo("EU");
            assertThat(config.timeout()).isEqualTo(30);
        }
    }

    // -------------------------------------------------------------------------
    // equals / hashCode / toString
    // -------------------------------------------------------------------------

    @Nested
    class WhenCompared {

        @Test
        void equalWhenAttributesMatch() {
            assertThat(new CustomAttributes(Map.of("a", 1)))
                    .isEqualTo(new CustomAttributes(Map.of("a", 1)));
        }

        @Test
        void notEqualWhenAttributesDiffer() {
            assertThat(new CustomAttributes(Map.of("a", 1)))
                    .isNotEqualTo(new CustomAttributes(Map.of("a", 2)));
        }

        @Test
        void toStringDelegatesToMap() {
            CustomAttributes attrs = new CustomAttributes(Map.of("x", "y"));
            assertThat(attrs.toString()).contains("x").contains("y");
        }
    }
}
