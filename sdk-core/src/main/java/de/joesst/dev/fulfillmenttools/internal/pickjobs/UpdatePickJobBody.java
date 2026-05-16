package de.joesst.dev.fulfillmenttools.internal.pickjobs;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

record UpdatePickJobBody(Integer version, List<ModifyPickJobAction> actions) {
    record ModifyPickJobAction(
            String action,
            String status,
            CustomAttributes customAttributes,
            List<String> preferredPickingMethods
    ) {
        ModifyPickJobAction(String status, CustomAttributes customAttributes, List<String> preferredPickingMethods) {
            this("ModifyPickJob", status, customAttributes, preferredPickingMethods);
        }
    }
}
