package de.joesst.dev.fulfillmenttools.internal.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
record ListingSearchBody(String facilityRef, String tenantArticleId, Integer size, String startAfterId) {}
