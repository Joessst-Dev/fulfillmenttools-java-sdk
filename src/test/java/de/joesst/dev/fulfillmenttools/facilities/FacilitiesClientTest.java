package de.joesst.dev.fulfillmenttools.facilities;

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
                          "createdDate": "2024-01-01T00:00:00Z",
                          "lastModifiedDate": "2024-01-02T00:00:00Z"
                        }
                        """)));

        // When
        Facility facility = client.facilities().get("fac-1");

        // Then
        assertThat(facility.id()).isEqualTo("fac-1");
        assertThat(facility.tenantFacilityId()).isEqualTo("ext-fac-1");
        assertThat(facility.name()).isEqualTo("Berlin Warehouse");
        assertThat(facility.status()).isEqualTo("ACTIVE");
        assertThat(facility.createdDate()).isNotNull();
        assertThat(facility.lastModifiedDate()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1"))
                .willReturn(okJson("{\"id\":\"fac-1\"}")));

        // When
        client.facilities().get("fac-1");

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
        assertThatThrownBy(() -> client.facilities().get("missing"))
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
        assertThat(page.items().get(0).id()).isEqualTo("fac-1");
        assertThat(page.items().get(1).id()).isEqualTo("fac-2");
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
            ids.add(f.id());
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

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
