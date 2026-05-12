package de.joesst.dev.fulfillmenttools.internal.handoverjobs;

import java.util.List;
import java.util.Map;

record UpdateHandoverJobBody(Integer version, List<ModifyHandoverjobAction> actions) {

    record ModifyHandoverjobAction(String action, String status, Map<String, Object> customAttributes) {
        ModifyHandoverjobAction(String status, Map<String, Object> customAttributes) {
            this("ModifyHandoverjob", status, customAttributes);
        }
    }
}
