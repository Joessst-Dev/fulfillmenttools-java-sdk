package de.joesst.dev.fulfillmenttools.facilities;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilitiesAsyncTest {

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
    void getAsync_returnsFacility() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\",\"name\":\"Berlin\",\"status\":\"ACTIVE\"}")));

        // When
        Facility facility = client.facilities().getAsync("fac-1").get();

        // Then
        assertThat(facility.id()).isEqualTo("fac-1");
        assertThat(facility.name()).isEqualTo("Berlin");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .willReturn(okJson("{\"facilities\":[{\"id\":\"fac-1\"},{\"id\":\"fac-2\"}],\"nextCursor\":\"c2\"}")));

        // When
        var page = client.facilities().listAsync(FacilityListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.hasMore()).isTrue();
    }

    @Test
    void createAsync_returnsCreatedFacility() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"fac-new\",\"name\":\"Hamburg\"}")));

        // When
        Facility facility = client.facilities()
                .createAsync(CreateFacilityRequest.builder().name("Hamburg").build()).get();

        // Then
        assertThat(facility.id()).isEqualTo("fac-new");
    }

    @Test
    void updateAsync_returnsUpdatedFacility() throws Exception {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\",\"status\":\"INACTIVE\"}")));

        // When
        Facility facility = client.facilities()
                .updateAsync("fac-1", UpdateFacilityRequest.builder().status("INACTIVE").build()).get();

        // Then
        assertThat(facility.status()).isEqualTo("INACTIVE");
    }

    @Test
    void deleteAsync_completesWithoutException() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatCode(() -> client.facilities().deleteAsync("fac-1").get())
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
