package de.joesst.dev.fulfillmenttools.internal.packjobs;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

record UpdatePackJobBody(Integer version, List<ModifyPackJobAction> actions) {

    record ModifyPackJobAction(String action, String status, CustomAttributes customAttributes) {
        ModifyPackJobAction(String status, CustomAttributes customAttributes) {
            this("ModifyPackJob", status, customAttributes);
        }
    }
}
