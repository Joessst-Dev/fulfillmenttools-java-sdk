package de.joesst.dev.fulfillmenttools.internal.listings;

import de.joesst.dev.fulfillmenttools.listings.ListingUpsertItem;

import java.util.List;

record ListingBulkUpsertBody(List<ListingUpsertItem> listings) {}
