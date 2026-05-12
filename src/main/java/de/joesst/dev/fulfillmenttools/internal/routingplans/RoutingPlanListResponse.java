package de.joesst.dev.fulfillmenttools.internal.routingplans;

import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlan;

import java.util.List;

record RoutingPlanListResponse(List<RoutingPlan> routingPlans, String nextCursor) {}
