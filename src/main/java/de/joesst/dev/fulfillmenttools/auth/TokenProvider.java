package de.joesst.dev.fulfillmenttools.auth;

import de.joesst.dev.fulfillmenttools.AuthenticationException;

public interface TokenProvider {

    /**
     * Returns a valid bearer token, refreshing it if near expiry.
     *
     * @throws AuthenticationException if a token cannot be obtained
     */
    String getAccessToken();

    /**
     * Discards the cached token so the next {@link #getAccessToken()} call fetches a fresh one.
     * Call this when a request returns 401 to force re-authentication.
     */
    void invalidate();
}
