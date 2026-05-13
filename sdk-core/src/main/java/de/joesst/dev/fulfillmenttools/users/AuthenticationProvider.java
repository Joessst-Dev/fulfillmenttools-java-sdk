package de.joesst.dev.fulfillmenttools.users;

/**
 * The authentication provider configuration for a user.
 *
 * @param id   the provider identifier
 * @param type the authentication mechanism; one of
 *             {@code EMAIL_PASSWORD}, {@code OIDC}, {@code SUPPORT_ACCESS}
 */
public record AuthenticationProvider(
        String id,
        String type
) {}
