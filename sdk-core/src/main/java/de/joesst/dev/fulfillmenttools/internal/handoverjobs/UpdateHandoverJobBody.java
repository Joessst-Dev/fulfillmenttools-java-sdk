package de.joesst.dev.fulfillmenttools.internal.handoverjobs;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

record UpdateHandoverJobBody(Integer version, List<ModifyHandoverjobAction> actions) {

    record ModifyHandoverjobAction(String action, String status, CustomAttributes customAttributes) {
        ModifyHandoverjobAction(String status, CustomAttributes customAttributes) {
            this("ModifyHandoverjob", status, customAttributes);
        }
    }
}
