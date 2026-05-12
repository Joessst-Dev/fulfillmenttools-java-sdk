package de.joesst.dev.fulfillmenttools.internal.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

record UpdateUserBody(Integer version, List<ModifyUserAction> actions) {

    record ModifyUserAction(
            String action,
            @JsonProperty("firstname") String firstName,
            @JsonProperty("lastname") String lastName,
            String email,
            String password,
            String locale,
            List<Map<String, Object>> assignedRoles,
            Map<String, Object> customAttributes
    ) {
        ModifyUserAction(String firstName, String lastName, String email, String password,
                         String locale, List<Map<String, Object>> assignedRoles,
                         Map<String, Object> customAttributes) {
            this("ModifyUser", firstName, lastName, email, password, locale, assignedRoles, customAttributes);
        }
    }
}
