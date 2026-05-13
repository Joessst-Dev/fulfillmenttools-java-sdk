package de.joesst.dev.fulfillmenttools.internal.listings;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.listings.ListingSearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record ListingSearchBody(ListingSearchQuery query, Integer size, Integer last, String after, String before) {}
