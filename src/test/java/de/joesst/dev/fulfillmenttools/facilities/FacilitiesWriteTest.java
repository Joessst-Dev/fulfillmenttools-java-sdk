package de.joesst.dev.fulfillmenttools.facilities;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilitiesWriteTest {

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

    // --- create ---

    @Test
    void create_returnsCreatedFacility() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"id":"fac-new","tenantFacilityId":"ext-1","name":"Berlin","status":"ACTIVE"}
                                """)));

        // When
        Facility facility = client.facilities().create(
                CreateFacilityRequest.builder().name("Berlin").tenantFacilityId("ext-1").build());

        // Then
        assertThat(facility.id()).isEqualTo("fac-new");
        assertThat(facility.name()).isEqualTo("Berlin");
        assertThat(facility.tenantFacilityId()).isEqualTo("ext-1");
    }

    @Test
    void create_sendsJsonBodyAndBearerToken() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"fac-1\",\"name\":\"Berlin\"}")));

        // When
        client.facilities().create(CreateFacilityRequest.builder().name("Berlin").build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities"))
                .withHeader("Authorization", equalTo("Bearer test-bearer"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("Berlin"))));
    }

    @Test
    void create_requiresName() {
        // When / Then
        assertThatThrownBy(() -> CreateFacilityRequest.builder().build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("name");
    }

    // --- update ---

    @Test
    void update_returnsUpdatedFacility() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("""
                        {"id":"fac-1","name":"Munich","status":"INACTIVE"}
                        """)));

        // When
        Facility facility = client.facilities().update("fac-1",
                UpdateFacilityRequest.builder().name("Munich").status("INACTIVE").build());

        // Then
        assertThat(facility.id()).isEqualTo("fac-1");
        assertThat(facility.name()).isEqualTo("Munich");
        assertThat(facility.status()).isEqualTo("INACTIVE");
    }

    @Test
    void update_sendsJsonBodyWithPatch() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\",\"name\":\"Munich\"}")));

        // When
        client.facilities().update("fac-1", UpdateFacilityRequest.builder().name("Munich").build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/facilities/fac-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("Munich"))));
    }

    // --- delete ---

    @Test
    void delete_succeeds() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatCode(() -> client.facilities().delete("fac-1")).doesNotThrowAnyException();
    }

    @Test
    void delete_on404_throwsNotFoundException() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Facility not found","description":"No facility with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.facilities().delete("missing"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Facility not found");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
