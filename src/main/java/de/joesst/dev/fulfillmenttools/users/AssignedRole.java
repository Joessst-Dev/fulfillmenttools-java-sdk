package de.joesst.dev.fulfillmenttools.users;

import java.util.List;
import java.util.Map;

public record AssignedRole(
        String ref,
        List<Map<String, Object>> context,
        List<Map<String, Object>> contextLimitations
) {}
