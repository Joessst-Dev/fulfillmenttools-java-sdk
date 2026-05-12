package de.joesst.dev.fulfillmenttools.internal.routingplans;

import java.util.List;

record UpdateRoutingPlanBody(Integer version, List<ModifyRoutingPlanAction> actions) {

    record ModifyRoutingPlanAction(String action, String facilityRef, String status) {
        ModifyRoutingPlanAction(String facilityRef, String status) {
            this("ModifyRoutingPlan", facilityRef, status);
        }
    }
}
