package de.joesst.dev.fulfillmenttools.internal.returns;

import de.joesst.dev.fulfillmenttools.returns.Return;

import java.util.List;

record ReturnListResponse(List<Return> returns, String nextCursor) {}
