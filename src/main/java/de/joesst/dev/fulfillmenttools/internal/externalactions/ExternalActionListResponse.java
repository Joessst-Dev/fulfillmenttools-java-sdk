package de.joesst.dev.fulfillmenttools.internal.externalactions;

import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;

import java.util.List;

record ExternalActionListResponse(List<ExternalAction> externalActions, String nextCursor) {}
