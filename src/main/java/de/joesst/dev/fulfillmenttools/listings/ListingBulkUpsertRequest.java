package de.joesst.dev.fulfillmenttools.listings;

import java.util.List;
import java.util.Objects;

/**
 * Request parameters for bulk upserting product listings via
 * {@link ListingsClient#bulkUpsert(ListingBulkUpsertRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * ListingBulkUpsertRequest request = ListingBulkUpsertRequest.builder()
 *     .listings(List.of(...))
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class ListingBulkUpsertRequest {

    private final List<ListingUpsertItem> listings;

    private ListingBulkUpsertRequest(Builder builder) {
        this.listings = Objects.requireNonNull(builder.listings, "listings must not be null");
    }

    /**
     * Returns the listing items to be upserted.
     *
     * @return the listing items
     */
    public List<ListingUpsertItem> listings() { return listings; }

    /**
     * Returns a new {@link Builder} for constructing a {@code ListingBulkUpsertRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link ListingBulkUpsertRequest}.
     */
    public static final class Builder {
        private List<ListingUpsertItem> listings;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the listing items to be upserted.
         *
         * @param listings the listing items
         * @return this builder
         */
        public Builder listings(List<ListingUpsertItem> listings) { this.listings = listings; return this; }

        /**
         * Builds the {@link ListingBulkUpsertRequest}. Throws {@link NullPointerException}
         * if listings is absent.
         *
         * @return a new {@code ListingBulkUpsertRequest}
         */
        public ListingBulkUpsertRequest build() { return new ListingBulkUpsertRequest(this); }
    }
}
