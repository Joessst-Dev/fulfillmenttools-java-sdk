package de.joesst.dev.fulfillmenttools.reservations;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ReservationsAsyncTest {

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
    void getAsync_returnsReservation() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-1"))
                .willReturn(okJson("{\"id\":\"res-1\",\"quantity\":3,\"status\":\"OPEN\"}")));

        // When
        Reservation reservation = client.reservations().getAsync("res-1").get();

        // Then
        assertThat(reservation.id()).isEqualTo("res-1");
        assertThat(reservation.quantity()).isEqualTo(3);
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations"))
                .willReturn(okJson("{\"reservations\":[{\"id\":\"res-1\",\"quantity\":1},{\"id\":\"res-2\",\"quantity\":2}]}")));

        // When
        var page = client.reservations().listAsync(ReservationListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedReservation() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/reservations"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"res-new\",\"quantity\":5}")));

        // When
        Reservation reservation = client.reservations().createAsync(
                CreateReservationRequest.builder()
                        .facilityRef("fac-1").tenantArticleId("art-1").quantity(5).build()).get();

        // Then
        assertThat(reservation.id()).isEqualTo("res-new");
        assertThat(reservation.quantity()).isEqualTo(5);
    }

    @Test
    void deleteAsync_completesWithoutException() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/reservations/res-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatCode(() -> client.reservations().deleteAsync("res-1").get())
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
