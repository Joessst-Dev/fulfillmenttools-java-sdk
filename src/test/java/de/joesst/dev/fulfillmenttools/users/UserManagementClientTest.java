package de.joesst.dev.fulfillmenttools.users;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.UserId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class UserManagementClientTest {

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

    // --- get ---

    @Test
    void get_deserializesAssignedRolesWithContextLimitations() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users/u-typed"))
                .willReturn(okJson("""
                        {
                          "id": "u-typed",
                          "version": 3,
                          "firstname": "Carol",
                          "lastname": "White",
                          "username": "carol",
                          "authenticationProvider": {"id": "prov-1", "type": "EMAIL_PASSWORD"},
                          "assignedRoles": [
                            {
                              "ref": "role-picker",
                              "context": [
                                {"type": "FACILITY", "values": ["fac-1", "fac-2"]}
                              ]
                            }
                          ]
                        }
                        """)));

        // When
        User user = client.users().get(new UserId("u-typed"));

        // Then
        assertThat(user.authenticationProvider()).isNotNull();
        assertThat(user.authenticationProvider().type()).isEqualTo("EMAIL_PASSWORD");
        assertThat(user.authenticationProvider().id()).isEqualTo("prov-1");

        assertThat(user.assignedRoles()).hasSize(1);
        AssignedRole role = user.assignedRoles().get(0);
        assertThat(role.ref()).isEqualTo("role-picker");
        assertThat(role.context()).hasSize(1);
        assertThat(role.context().get(0).type()).isEqualTo("FACILITY");
        assertThat(role.context().get(0).values()).containsExactly("fac-1", "fac-2");
    }

    @Test
    void create_sendsTypedAssignedRolesInBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/users"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"u-new\"}")));

        // When
        client.users().create(CreateUserRequest.builder()
                .username("dana")
                .password("secret99")
                .firstName("Dana")
                .lastName("Lee")
                .assignedRoles(List.of(
                        new AssignedRole("role-manager",
                                List.of(new ContextLimitation("FACILITY", List.of("fac-99"))),
                                null)))
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/users"))
                .withRequestBody(matchingJsonPath("$.assignedRoles[0].ref", equalTo("role-manager")))
                .withRequestBody(matchingJsonPath("$.assignedRoles[0].context[0].type", equalTo("FACILITY")))
                .withRequestBody(matchingJsonPath("$.assignedRoles[0].context[0].values[0]", equalTo("fac-99"))));
    }

    @Test
    void get_returnsDeserializedUser() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users/u-1"))
                .willReturn(okJson("""
                        {
                          "id": "u-1",
                          "version": 1,
                          "firstname": "Alice",
                          "lastname": "Smith",
                          "username": "alice",
                          "email": "alice@example.com",
                          "status": "ACTIVE",
                          "locale": "en_US"
                        }
                        """)));

        // When
        User user = client.users().get(new UserId("u-1"));

        // Then
        assertThat(user.id().value()).isEqualTo("u-1");
        assertThat(user.firstName()).isEqualTo("Alice");
        assertThat(user.lastName()).isEqualTo("Smith");
        assertThat(user.username()).isEqualTo("alice");
        assertThat(user.email()).isEqualTo("alice@example.com");
        assertThat(user.status()).isEqualTo("ACTIVE");
        assertThat(user.locale()).isEqualTo("en_US");
        assertThat(user.version()).isEqualTo(1);
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users/u-1"))
                .willReturn(okJson("{\"id\":\"u-1\"}")));

        // When
        client.users().get(new UserId("u-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/users/u-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"User not found","description":"No user with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.users().get(new UserId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
    }

    // --- list ---

    @Test
    void list_returnsPageDeserializedFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users"))
                .willReturn(okJson("""
                        {
                          "users": [
                            {"id":"u-1","email":"alice@example.com"},
                            {"id":"u-2","email":"bob@example.com"}
                          ],
                          "pageInfo": {"endCursor": null, "hasNextPage": false}
                        }
                        """)));

        // When
        Page<User> page = client.users().list(UserListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("u-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_returnsCursorFromPageInfo() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users"))
                .willReturn(okJson("""
                        {
                          "users": [{"id":"u-1"}],
                          "pageInfo": {"endCursor": "cursor-xyz", "hasNextPage": true}
                        }
                        """)));

        // When
        Page<User> page = client.users().list(UserListRequest.builder().build());

        // Then
        assertThat(page.nextCursor()).isEqualTo("cursor-xyz");
        assertThat(page.hasMore()).isTrue();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users"))
                .willReturn(okJson("{\"users\":[], \"pageInfo\":{\"endCursor\":null,\"hasNextPage\":false}}")));

        // When
        client.users().list(UserListRequest.builder()
                .size(10)
                .startAfterId("cursor-abc")
                .orderBy("lastname")
                .facilityId(new FacilityId("fac-1"))
                .includeAdminUsers(true)
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/users"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("orderBy", equalTo("lastname"))
                .withQueryParam("facilityId", equalTo("fac-1"))
                .withQueryParam("includeAdminUsers", equalTo("true")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/users"))
                .willReturn(okJson("{\"users\":[{\"id\":\"u-1\"},{\"id\":\"u-2\"},{\"id\":\"u-3\"}],\"pageInfo\":{\"endCursor\":null,\"hasNextPage\":false}}")));

        // When
        List<String> ids = new ArrayList<>();
        for (User u : client.users().listAll(UserListRequest.builder().build())) {
            ids.add(u.id().value());
        }

        // Then
        assertThat(ids).containsExactly("u-1", "u-2", "u-3");
    }

    // --- create ---

    @Test
    void create_returnsCreatedUser() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/users"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"u-new\",\"username\":\"alice\",\"email\":\"alice@example.com\",\"status\":\"ACTIVE\"}")));

        // When
        User user = client.users().create(CreateUserRequest.builder()
                .username("alice")
                .password("secret123")
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .build());

        // Then
        assertThat(user.id().value()).isEqualTo("u-new");
        assertThat(user.username()).isEqualTo("alice");
    }

    @Test
    void create_sendsUsernamePasswordFirstLastName() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/users"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"u-new\"}")));

        // When
        client.users().create(CreateUserRequest.builder()
                .username("alice")
                .password("secret123")
                .firstName("Alice")
                .lastName("Smith")
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/users"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("alice")))
                .withRequestBody(matchingJsonPath("$.password", equalTo("secret123")))
                .withRequestBody(matchingJsonPath("$.firstname", equalTo("Alice")))
                .withRequestBody(matchingJsonPath("$.lastname", equalTo("Smith"))));
    }

    @Test
    void create_requiresUsername() {
        assertThatThrownBy(() -> CreateUserRequest.builder()
                .password("secret").firstName("A").lastName("B").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("username");
    }

    @Test
    void create_requiresPassword() {
        assertThatThrownBy(() -> CreateUserRequest.builder()
                .username("alice").firstName("A").lastName("B").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("password");
    }

    @Test
    void create_requiresFirstName() {
        assertThatThrownBy(() -> CreateUserRequest.builder()
                .username("alice").password("secret").lastName("B").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("firstName");
    }

    @Test
    void create_requiresLastName() {
        assertThatThrownBy(() -> CreateUserRequest.builder()
                .username("alice").password("secret").firstName("A").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("lastName");
    }

    // --- update ---

    @Test
    void update_sendsPatchActionsFormat() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/users/u-1"))
                .willReturn(okJson("{\"id\":\"u-1\",\"firstname\":\"Bob\",\"lastname\":\"Smith\"}")));

        // When
        User user = client.users().update(new UserId("u-1"), UpdateUserRequest.builder()
                .version(2)
                .firstName("Bob")
                .build());

        // Then
        assertThat(user.id().value()).isEqualTo("u-1");
        server.verify(patchRequestedFor(urlPathEqualTo("/api/users/u-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyUser")))
                .withRequestBody(matchingJsonPath("$.actions[0].firstname", equalTo("Bob"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateUserRequest.builder().firstName("Bob").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- delete ---

    @Test
    void delete_sends204() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/users/u-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.users().delete(new UserId("u-1")))
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
