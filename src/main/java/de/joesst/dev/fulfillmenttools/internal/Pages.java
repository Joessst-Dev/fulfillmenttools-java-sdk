package de.joesst.dev.fulfillmenttools.internal;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.function.Function;

/**
 * Factory for lazy auto-paginating iterables.
 *
 * <p>Usage in a resource client:
 * <pre>{@code
 * public Iterable<Order> listAll(OrderListRequest req) {
 *     return Pages.all(cursor -> {
 *         OrderListRequest r = cursor == null ? req : req.toBuilder().startAfterId(cursor).build();
 *         return list(r);
 *     });
 * }
 * }</pre>
 */
public final class Pages {

    private Pages() {}

    /**
     * Returns a lazy {@link Iterable} that fetches pages on demand.
     *
     * @param fetcher called with {@code null} for the first page, then with the
     *                {@link Page#nextCursor()} from each subsequent page
     */
    public static <T> Iterable<T> all(Function<String, Page<T>> fetcher) {
        return () -> new PageIterator<>(fetcher);
    }
}
