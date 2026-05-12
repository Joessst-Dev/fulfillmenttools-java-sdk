package de.joesst.dev.fulfillmenttools.users;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class UserManagementAsyncTest {

    private static WireMockServer server;
    private FulfillmenttoolsClient client;

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
        client = FulfillmenttoolsClient.builder()
                .baseUrl(server.baseUrl())
                .tokenProvider(fixedToken("test-bearer"))
                .build();
    }

    @Test
    void getAsync_returnsUser() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users/u-1"))
                .willReturn(okJson("{\"id\":\"u-1\",\"email\":\"alice@example.com\",\"firstname\":\"Alice\",\"lastname\":\"Smith\",\"status\":\"ACTIVE\"}")));

        // When
        User user = client.users().getAsync("u-1").get();

        // Then
        assertThat(user.id()).isEqualTo("u-1");
        assertThat(user.email()).isEqualTo("alice@example.com");
        assertThat(user.firstName()).isEqualTo("Alice");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users"))
                .willReturn(okJson("{\"users\":[{\"id\":\"u-1\",\"email\":\"alice@example.com\"},{\"id\":\"u-2\",\"email\":\"bob@example.com\"}],\"pageInfo\":{\"endCursor\":null,\"hasNextPage\":false}}")));

        // When
        var page = client.users().listAsync(UserListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedUser() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/users"))
                .willReturn(okJson("{\"id\":\"u-new\",\"email\":\"charlie@example.com\",\"status\":\"ACTIVE\"}")));

        // When
        User user = client.users()
                .createAsync(CreateUserRequest.builder()
                        .username("charlie")
                        .password("secret123")
                        .firstName("Charlie")
                        .lastName("Brown")
                        .email("charlie@example.com")
                        .build()).get();

        // Then
        assertThat(user.id()).isEqualTo("u-new");
        assertThat(user.email()).isEqualTo("charlie@example.com");
    }

    @Test
    void deleteAsync_completesWithoutException() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/users/u-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.users().deleteAsync("u-1").get())
                .doesNotThrowAnyException();
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
