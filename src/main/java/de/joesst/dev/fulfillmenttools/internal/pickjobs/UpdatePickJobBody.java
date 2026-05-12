package de.joesst.dev.fulfillmenttools.internal.pickjobs;

import java.util.List;
import java.util.Map;

record UpdatePickJobBody(Integer version, List<ModifyPickJobAction> actions) {
    record ModifyPickJobAction(
            String action,
            String status,
            Map<String, Object> customAttributes,
            List<String> preferredPickingMethods
    ) {
        ModifyPickJobAction(String status, Map<String, Object> customAttributes, List<String> preferredPickingMethods) {
            this("ModifyPickJob", status, customAttributes, preferredPickingMethods);
        }
    }
}
