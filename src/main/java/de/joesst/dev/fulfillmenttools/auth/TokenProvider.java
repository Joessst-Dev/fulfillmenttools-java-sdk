package de.joesst.dev.fulfillmenttools.auth;

import de.joesst.dev.fulfillmenttools.AuthenticationException;

/**
 * Provides bearer tokens for authenticating requests to the fulfillmenttools API.
 *
 * <p>Implementations must handle token refresh lifecycle, including expiry detection and automatic
 * refresh. Tokens returned by {@link #getAccessToken()} should be valid for use immediately.
 */
public interface TokenProvider {

    /**
     * Returns a valid bearer token, refreshing it if near expiry.
     *
     * @return a valid bearer token.
     * @throws AuthenticationException if a token cannot be obtained
     */
    String getAccessToken();

    /**
     * Discards the cached token so the next {@link #getAccessToken()} call fetches a fresh one.
     * Call this when a request returns 401 to force re-authentication.
     */
    void invalidate();
}
