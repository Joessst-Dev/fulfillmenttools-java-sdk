package de.joesst.dev.fulfillmenttools.model;

import java.util.List;

/**
 * A page of results from a list API endpoint with support for cursor-based pagination.
 *
 * @param items the list of items on this page.
 * @param nextCursor the cursor to use when fetching the next page, or {@code null} if there are no more pages.
 * @param <T> the type of items in this page.
 */
public record Page<T>(List<T> items, String nextCursor) {

    /**
     * Checks whether more pages are available for fetching.
     *
     * @return {@code true} if a next page is available, {@code false} otherwise.
     */
    public boolean hasMore() {
        return nextCursor != null && !nextCursor.isEmpty();
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {
        private Builder() {}

        private List<T> items;
        private String nextCursor;

        public Builder<T> items(List<T> items) { this.items = items; return this; }
        public Builder<T> nextCursor(String nextCursor) { this.nextCursor = nextCursor; return this; }

        public Page<T> build() {
            return new Page<>(items, nextCursor);
        }
    }
}
