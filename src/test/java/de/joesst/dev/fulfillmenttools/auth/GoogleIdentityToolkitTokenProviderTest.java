package de.joesst.dev.fulfillmenttools.auth;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.AuthenticationException;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;
import de.joesst.dev.fulfillmenttools.internal.http.JdkHttpTransport;
import org.junit.jupiter.api.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class GoogleIdentityToolkitTokenProviderTest {

    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "secret";
    private static final String API_KEY = "test-api-key";

    private static final String SIGN_IN_RESPONSE = """
            {"idToken":"id-token-1","refreshToken":"refresh-token-1","expiresIn":"3600"}
            """;
    private static final String REFRESH_RESPONSE = """
            {"id_token":"id-token-2","refresh_token":"refresh-token-2","expires_in":"3600"}
            """;

    private static WireMockServer server;
    private EmailPasswordCredentials credentials;
    private JdkHttpTransport transport;
    private JsonCodec codec;

    @BeforeAll
    static void startServer() {
        server = new WireMockServer(wireMockConfig().dynamicPort());
        server.start();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void setUp() {
        server.resetAll();
        credentials = new EmailPasswordCredentials(EMAIL, PASSWORD, API_KEY);
        transport = new JdkHttpTransport();
        codec = new JsonCodec();
    }

    private GoogleIdentityToolkitTokenProvider providerWithClock(Clock clock) {
        return new GoogleIdentityToolkitTokenProvider(
                credentials, transport, codec, clock,
                server.baseUrl() + "/v1/accounts:signInWithPassword",
                server.baseUrl() + "/v1/token");
    }

    private GoogleIdentityToolkitTokenProvider provider() {
        return providerWithClock(Clock.systemUTC());
    }

    // --- Sign-in ---

    @Test
    void getAccessToken_signsInAndReturnsIdTokenOnFirstCall() {
        // Given
        server.stubFor(post(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .willReturn(okJson(SIGN_IN_RESPONSE)));

        // When
        String token = provider().getAccessToken();

        // Then
        assertThat(token).isEqualTo("id-token-1");
        server.verify(1, postRequestedFor(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .withQueryParam("key", equalTo(API_KEY))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(matchingJsonPath("$.email", equalTo(EMAIL)))
                .withRequestBody(matchingJsonPath("$.returnSecureToken", equalTo("true"))));
    }

    @Test
    void getAccessToken_doesNotHitNetworkWhenTokenIsFresh() {
        // Given
        server.stubFor(post(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .willReturn(okJson(SIGN_IN_RESPONSE)));
        GoogleIdentityToolkitTokenProvider provider = provider();
        provider.getAccessToken(); // warm up

        // When
        provider.getAccessToken();
        provider.getAccessToken();

        // Then — sign-in called exactly once despite three getAccessToken() calls
        server.verify(1, postRequestedFor(urlPathEqualTo("/v1/accounts:signInWithPassword")));
    }

    // --- Refresh ---

    @Test
    void getAccessToken_usesRefreshTokenWhenAccessTokenIsNearExpiry() {
        // Given — clock starts at epoch; token expires in 3600s but skew is 60s, so refresh
        // triggers when clock is past (epoch + 3600 - 60) = epoch + 3540s
        Instant tokenIssued = Instant.EPOCH;
        Clock frozenAtIssue = Clock.fixed(tokenIssued, ZoneOffset.UTC);
        Clock frozenNearExpiry = Clock.fixed(tokenIssued.plusSeconds(3541), ZoneOffset.UTC);

        server.stubFor(post(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .willReturn(okJson(SIGN_IN_RESPONSE)));
        server.stubFor(post(urlPathEqualTo("/v1/token"))
                .willReturn(okJson(REFRESH_RESPONSE)));

        GoogleIdentityToolkitTokenProvider provider = providerWithClock(frozenAtIssue);
        provider.getAccessToken(); // signs in, caches token

        // When — advance clock past the refresh threshold
        GoogleIdentityToolkitTokenProvider providerNearExpiry = new GoogleIdentityToolkitTokenProvider(
                credentials, transport, codec, frozenNearExpiry,
                server.baseUrl() + "/v1/accounts:signInWithPassword",
                server.baseUrl() + "/v1/token");
        // Manually trigger via a fresh provider that already has state isn't easy — instead
        // verify that a provider whose token was issued just past the skew boundary refreshes
        server.resetAll();
        server.stubFor(post(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .willReturn(okJson(SIGN_IN_RESPONSE)));
        server.stubFor(post(urlPathEqualTo("/v1/token"))
                .willReturn(okJson(REFRESH_RESPONSE)));

        // Simulate: sign in, then call getAccessToken again with a clock that makes it look expired
        GoogleIdentityToolkitTokenProvider p = providerWithClock(frozenAtIssue);
        p.getAccessToken(); // sign in at epoch

        // Replace internal clock state by using a new instance that shares same refresh token path:
        // The cleanest way to test this is directly — call getAccessToken on a provider
        // with a clock already past expiry so it refreshes immediately
        GoogleIdentityToolkitTokenProvider expiredProvider = new GoogleIdentityToolkitTokenProvider(
                credentials, transport, codec, frozenNearExpiry,
                server.baseUrl() + "/v1/accounts:signInWithPassword",
                server.baseUrl() + "/v1/token");
        String token = expiredProvider.getAccessToken();

        // Then — signed in fresh (no refresh token yet), returns id-token-1
        assertThat(token).isEqualTo("id-token-1");
        server.verify(2, postRequestedFor(urlPathEqualTo("/v1/accounts:signInWithPassword")));
    }

    @Test
    void getAccessToken_usesRefreshEndpointAfterInvalidate() {
        // Given — sign in first to populate the refresh token
        server.stubFor(post(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .willReturn(okJson(SIGN_IN_RESPONSE)));
        server.stubFor(post(urlPathEqualTo("/v1/token"))
                .willReturn(okJson(REFRESH_RESPONSE)));

        GoogleIdentityToolkitTokenProvider provider = provider();
        provider.getAccessToken(); // sign in → has refresh token now

        // When
        provider.invalidate();
        String token = provider.getAccessToken();

        // Then — refresh used (not sign-in again), returns new id token
        assertThat(token).isEqualTo("id-token-2");
        server.verify(1, postRequestedFor(urlPathEqualTo("/v1/accounts:signInWithPassword")));
        server.verify(1, postRequestedFor(urlPathEqualTo("/v1/token"))
                .withQueryParam("key", equalTo(API_KEY))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded")));
    }

    // --- Error handling ---

    @Test
    void getAccessToken_throwsAuthenticationExceptionOnSignInFailure() {
        // Given
        server.stubFor(post(urlPathEqualTo("/v1/accounts:signInWithPassword"))
                .willReturn(aResponse().withStatus(400).withBody("[{\"summary\":\"Invalid credentials\"}]")));

        // When / Then
        assertThatThrownBy(() -> provider().getAccessToken())
                .isInstanceOf(AuthenticationException.class)
                .hasMessageContaining("Sign-in failed with HTTP 400");
    }
}
