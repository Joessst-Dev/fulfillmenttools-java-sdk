package de.joesst.dev.fulfillmenttools.auth;

import com.fasterxml.jackson.databind.JsonNode;
import de.joesst.dev.fulfillmenttools.AuthenticationException;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public final class GoogleIdentityToolkitTokenProvider implements TokenProvider {

    static final String DEFAULT_SIGN_IN_URL =
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    static final String DEFAULT_REFRESH_URL =
            "https://securetoken.googleapis.com/v1/token";

    private static final Duration REFRESH_SKEW = Duration.ofSeconds(60);

    private final EmailPasswordCredentials credentials;
    private final HttpTransport transport;
    private final JsonCodec codec;
    private final Clock clock;
    private final String signInUrl;
    private final String refreshUrl;

    private String idToken;
    private String refreshToken;
    private Instant expiresAt;

    public GoogleIdentityToolkitTokenProvider(EmailPasswordCredentials credentials,
                                              HttpTransport transport) {
        this(credentials, transport, new JsonCodec(), Clock.systemUTC(),
                DEFAULT_SIGN_IN_URL, DEFAULT_REFRESH_URL);
    }

    GoogleIdentityToolkitTokenProvider(EmailPasswordCredentials credentials,
                                       HttpTransport transport,
                                       JsonCodec codec,
                                       Clock clock,
                                       String signInUrl,
                                       String refreshUrl) {
        this.credentials = credentials;
        this.transport = transport;
        this.codec = codec;
        this.clock = clock;
        this.signInUrl = signInUrl;
        this.refreshUrl = refreshUrl;
    }

    @Override
    public synchronized String getAccessToken() {
        if (needsRefresh()) {
            try {
                if (refreshToken != null) {
                    doRefresh();
                } else {
                    doSignIn();
                }
            } catch (IOException e) {
                throw new AuthenticationException("Failed to obtain access token", e);
            }
        }
        return idToken;
    }

    @Override
    public synchronized void invalidate() {
        idToken = null;
        expiresAt = null;
        // refreshToken is kept — the next getAccessToken() call will use it
    }

    private boolean needsRefresh() {
        return idToken == null
                || expiresAt == null
                || clock.instant().isAfter(expiresAt.minus(REFRESH_SKEW));
    }

    private void doSignIn() throws IOException {
        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                credentials.email(), credentials.password());

        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(signInUrl + "?key=" + encode(credentials.apiKey()))
                .header("Content-Type", "application/json")
                .body(body.getBytes(StandardCharsets.UTF_8))
                .build();

        SdkHttpResponse response = transport.execute(request);
        if (!response.isSuccessful()) {
            throw new AuthenticationException(
                    "Sign-in failed with HTTP " + response.statusCode() + ": " + response.bodyAsString());
        }

        JsonNode node = codec.decodeTree(response.body());
        storeToken(
                node.get("idToken").asText(),
                node.get("refreshToken").asText(),
                Long.parseLong(node.get("expiresIn").asText()));
    }

    private void doRefresh() throws IOException {
        String body = "grant_type=refresh_token&refresh_token=" + encode(refreshToken);

        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(refreshUrl + "?key=" + encode(credentials.apiKey()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(body.getBytes(StandardCharsets.UTF_8))
                .build();

        SdkHttpResponse response = transport.execute(request);
        if (!response.isSuccessful()) {
            throw new AuthenticationException(
                    "Token refresh failed with HTTP " + response.statusCode() + ": " + response.bodyAsString());
        }

        JsonNode node = codec.decodeTree(response.body());
        storeToken(
                node.get("id_token").asText(),
                node.get("refresh_token").asText(),
                Long.parseLong(node.get("expires_in").asText()));
    }

    private void storeToken(String newIdToken, String newRefreshToken, long expiresInSeconds) {
        this.idToken = newIdToken;
        this.refreshToken = newRefreshToken;
        this.expiresAt = clock.instant().plusSeconds(expiresInSeconds);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
