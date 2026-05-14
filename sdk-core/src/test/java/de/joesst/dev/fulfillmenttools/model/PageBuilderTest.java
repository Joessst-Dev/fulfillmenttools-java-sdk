package de.joesst.dev.fulfillmenttools.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PageBuilderTest {

    @Nested
    class WhenBuildingHappyPath {

        @Test
        void shouldReturnRecordWithItemsAndNextCursorSetViaBuilder() {
            // Given
            var items = List.of("item-1", "item-2", "item-3");
            var nextCursor = "cursor-abc";

            // When
            var result = Page.<String>builder()
                    .items(items)
                    .nextCursor(nextCursor)
                    .build();

            // Then
            assertThat(result.items()).containsExactlyElementsOf(items);
            assertThat(result.nextCursor()).isEqualTo(nextCursor);
        }

        @Test
        void shouldReportHasMoreWhenNextCursorIsPresent() {
            // Given
            var items = List.of("item-1");

            // When
            var result = Page.<String>builder()
                    .items(items)
                    .nextCursor("next-page-cursor")
                    .build();

            // Then
            assertThat(result.hasMore()).isTrue();
        }
    }

    @Nested
    class WhenNullableFieldsAreNotSet {

        @Test
        void shouldProduceRecordWithNullNextCursorWhenNotSet() {
            // Given – builder with only items set, nextCursor omitted

            // When
            var result = Page.<String>builder()
                    .items(List.of("item-1"))
                    .build();

            // Then
            assertThat(result.nextCursor()).isNull();
        }

        @Test
        void shouldReportNoMorePagesWhenNextCursorIsNull() {
            // Given
            var result = Page.<String>builder()
                    .items(List.of("item-1"))
                    .build();

            // When / Then
            assertThat(result.hasMore()).isFalse();
        }

        @Test
        void shouldProduceRecordWithNullItemsWhenNotSet() {
            // Given – builder with no setters called

            // When
            var result = Page.<String>builder().build();

            // Then
            assertThat(result.items()).isNull();
            assertThat(result.nextCursor()).isNull();
        }
    }
}
