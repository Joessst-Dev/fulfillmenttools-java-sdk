package de.joesst.dev.fulfillmenttools.reservations;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
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
        Reservation reservation = client.reservations().get("res-1");

        // Then
        assertThat(reservation.id()).isEqualTo("res-1");
        assertThat(reservation.facilityRef()).isEqualTo("fac-1");
        assertThat(reservation.tenantArticleId()).isEqualTo("art-1");
        assertThat(reservation.quantity()).isEqualTo(5);
        assertThat(reservation.created()).isNotNull();
    }

    @Test
    void get_deserializesHostWithTypedEnum() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-2"))
                .willReturn(okJson("""
                        {
                          "id": "res-2",
                          "facilityRef": "fac-1",
                          "tenantArticleId": "art-1",
                          "quantity": 3,
                          "version": 1,
                          "host": {
                            "reference": "stock-ref-99",
                            "type": "STOCK"
                          }
                        }
                        """)));

        // When
        Reservation reservation = client.reservations().get("res-2");

        // Then
        assertThat(reservation.host()).isNotNull();
        assertThat(reservation.host().reference()).isEqualTo("stock-ref-99");
        assertThat(reservation.host().type()).isEqualTo(HostType.STOCK);
    }

    @Test
    void get_deserializesExpectedStockHostType() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-3"))
                .willReturn(okJson("""
                        {
                          "id": "res-3",
                          "facilityRef": "fac-2",
                          "tenantArticleId": "art-2",
                          "quantity": 1,
                          "host": {
                            "reference": "expected-stock-ref-7",
                            "type": "EXPECTED_STOCK"
                          }
                        }
                        """)));

        // When
        Reservation reservation = client.reservations().get("res-3");

        // Then
        assertThat(reservation.host().type()).isEqualTo(HostType.EXPECTED_STOCK);
        assertThat(reservation.host().reference()).isEqualTo("expected-stock-ref-7");
    }

    @Test
    void get_deserializesNoneHostType() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-4"))
                .willReturn(okJson("""
                        {
                          "id": "res-4",
                          "facilityRef": "fac-3",
                          "tenantArticleId": "art-3",
                          "quantity": 2,
                          "host": {
                            "reference": "none-ref",
                            "type": "NONE"
                          }
                        }
                        """)));

        // When
        Reservation reservation = client.reservations().get("res-4");

        // Then
        assertThat(reservation.host().type()).isEqualTo(HostType.NONE);
    }

    @Test
    void get_deserializesRelatedRefs() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-5"))
                .willReturn(okJson("""
                        {
                          "id": "res-5",
                          "facilityRef": "fac-1",
                          "tenantArticleId": "art-1",
                          "quantity": 10,
                          "relatedRefs": {
                            "orderRefs": ["order-1", "order-2"],
                            "pickJobRefs": ["pj-1"],
                            "processRefs": [],
                            "routingPlanRefs": ["rp-1"],
                            "transferRefs": ["tr-1", "tr-2"]
                          }
                        }
                        """)));

        // When
        Reservation reservation = client.reservations().get("res-5");

        // Then
        assertThat(reservation.relatedRefs()).isNotNull();
        assertThat(reservation.relatedRefs().orderRefs()).containsExactly("order-1", "order-2");
        assertThat(reservation.relatedRefs().pickJobRefs()).containsExactly("pj-1");
        assertThat(reservation.relatedRefs().processRefs()).isEmpty();
        assertThat(reservation.relatedRefs().routingPlanRefs()).containsExactly("rp-1");
        assertThat(reservation.relatedRefs().transferRefs()).containsExactly("tr-1", "tr-2");
    }

    @Test
    void get_deserializesNullRelatedRefArraysAsNull() {
        // Given — relatedRefs present but only some arrays populated
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-6"))
                .willReturn(okJson("""
                        {
                          "id": "res-6",
                          "facilityRef": "fac-1",
                          "tenantArticleId": "art-1",
                          "quantity": 1,
                          "relatedRefs": {
                            "orderRefs": ["order-3"]
                          }
                        }
                        """)));

        // When
        Reservation reservation = client.reservations().get("res-6");

        // Then
        assertThat(reservation.relatedRefs().orderRefs()).containsExactly("order-3");
        assertThat(reservation.relatedRefs().pickJobRefs()).isNull();
        assertThat(reservation.relatedRefs().processRefs()).isNull();
        assertThat(reservation.relatedRefs().routingPlanRefs()).isNull();
        assertThat(reservation.relatedRefs().transferRefs()).isNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/reservations/res-1"))
                .willReturn(okJson("{\"id\":\"res-1\"}")));

        // When
        client.reservations().get("res-1");

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
        assertThatThrownBy(() -> client.reservations().get("missing"))
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
