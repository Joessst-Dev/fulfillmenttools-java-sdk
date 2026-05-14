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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String type;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public AuthenticationProvider build() {
            return new AuthenticationProvider(id, type);
        }
    }
}
