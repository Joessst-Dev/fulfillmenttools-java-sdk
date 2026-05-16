package de.joesst.dev.fulfillmenttools.internal.pickjobs;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

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
