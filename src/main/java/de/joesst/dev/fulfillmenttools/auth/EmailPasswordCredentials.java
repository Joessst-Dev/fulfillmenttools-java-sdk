package de.joesst.dev.fulfillmenttools.auth;

import java.util.Objects;

public record EmailPasswordCredentials(String email, String password, String apiKey)
        implements Credentials {

    public EmailPasswordCredentials {
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(password, "password must not be null");
        Objects.requireNonNull(apiKey, "apiKey must not be null");
    }
}
