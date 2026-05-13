package de.joesst.dev.fulfillmenttools.facilitygroups;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilityGroupsAsyncTest {

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
    void getAsync_returnsFacilityGroup() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":1,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[\"fac-1\"],\"nameLocalized\":{\"en\":\"G1\"}}")));

        // When
        FacilityGroup group = client.facilityGroups().getAsync(new FacilityGroupId("fg-1")).get();

        // Then
        assertThat(group.id()).isEqualTo("fg-1");
        assertThat(group.tenantFacilityGroupId()).isEqualTo("tg-1");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups"))
                .willReturn(okJson("""
                        {"facilityGroups":[
                          {"id":"fg-1","version":1,"tenantFacilityGroupId":"tg-1","facilityRefs":["fac-1"],"nameLocalized":{"en":"G1"}}
                        ],"pageInfo":{"endCursor":"c2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"c1"}}
                        """)));

        // When
        Page<FacilityGroup> page = client.facilityGroups().listAsync(FacilityGroupListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.hasMore()).isTrue();
    }

    @Test
    void createAsync_returnsCreatedGroup() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups"))
                .willReturn(okJson("{\"id\":\"fg-new\",\"version\":1,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[\"fac-1\"],\"nameLocalized\":{\"en\":\"G\"}}")));

        // When
        FacilityGroup group = client.facilityGroups().createAsync(CreateFacilityGroupRequest.builder()
                .tenantFacilityGroupId("tg-1")
                .facilityRefs(List.of("fac-1"))
                .nameLocalized(Map.of("en", "G"))
                .build()).get();

        // Then
        assertThat(group.id()).isEqualTo("fg-new");
    }

    @Test
    void updateAsync_returnsUpdatedGroup() throws Exception {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":2,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[],\"nameLocalized\":{\"en\":\"Updated\"}}")));

        // When
        FacilityGroup group = client.facilityGroups().updateAsync(new FacilityGroupId("fg-1"), UpdateFacilityGroupRequest.builder()
                .version(1)
                .nameLocalized(Map.of("en", "Updated"))
                .build()).get();

        // Then
        assertThat(group.version()).isEqualTo(2);
    }

    @Test
    void deleteAsync_completes() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatNoException().isThrownBy(() -> client.facilityGroups().deleteAsync(new FacilityGroupId("fg-1")).get());
    }

    @Test
    void addFacilitiesAsync_sendsAction() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":2,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[\"fac-1\",\"fac-2\"],\"nameLocalized\":{}}")));

        // When
        FacilityGroup group = client.facilityGroups().addFacilitiesAsync(new FacilityGroupId("fg-1"), List.of(new FacilityId("fac-2")), 1).get();

        // Then
        assertThat(group.version()).isEqualTo(2);
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("ADD_FACILITIES_TO_GROUP"))));
    }

    @Test
    void searchAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/search"))
                .willReturn(okJson("""
                        {"facilityGroups":[
                          {"id":"fg-1","version":1,"tenantFacilityGroupId":"tg-1","facilityRefs":["fac-1"],"nameLocalized":{"en":"G1"}}
                        ],"pageInfo":{"endCursor":"c2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"c1"}}
                        """)));

        // When
        Page<FacilityGroup> page = client.facilityGroups().searchAsync(FacilityGroupSearchRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("c2");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
