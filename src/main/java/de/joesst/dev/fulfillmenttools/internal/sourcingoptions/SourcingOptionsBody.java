package de.joesst.dev.fulfillmenttools.internal.sourcingoptions;

import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;

record SourcingOptionsBody(OrderForSourcingOptionsRequest order, Boolean includeListingCustomAttributes) {}
