package de.joesst.dev.fulfillmenttools.users;

import java.util.List;

/**
 * A role assigned to a user, optionally scoped to specific facilities.
 */
public record AssignedRole(String roleId, String roleName, List<String> facilityRefs) {}
