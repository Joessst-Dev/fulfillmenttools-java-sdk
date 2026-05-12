package de.joesst.dev.fulfillmenttools.listings;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ListingsClientTest {

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

    // --- bulkUpsert ---

    @Test
    void bulkUpsert_returnsDeserializedListings() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/listings"))
                .willReturn(okJson("""
                        {
                          "listings": [
                            {"id":"l-1","version":1,"facilityRef":"fac-1","tenantArticleId":"art-1","title":"Article 1"},
                            {"id":"l-2","version":1,"facilityRef":"fac-1","tenantArticleId":"art-2","title":"Article 2"}
                          ]
                        }
                        """)));

        // When
        List<Listing> listings = client.listings().bulkUpsert(ListingBulkUpsertRequest.builder()
                .listings(List.of(
                        ListingUpsertItem.builder().facilityRef("fac-1").tenantArticleId("art-1").title("Article 1").build(),
                        ListingUpsertItem.builder().facilityRef("fac-1").tenantArticleId("art-2").title("Article 2").build()
                ))
                .build());

        // Then
        assertThat(listings).hasSize(2);
        assertThat(listings.get(0).id()).isEqualTo("l-1");
        assertThat(listings.get(0).facilityRef()).isEqualTo("fac-1");
        assertThat(listings.get(0).tenantArticleId()).isEqualTo("art-1");
        assertThat(listings.get(0).title()).isEqualTo("Article 1");
        assertThat(listings.get(1).id()).isEqualTo("l-2");
    }

    @Test
    void bulkUpsert_sendsPutWithBearerToken() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/listings"))
                .willReturn(okJson("{\"listings\":[]}")));

        // When
        client.listings().bulkUpsert(ListingBulkUpsertRequest.builder()
                .listings(List.of(
                        ListingUpsertItem.builder().facilityRef("fac-1").tenantArticleId("art-1").build()
                ))
                .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/listings"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void bulkUpsert_sendsCorrectJsonBody() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/listings"))
                .willReturn(okJson("{\"listings\":[]}")));

        // When
        client.listings().bulkUpsert(ListingBulkUpsertRequest.builder()
                .listings(List.of(
                        ListingUpsertItem.builder()
                                .facilityRef("fac-1")
                                .tenantArticleId("art-1")
                                .title("My Article")
                                .build()
                ))
                .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/listings"))
                .withRequestBody(matchingJsonPath("$.listings[0].facilityRef", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.listings[0].tenantArticleId", equalTo("art-1")))
                .withRequestBody(matchingJsonPath("$.listings[0].title", equalTo("My Article"))));
    }

    @Test
    void bulkUpsert_emptyResponse_returnsEmptyList() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/listings"))
                .willReturn(okJson("{\"listings\":[]}")));

        // When
        List<Listing> listings = client.listings().bulkUpsert(ListingBulkUpsertRequest.builder()
                .listings(List.of(
                        ListingUpsertItem.builder().facilityRef("fac-1").tenantArticleId("art-1").build()
                ))
                .build());

        // Then
        assertThat(listings).isEmpty();
    }

    @Test
    void listingUpsertItem_requiresFacilityRef() {
        assertThatNullPointerException().isThrownBy(() ->
                ListingUpsertItem.builder().tenantArticleId("art-1").build()
        ).withMessageContaining("facilityRef");
    }

    @Test
    void listingUpsertItem_requiresTenantArticleId() {
        assertThatNullPointerException().isThrownBy(() ->
                ListingUpsertItem.builder().facilityRef("fac-1").build()
        ).withMessageContaining("tenantArticleId");
    }

    @Test
    void listingBulkUpsertRequest_requiresListings() {
        assertThatNullPointerException().isThrownBy(() ->
                ListingBulkUpsertRequest.builder().build()
        ).withMessageContaining("listings");
    }

    // --- search ---

    @Test
    void search_returnsDeserializedPage() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("""
                        {
                          "listings": [
                            {"id":"l-1","facilityRef":"fac-1","tenantArticleId":"art-1","title":"Article 1","version":1},
                            {"id":"l-2","facilityRef":"fac-1","tenantArticleId":"art-2","title":"Article 2","version":2}
                          ],
                          "total": 2,
                          "pageInfo": {"endCursor":"cursor-2","hasNextPage":false,"hasPreviousPage":false,"startCursor":"cursor-1"}
                        }
                        """)));

        // When
        Page<Listing> page = client.listings().search(ListingSearchRequest.builder()
                .facilityRef("fac-1")
                .size(10)
                .build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("l-1");
        assertThat(page.items().get(0).tenantArticleId()).isEqualTo("art-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cursor-2");
    }

    @Test
    void search_sendsCorrectJsonBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("{\"listings\":[]}")));

        // When
        client.listings().search(ListingSearchRequest.builder()
                .facilityRef("fac-1")
                .tenantArticleId("art-1")
                .size(5)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/listings/search"))
                .withRequestBody(matchingJsonPath("$.facilityRef", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.tenantArticleId", equalTo("art-1")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("5"))));
    }

    @Test
    void search_sendsBearerTokenHeader() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("{\"listings\":[]}")));

        // When
        client.listings().search(ListingSearchRequest.builder().build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/listings/search"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void search_withHasMore_returnsNextCursor() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("""
                        {
                          "listings": [{"id":"l-1","facilityRef":"fac-1","tenantArticleId":"art-1","version":1}],
                          "pageInfo": {"endCursor":"cur-next","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-start"}
                        }
                        """)));

        // When
        Page<Listing> page = client.listings().search(ListingSearchRequest.builder().build());

        // Then
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cur-next");
    }

    @Test
    void search_withNoPageInfo_hasMoreIsFalse() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("{\"listings\":[{\"id\":\"l-1\",\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"version\":1}]}")));

        // When
        Page<Listing> page = client.listings().search(ListingSearchRequest.builder().build());

        // Then
        assertThat(page.hasMore()).isFalse();
        assertThat(page.nextCursor()).isNull();
    }

    // --- searchAll ---

    @Test
    void searchAll_lazilyTraversesMultiplePages() {
        // Given — page 1 has 2 listings and a cursor; page 2 has 1 listing and no cursor
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .withRequestBody(matchingJsonPath("$.size", equalTo("2")))
                .withRequestBody(notMatching(".*startAfterId.*"))
                .willReturn(okJson("""
                        {"listings":[
                          {"id":"l-1","facilityRef":"fac-1","tenantArticleId":"art-1","version":1},
                          {"id":"l-2","facilityRef":"fac-1","tenantArticleId":"art-2","version":1}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));

        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .withRequestBody(matchingJsonPath("$.startAfterId", equalTo("cur-2")))
                .willReturn(okJson("""
                        {"listings":[
                          {"id":"l-3","facilityRef":"fac-1","tenantArticleId":"art-3","version":1}
                        ]}
                        """)));

        // When
        List<String> articleIds = new ArrayList<>();
        for (Listing l : client.listings().searchAll(ListingSearchRequest.builder().size(2).build())) {
            articleIds.add(l.tenantArticleId());
        }

        // Then
        assertThat(articleIds).containsExactly("art-1", "art-2", "art-3");
        server.verify(2, postRequestedFor(urlPathEqualTo("/api/listings/search")));
    }

    @Test
    void searchAll_doesNotFetchExtraPageAfterLastCursor() {
        // Given — single page, no pageInfo
        server.stubFor(post(urlPathEqualTo("/api/listings/search"))
                .willReturn(okJson("{\"listings\":[{\"id\":\"l-1\",\"facilityRef\":\"fac-1\",\"tenantArticleId\":\"art-1\",\"version\":1}]}")));

        // When
        List<Listing> items = new ArrayList<>();
        client.listings().searchAll(ListingSearchRequest.builder().build()).forEach(items::add);

        // Then
        assertThat(items).hasSize(1);
        server.verify(1, postRequestedFor(urlPathEqualTo("/api/listings/search")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
