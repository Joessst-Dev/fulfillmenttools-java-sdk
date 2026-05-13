package de.joesst.dev.fulfillmenttools.internal.listings;

import de.joesst.dev.fulfillmenttools.listings.Listing;

import java.util.List;

record ListingBulkUpsertResponse(List<Listing> listings) {}
