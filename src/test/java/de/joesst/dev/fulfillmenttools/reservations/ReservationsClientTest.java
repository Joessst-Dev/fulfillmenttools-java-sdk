package de.joesst.dev.fulfillmenttools.reservations;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.ReservationId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ReservationsClientTest {

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
    void get_returnsDeserializedReservation() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-1"))
                .willReturn(okJson("""
                        {
                          "id": "res-1",
                          "facilityRef": "fac-1",
                          "tenantArticleId": "art-1",
                          "quantity": 5,
                          "created": "2024-03-01T10:00:00Z"
                        }
                        """)));

        // When
        Reservation reservation = client.reservations().get(new ReservationId("res-1"));

        // Then
        assertThat(reservation.id()).isEqualTo("res-1");
        assertThat(reservation.facilityRef()).isEqualTo("fac-1");
        assertThat(reservation.tenantArticleId()).isEqualTo("art-1");
        assertThat(reservation.quantity()).isEqualTo(5);
        assertThat(reservation.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-1"))
                .willReturn(okJson("{\"id\":\"res-1\"}")));

        // When
        client.reservations().get(new ReservationId("res-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/reservations/res-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Reservation not found","description":"No reservation with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.reservations().get(new ReservationId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Reservation not found");
    }

    // --- list ---

    @Test
    void list_returnsPageWithItemsAndCursor() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations"))
                .willReturn(okJson("""
                        {
                          "reservations": [
                            {"id":"res-1","facilityRef":"fac-1","tenantArticleId":"art-1","quantity":1},
                            {"id":"res-2","facilityRef":"fac-1","tenantArticleId":"art-2","quantity":2}
                          ],
                          "pageInfo": {
                            "endCursor": "cursor-page-2",
                            "hasNextPage": true,
                            "hasPreviousPage": false,
                            "startCursor": ""
                          },
                          "total": 10
                        }
                        """)));

        // When
        Page<Reservation> page = client.reservations().list(
                ReservationListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("res-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations"))
                .willReturn(okJson("{\"reservations\":[],\"pageInfo\":{\"endCursor\":null,\"hasNextPage\":false,\"hasPreviousPage\":false,\"startCursor\":\"\"}}")));

        // When
        client.reservations().list(ReservationListRequest.builder()
                .size(10).after("cursor-abc").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/reservations"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("after", equalTo("cursor-abc")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"reservations":[
                          {"id":"res-1","quantity":1},
                          {"id":"res-2","quantity":2}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":""}}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/reservations"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("after", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"reservations":[{"id":"res-3","quantity":3}],
                         "pageInfo":{"endCursor":null,"hasNextPage":false,"hasPreviousPage":true,"startCursor":""}}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (Reservation r : client.reservations().listAll(
                ReservationListRequest.builder().size(2).build())) {
            ids.add(r.id());
        }

        // Then
        assertThat(ids).containsExactly("res-1", "res-2", "res-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/reservations")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
