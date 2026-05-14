package de.joesst.dev.fulfillmenttools.auth;

import java.util.Objects;

/**
 * Email and password credentials for authenticating with the fulfillmenttools platform.
 *
 * @param email the email address associated with the user account; must not be null.
 * @param password the password for the user account; must not be null.
 * @param apiKey the API key issued by the fulfillmenttools platform; must not be null.
 */
public record EmailPasswordCredentials(String email, String password, String apiKey)
        implements Credentials {

    /**
     * Compact constructor that validates all credentials are present.
     */
    public EmailPasswordCredentials {
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(password, "password must not be null");
        Objects.requireNonNull(apiKey, "apiKey must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String email;
        private String password;
        private String apiKey;

        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder apiKey(String apiKey) { this.apiKey = apiKey; return this; }

        public EmailPasswordCredentials build() {
            return new EmailPasswordCredentials(email, password, apiKey);
        }
    }
}
