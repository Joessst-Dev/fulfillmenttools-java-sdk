package de.joesst.dev.fulfillmenttools.internal.listings;

import de.joesst.dev.fulfillmenttools.listings.Listing;

import java.util.List;

record ListingSearchResponse(List<Listing> listings, PageInfoDto pageInfo, Integer total) {
    record PageInfoDto(String endCursor, Boolean hasNextPage, Boolean hasPreviousPage, String startCursor) {}
}
