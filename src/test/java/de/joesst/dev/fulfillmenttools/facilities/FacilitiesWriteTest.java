package de.joesst.dev.fulfillmenttools.facilities;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
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
        assertThat(facility.id().value()).isEqualTo("fac-new");
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
        Facility facility = client.facilities().update(new FacilityId("fac-1"),
                UpdateFacilityRequest.builder().name("Munich").status("INACTIVE").build());

        // Then
        assertThat(facility.id().value()).isEqualTo("fac-1");
        assertThat(facility.name()).isEqualTo("Munich");
        assertThat(facility.status()).isEqualTo("INACTIVE");
    }

    @Test
    void update_sendsJsonBodyWithPatch() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\",\"name\":\"Munich\"}")));

        // When
        client.facilities().update(new FacilityId("fac-1"), UpdateFacilityRequest.builder().name("Munich").build());

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
        assertThatCode(() -> client.facilities().delete(new FacilityId("fac-1"))).doesNotThrowAnyException();
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
        assertThatThrownBy(() -> client.facilities().delete(new FacilityId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Facility not found");
    }

    // --- replace ---

    @Test
    void replace_sendsPUT() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\",\"name\":\"Replaced\"}")));

        // When
        Facility facility = client.facilities().replace(new FacilityId("fac-1"),
                CreateFacilityRequest.builder().name("Replaced").build());

        // Then
        assertThat(facility.name()).isEqualTo("Replaced");
        server.verify(putRequestedFor(urlPathEqualTo("/api/facilities/fac-1"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("Replaced"))));
    }

    // --- delete with forceDeletion ---

    @Test
    void delete_withForceDeletion_sendsQueryParam() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1"))
                .withQueryParam("forceDeletion", equalTo("true"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatCode(() -> client.facilities().delete(new FacilityId("fac-1"), true)).doesNotThrowAnyException();
        server.verify(deleteRequestedFor(urlPathEqualTo("/api/facilities/fac-1"))
                .withQueryParam("forceDeletion", equalTo("true")));
    }

    // --- search ---

    @Test
    void search_sendsPostToSearchEndpoint() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/search"))
                .willReturn(okJson("{\"facilities\":[{\"id\":\"fac-1\"}],\"pageInfo\":{\"endCursor\":\"c1\",\"startCursor\":\"s1\",\"hasNextPage\":true,\"hasPreviousPage\":false}}")));

        // When
        var page = client.facilities().search(
                FacilitySearchRequest.builder()
                        .query(FacilitySearchQuery.builder().statusEq("ONLINE").build())
                        .size(10)
                        .build());

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).id().value()).isEqualTo("fac-1");
        assertThat(page.nextCursor()).isEqualTo("c1");
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities/search"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void search_serializesQueryCorrectly() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/search"))
                .willReturn(okJson("{\"facilities\":[],\"pageInfo\":{\"endCursor\":null,\"startCursor\":null,\"hasNextPage\":false,\"hasPreviousPage\":false}}")));

        FacilitySearchQuery query = FacilitySearchQuery.builder()
                .statusIn("ONLINE", "SUSPENDED")
                .nameLike("Berlin*")
                .cityEq("Berlin")
                .countryEq("DE")
                .typeEq("MANAGED_FACILITY")
                .build();

        // When
        client.facilities().search(FacilitySearchRequest.builder().query(query).size(25).build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities/search"))
                .withRequestBody(matchingJsonPath("$.query.status.in[0]", equalTo("ONLINE")))
                .withRequestBody(matchingJsonPath("$.query.status.in[1]", equalTo("SUSPENDED")))
                .withRequestBody(matchingJsonPath("$.query.name.like", equalTo("Berlin*")))
                .withRequestBody(matchingJsonPath("$.query.address.city.eq", equalTo("Berlin")))
                .withRequestBody(matchingJsonPath("$.query.address.country.eq", equalTo("DE")))
                .withRequestBody(matchingJsonPath("$.query.type.eq", equalTo("MANAGED_FACILITY")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("25"))));
    }

    // --- list query params ---

    @Test
    void list_sendsStatusAndTypeQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .willReturn(okJson("{\"facilities\":[]}")));

        // When
        client.facilities().list(FacilityListRequest.builder()
                .status(java.util.List.of("ONLINE", "SUSPENDED"))
                .type(java.util.List.of("MANAGED_FACILITY"))
                .tenantFacilityId("ext-1")
                .orderBy("NAME")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities"))
                .withQueryParam("status", equalTo("ONLINE"))
                .withQueryParam("status", equalTo("SUSPENDED"))
                .withQueryParam("type", equalTo("MANAGED_FACILITY"))
                .withQueryParam("tenantFacilityId", equalTo("ext-1"))
                .withQueryParam("orderBy", equalTo("NAME")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
