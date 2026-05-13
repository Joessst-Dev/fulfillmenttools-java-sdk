package de.joesst.dev.fulfillmenttools.facilities;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilitiesClientTest {

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
    void get_returnsDeserializedFacility() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("""
                        {
                          "id": "fac-1",
                          "tenantFacilityId": "ext-fac-1",
                          "name": "Berlin Warehouse",
                          "status": "ACTIVE",
                          "created": "2024-01-01T00:00:00Z",
                          "lastModified": "2024-01-02T00:00:00Z"
                        }
                        """)));

        // When
        Facility facility = client.facilities().get(new FacilityId("fac-1"));

        // Then
        assertThat(facility.id().value()).isEqualTo("fac-1");
        assertThat(facility.tenantFacilityId()).isEqualTo("ext-fac-1");
        assertThat(facility.name()).isEqualTo("Berlin Warehouse");
        assertThat(facility.status()).isEqualTo("ACTIVE");
        assertThat(facility.created()).isNotNull();
        assertThat(facility.lastModified()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\"}")));

        // When
        client.facilities().get(new FacilityId("fac-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Facility not found","description":"No facility with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.facilities().get(new FacilityId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Facility not found");
    }

    // --- list ---

    @Test
    void list_returnsPageWithItemsAndCursor() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .willReturn(okJson("""
                        {
                          "facilities": [
                            {"id":"fac-1","name":"Berlin"},
                            {"id":"fac-2","name":"Munich"}
                          ],
                          "nextCursor": "cursor-page-2"
                        }
                        """)));

        // When
        Page<Facility> page = client.facilities().list(FacilityListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("fac-1");
        assertThat(page.items().get(1).id().value()).isEqualTo("fac-2");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-page-2");
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .willReturn(okJson("{\"facilities\":[]}")));

        // When
        client.facilities().list(FacilityListRequest.builder().size(5).startAfterId("cursor-abc").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities"))
                .withQueryParam("size", equalTo("5"))
                .withQueryParam("startAfterId", equalTo("cursor-abc")));
    }

    @Test
    void list_withNoCursor_hasMoreIsFalse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .willReturn(okJson("{\"facilities\":[{\"id\":\"fac-1\"}]}")));

        // When
        Page<Facility> page = client.facilities().list(FacilityListRequest.builder().build());

        // Then
        assertThat(page.hasMore()).isFalse();
        assertThat(page.nextCursor()).isNull();
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given — page 1 has 2 facilities and a cursor; page 2 has 1 facility and no cursor
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"facilities":[{"id":"fac-1"},{"id":"fac-2"}],"nextCursor":"cur-2"}
                        """)));

        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .withQueryParam("size", equalTo("2"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"facilities":[{"id":"fac-3"}]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        for (Facility f : client.facilities().listAll(FacilityListRequest.builder().size(2).build())) {
            ids.add(f.id().value());
        }

        // Then
        assertThat(ids).containsExactly("fac-1", "fac-2", "fac-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/facilities")));
    }

    @Test
    void listAll_doesNotFetchExtraPageAfterLastCursor() {
        // Given — single page, no cursor
        server.stubFor(get(urlPathEqualTo("/api/facilities"))
                .willReturn(okJson("{\"facilities\":[{\"id\":\"fac-1\"}]}")));

        // When
        List<Facility> facilities = new ArrayList<>();
        client.facilities().listAll(FacilityListRequest.builder().build()).forEach(facilities::add);

        // Then
        assertThat(facilities).hasSize(1);
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/facilities")));
    }

    // --- new fields ---

    @Test
    void get_deserializesNestedFacilityFields() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-rich"))
                .willReturn(okJson("""
                        {
                          "id": "fac-rich",
                          "name": "Hamburg Store",
                          "status": "ONLINE",
                          "type": "MANAGED_FACILITY",
                          "locationType": "STORE",
                          "capacityEnabled": true,
                          "fulfillmentProcessBuffer": 120,
                          "contact": {
                            "firstName": "Max",
                            "lastName": "Mustermann",
                            "roleDescription": "Manager"
                          },
                          "services": [{"type": "SHIP_FROM_STORE"}, {"type": "PICKUP"}],
                          "pickingMethods": ["SINGLE_ORDER", "BATCH"],
                          "operativeCosts": [{"value": 25000, "currency": "EUR", "decimalPlaces": 2}],
                          "tags": [{"id": "tag-1", "value": "premium"}],
                          "address": {
                            "street": "Hauptstr.",
                            "houseNumber": "1",
                            "city": "Hamburg",
                            "postalCode": "20095",
                            "country": "DE",
                            "companyName": "Boxales GmbH",
                            "phoneNumbers": [{"value": "+49123456", "type": "PHONE", "label": "main"}],
                            "emailAddresses": [{"value": "store@example.com", "recipient": "Store Team"}],
                            "resolvedCoordinates": {"lat": 53.5511, "lon": 9.9937},
                            "resolvedTimeZone": {
                              "timeZoneId": "Europe/Berlin",
                              "timeZoneName": "Central European Time",
                              "offsetInSeconds": 3600
                            }
                          },
                          "pickingTimes": {
                            "monday": [{"start": {"hour": 8, "minute": 0}, "end": {"hour": 20, "minute": 0}}]
                          },
                          "closingDays": [{"date": "2025-12-25T00:00:00Z", "reason": "Christmas", "recurrence": "YEARLY"}],
                          "scanningRule": {
                            "values": [{"priority": 1.0, "scanningRuleType": "ARTICLE"}]
                          },
                          "configs": [{"ref": "cfg-1", "rel": "carrier"}]
                        }
                        """)));

        // When
        Facility facility = client.facilities().get(new FacilityId("fac-rich"));

        // Then
        assertThat(facility.locationType()).isEqualTo("STORE");
        assertThat(facility.capacityEnabled()).isTrue();
        assertThat(facility.fulfillmentProcessBuffer()).isEqualTo(120);
        assertThat(facility.contact().firstName()).isEqualTo("Max");
        assertThat(facility.contact().lastName()).isEqualTo("Mustermann");
        assertThat(facility.services()).hasSize(2);
        assertThat(facility.services().get(0).type()).isEqualTo("SHIP_FROM_STORE");
        assertThat(facility.pickingMethods()).containsExactly("SINGLE_ORDER", "BATCH");
        assertThat(facility.operativeCosts()).hasSize(1);
        assertThat(facility.operativeCosts().get(0).currency()).isEqualTo("EUR");
        assertThat(facility.tags()).hasSize(1);
        assertThat(facility.tags().get(0).value()).isEqualTo("premium");
        assertThat(facility.address().city()).isEqualTo("Hamburg");
        assertThat(facility.address().phoneNumbers()).hasSize(1);
        assertThat(facility.address().phoneNumbers().get(0).value()).isEqualTo("+49123456");
        assertThat(facility.address().emailAddresses().get(0).value()).isEqualTo("store@example.com");
        assertThat(facility.address().resolvedCoordinates().lat()).isEqualTo(53.5511);
        assertThat(facility.address().resolvedTimeZone().timeZoneId()).isEqualTo("Europe/Berlin");
        assertThat(facility.pickingTimes().monday()).hasSize(1);
        assertThat(facility.pickingTimes().monday().get(0).start().hour()).isEqualTo(8);
        assertThat(facility.closingDays()).hasSize(1);
        assertThat(facility.closingDays().get(0).reason()).isEqualTo("Christmas");
        assertThat(facility.scanningRule().values().get(0).scanningRuleType()).isEqualTo("ARTICLE");
        assertThat(facility.configs().get(0).rel()).isEqualTo("carrier");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
