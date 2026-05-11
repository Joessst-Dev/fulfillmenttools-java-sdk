package de.joesst.dev.fulfillmenttools.internal;

import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class PagesTest {

    // --- Single page ---

    @Test
    void all_singlePage_yieldsAllItemsAndStops() {
        // Given
        Iterable<String> iterable = Pages.all(cursor -> {
            assertThat(cursor).isNull(); // first page has no cursor
            return new Page<>(List.of("a", "b", "c"), null);
        });

        // When
        List<String> results = new ArrayList<>();
        iterable.forEach(results::add);

        // Then
        assertThat(results).containsExactly("a", "b", "c");
    }

    // --- Multiple pages ---

    @Test
    void all_multiplePages_yieldsItemsAcrossAllPages() {
        // Given — page 1 returns cursor "p2"; page 2 returns cursor "p3"; page 3 has no cursor
        Iterable<String> iterable = Pages.all(cursor -> switch (cursor == null ? "first" : cursor) {
            case "first" -> new Page<>(List.of("a", "b"), "p2");
            case "p2"    -> new Page<>(List.of("c", "d"), "p3");
            case "p3"    -> new Page<>(List.of("e"), null);
            default      -> throw new IllegalArgumentException("unexpected cursor: " + cursor);
        });

        // When
        List<String> results = new ArrayList<>();
        iterable.forEach(results::add);

        // Then
        assertThat(results).containsExactly("a", "b", "c", "d", "e");
    }

    @Test
    void all_fetcherCalledWithCorrectCursors() {
        // Given
        List<String> receivedCursors = new ArrayList<>();
        Iterable<String> iterable = Pages.all(cursor -> {
            receivedCursors.add(cursor);
            return cursor == null
                    ? new Page<>(List.of("x"), "next-cursor")
                    : new Page<>(List.of("y"), null);
        });

        // When
        iterable.forEach(ignored -> {});

        // Then
        assertThat(receivedCursors).containsExactly(null, "next-cursor");
    }

    // --- Empty page ---

    @Test
    void all_emptyFirstPage_yieldsNothing() {
        // Given
        Iterable<String> iterable = Pages.all(cursor -> new Page<>(List.of(), null));

        // When
        List<String> results = new ArrayList<>();
        iterable.forEach(results::add);

        // Then
        assertThat(results).isEmpty();
    }

    // --- Lazy fetching ---

    @Test
    void all_doesNotFetchNextPageUntilCurrentPageExhausted() {
        // Given
        AtomicInteger fetchCount = new AtomicInteger();
        Iterable<String> iterable = Pages.all(cursor -> {
            int call = fetchCount.incrementAndGet();
            return call == 1
                    ? new Page<>(List.of("a", "b"), "c2")
                    : new Page<>(List.of("c"), null);
        });

        var iterator = iterable.iterator();

        // When — consume only the first item
        assertThat(iterator.next()).isEqualTo("a");

        // Then — only one fetch so far
        assertThat(fetchCount.get()).isEqualTo(1);

        // When — consume the rest of page 1
        assertThat(iterator.next()).isEqualTo("b");
        assertThat(fetchCount.get()).isEqualTo(1);

        // When — cross the page boundary
        assertThat(iterator.next()).isEqualTo("c");

        // Then — second fetch triggered
        assertThat(fetchCount.get()).isEqualTo(2);
    }

    // --- hasNext idempotency ---

    @Test
    void all_repeatedHasNextCallsDoNotTriggerExtraFetches() {
        // Given
        AtomicInteger fetchCount = new AtomicInteger();
        var iterator = Pages.<String>all(cursor -> {
            fetchCount.incrementAndGet();
            return new Page<>(List.of("only"), null);
        }).iterator();

        // When — call hasNext multiple times without consuming
        boolean r1 = iterator.hasNext();
        boolean r2 = iterator.hasNext();
        boolean r3 = iterator.hasNext();

        // Then — only one fetch despite three hasNext calls
        assertThat(r1).isTrue();
        assertThat(r2).isTrue();
        assertThat(r3).isTrue();
        assertThat(fetchCount.get()).isEqualTo(1);
    }

    // --- next() after exhaustion ---

    @Test
    void all_nextAfterExhaustion_throwsNoSuchElementException() {
        // Given
        var iterator = Pages.<String>all(cursor -> new Page<>(List.of("a"), null)).iterator();
        iterator.next(); // consume the only item

        // When / Then
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }

    // --- Each Iterable call returns a fresh iterator ---

    @Test
    void all_iterableCanBeIteratedMultipleTimes() {
        // Given
        AtomicInteger fetchCount = new AtomicInteger();
        Iterable<String> iterable = Pages.all(cursor -> {
            fetchCount.incrementAndGet();
            return new Page<>(List.of("a"), null);
        });

        // When — iterate twice
        List<String> first = new ArrayList<>();
        iterable.forEach(first::add);
        List<String> second = new ArrayList<>();
        iterable.forEach(second::add);

        // Then — independent iterations, each fetches from the start
        assertThat(first).containsExactly("a");
        assertThat(second).containsExactly("a");
        assertThat(fetchCount.get()).isEqualTo(2);
    }
}
