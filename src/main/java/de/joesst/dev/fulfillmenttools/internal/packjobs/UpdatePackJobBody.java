package de.joesst.dev.fulfillmenttools.internal.packjobs;

import java.util.List;
import java.util.Map;

record UpdatePackJobBody(Integer version, List<ModifyPackJobAction> actions) {

    record ModifyPackJobAction(String action, String status, Map<String, Object> customAttributes) {
        ModifyPackJobAction(String status, Map<String, Object> customAttributes) {
            this("ModifyPackJob", status, customAttributes);
        }
    }
}
