package de.joesst.dev.fulfillmenttools.internal.carriers;

import de.joesst.dev.fulfillmenttools.carriers.Carrier;

import java.util.List;

record CarrierListResponse(List<Carrier> carriers, String nextCursor) {}
