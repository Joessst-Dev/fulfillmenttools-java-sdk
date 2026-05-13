package de.joesst.dev.fulfillmenttools.users;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A role assignment with optional context limitations.
 *
 * <p>{@code UserRole} is used within {@link CustomClaims} (deprecated) and in the
 * deprecated {@code roles} field of a user creation request.
 *
 * @param name               the role name; required
 * @param context            context limitations scoping this role (preferred field)
 * @param contextLimitations context limitations scoping this role (deprecated; prefer {@code context})
 * @param facilities         legacy list of facility IDs (deprecated)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRole(
        String name,
        List<ContextLimitation> context,
        List<ContextLimitation> contextLimitations,
        List<String> facilities
) {}
