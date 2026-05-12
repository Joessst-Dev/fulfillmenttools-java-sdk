package de.joesst.dev.fulfillmenttools.listings;

import java.util.List;
import java.util.Objects;

public final class ListingBulkUpsertRequest {

    private final List<ListingUpsertItem> listings;

    private ListingBulkUpsertRequest(Builder builder) {
        this.listings = Objects.requireNonNull(builder.listings, "listings must not be null");
    }

    public List<ListingUpsertItem> listings() { return listings; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private List<ListingUpsertItem> listings;

        public Builder listings(List<ListingUpsertItem> listings) { this.listings = listings; return this; }

        public ListingBulkUpsertRequest build() { return new ListingBulkUpsertRequest(this); }
    }
}
