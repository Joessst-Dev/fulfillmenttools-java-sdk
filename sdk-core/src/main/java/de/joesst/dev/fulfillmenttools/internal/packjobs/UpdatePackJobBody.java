package de.joesst.dev.fulfillmenttools.internal.packjobs;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

record UpdatePackJobBody(Integer version, List<ModifyPackJobAction> actions) {

    record ModifyPackJobAction(String action, String status, CustomAttributes customAttributes) {
        ModifyPackJobAction(String status, CustomAttributes customAttributes) {
            this("ModifyPackJob", status, customAttributes);
        }
    }
}
