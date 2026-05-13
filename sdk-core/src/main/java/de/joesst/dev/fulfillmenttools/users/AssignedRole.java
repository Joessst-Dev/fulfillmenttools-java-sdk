package de.joesst.dev.fulfillmenttools.users;

import java.util.List;

/**
 * A role reference assigned to a user, optionally scoped by context limitations.
 *
 * @param ref                the role reference identifier; required
 * @param context            context limitations that scope this role assignment (preferred field)
 * @param contextLimitations context limitations that scope this role assignment
 *                           (deprecated; prefer {@code context})
 */
public record AssignedRole(
        String ref,
        List<ContextLimitation> context,
        List<ContextLimitation> contextLimitations
) {}
