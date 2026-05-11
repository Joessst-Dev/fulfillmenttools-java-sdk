package de.joesst.dev.fulfillmenttools.internal;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Lazy iterator that auto-paginates by calling {@code fetcher} with {@code null} for the first
 * page and with the previous page's cursor for each subsequent page.
 */
final class PageIterator<T> implements Iterator<T> {

    private final Function<String, Page<T>> fetcher;
    private Page<T> currentPage;
    private int index;
    private boolean initialized;

    PageIterator(Function<String, Page<T>> fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public boolean hasNext() {
        if (!initialized) {
            currentPage = fetcher.apply(null);
            initialized = true;
        }
        // Skip empty intermediate pages (defensive; the API should not return them)
        while (index >= currentPage.items().size()) {
            if (!currentPage.hasMore()) {
                return false;
            }
            currentPage = fetcher.apply(currentPage.nextCursor());
            index = 0;
        }
        return true;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        return currentPage.items().get(index++);
    }
}
