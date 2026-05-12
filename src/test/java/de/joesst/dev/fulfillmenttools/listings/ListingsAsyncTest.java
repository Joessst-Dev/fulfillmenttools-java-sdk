package de.joesst.dev.fulfillmenttools.listings;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ListingsAsyncTest {

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
    void bulkUpsertAsync_returnsListings() throws Exception {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/listings"))
                .willReturn(okJson("""
                        {"listings":[
                          {"id":"l-1","facilityId":"fac-1","tenantArticleId":"art-1","version":1,"status":"ACTIVE"}
                        ]}
                        """)));

        // When
        List<Listing> listings = client.listings()
                .bulkUpsertAsync(ListingBulkUpsertRequest.builder()
                        .listings(List.of(
                                ListingUpsertItem.builder().facilityId("fac-1").tenantArticleId("art-1").build()
                        ))
                        .build())
                .get();

        // Then
        assertThat(listings).hasSize(1);
        assertThat(listings.get(0).id()).isEqualTo("l-1");
        assertThat(listings.get(0).facilityId()).isEqualTo("fac-1");
        assertThat(listings.get(0).tenantArticleId()).isEqualTo("art-1");
        assertThat(listings.get(0).status()).isEqualTo("ACTIVE");
    }

    @Test
    void searchAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("""
                        {"listings":[
                          {"id":"l-1","facilityId":"fac-1","tenantArticleId":"art-1","version":1,"status":"ACTIVE"}
                        ],"pageInfo":{"endCursor":"c2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"c1"}}
                        """)));

        // When
        Page<Listing> page = client.listings()
                .searchAsync(ListingSearchRequest.builder().facilityRef("fac-1").build())
                .get();

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).tenantArticleId()).isEqualTo("art-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("c2");
    }

    @Test
    void searchAsync_sendsCorrectBody() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("{\"listings\":[]}")));

        // When
        client.listings()
                .searchAsync(ListingSearchRequest.builder().size(5).facilityRef("fac-1").build())
                .get();

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/listings/search"))
                .withRequestBody(matchingJsonPath("$.size", equalTo("5")))
                .withRequestBody(matchingJsonPath("$.facilityRef", equalTo("fac-1"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
