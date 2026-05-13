package de.joesst.dev.fulfillmenttools.facilitygroups;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilityGroupsClientTest {

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
    void get_returnsDeserializedFacilityGroup() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(okJson("""
                        {"id":"fg-1","version":1,"tenantFacilityGroupId":"tenant-fg-1",
                         "facilityRefs":["fac-1","fac-2"],"nameLocalized":{"en":"Group 1","de":"Gruppe 1"}}
                        """)));

        // When
        FacilityGroup group = client.facilityGroups().get(new FacilityGroupId("fg-1"));

        // Then
        assertThat(group.id()).isEqualTo("fg-1");
        assertThat(group.version()).isEqualTo(1);
        assertThat(group.tenantFacilityGroupId()).isEqualTo("tenant-fg-1");
        assertThat(group.facilityRefs()).containsExactly("fac-1", "fac-2");
        assertThat(group.nameLocalized()).containsEntry("en", "Group 1");
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":1,\"tenantFacilityGroupId\":\"t\",\"facilityRefs\":[],\"nameLocalized\":{}}")));

        // When
        client.facilityGroups().get(new FacilityGroupId("fg-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_notFound_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups/missing"))
                .willReturn(aResponse().withStatus(404)));

        // Then
        assertThatThrownBy(() -> client.facilityGroups().get(new FacilityGroupId("missing")))
                .isInstanceOf(NotFoundException.class);
    }

    // --- list ---

    @Test
    void list_returnsDeserializedPage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups"))
                .willReturn(okJson("""
                        {
                          "facilityGroups": [
                            {"id":"fg-1","version":1,"tenantFacilityGroupId":"t1","facilityRefs":["fac-1"],"nameLocalized":{"en":"G1"}},
                            {"id":"fg-2","version":1,"tenantFacilityGroupId":"t2","facilityRefs":["fac-2"],"nameLocalized":{"en":"G2"}}
                          ],
                          "total": 2,
                          "pageInfo": {"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}
                        }
                        """)));

        // When
        Page<FacilityGroup> page = client.facilityGroups().list(FacilityGroupListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("fg-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cur-2");
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups"))
                .willReturn(okJson("{\"facilityGroups\":[]}")));

        // When
        client.facilityGroups().list(FacilityGroupListRequest.builder().size(10).startAfterId("cur-abc").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilitygroups"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cur-abc")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"facilityGroups":[
                          {"id":"fg-1","version":1,"tenantFacilityGroupId":"t1","facilityRefs":[],"nameLocalized":{}},
                          {"id":"fg-2","version":1,"tenantFacilityGroupId":"t2","facilityRefs":[],"nameLocalized":{}}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));
        server.stubFor(get(urlPathEqualTo("/api/facilitygroups"))
                .withQueryParam("startAfterId", equalTo("cur-2"))
                .willReturn(okJson("""
                        {"facilityGroups":[
                          {"id":"fg-3","version":1,"tenantFacilityGroupId":"t3","facilityRefs":[],"nameLocalized":{}}
                        ]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        client.facilityGroups().listAll(FacilityGroupListRequest.builder().size(2).build()).forEach(g -> ids.add(g.id()));

        // Then
        assertThat(ids).containsExactly("fg-1", "fg-2", "fg-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/facilitygroups")));
    }

    // --- create ---

    @Test
    void create_returnsCreatedFacilityGroup() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups"))
                .willReturn(okJson("""
                        {"id":"fg-new","version":1,"tenantFacilityGroupId":"tg-1",
                         "facilityRefs":["fac-1"],"nameLocalized":{"en":"New Group"}}
                        """)));

        // When
        FacilityGroup group = client.facilityGroups().create(CreateFacilityGroupRequest.builder()
                .tenantFacilityGroupId("tg-1")
                .facilityRefs(List.of("fac-1"))
                .nameLocalized(Map.of("en", "New Group"))
                .build());

        // Then
        assertThat(group.id()).isEqualTo("fg-new");
        assertThat(group.tenantFacilityGroupId()).isEqualTo("tg-1");
    }

    @Test
    void create_sendsCorrectBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":1,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[],\"nameLocalized\":{}}")));

        // When
        client.facilityGroups().create(CreateFacilityGroupRequest.builder()
                .tenantFacilityGroupId("tg-1")
                .facilityRefs(List.of("fac-1"))
                .nameLocalized(Map.of("en", "My Group"))
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups"))
                .withRequestBody(matchingJsonPath("$.tenantFacilityGroupId", equalTo("tg-1")))
                .withRequestBody(matchingJsonPath("$.nameLocalized.en", equalTo("My Group")))
                .withRequestBody(matchingJsonPath("$.facilityRefs[0]", equalTo("fac-1"))));
    }

    @Test
    void create_requiresTenantFacilityGroupId() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateFacilityGroupRequest.builder()
                        .facilityRefs(List.of("fac-1"))
                        .nameLocalized(Map.of("en", "G"))
                        .build()
        ).withMessageContaining("tenantFacilityGroupId");
    }

    @Test
    void create_requiresFacilityRefs() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateFacilityGroupRequest.builder()
                        .tenantFacilityGroupId("tg-1")
                        .nameLocalized(Map.of("en", "G"))
                        .build()
        ).withMessageContaining("facilityRefs");
    }

    @Test
    void create_requiresNameLocalized() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateFacilityGroupRequest.builder()
                        .tenantFacilityGroupId("tg-1")
                        .facilityRefs(List.of("fac-1"))
                        .build()
        ).withMessageContaining("nameLocalized");
    }

    // --- update ---

    @Test
    void update_sendsCorrectBody() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":2,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[\"fac-1\"],\"nameLocalized\":{\"en\":\"Updated\"}}")));

        // When
        FacilityGroup group = client.facilityGroups().update(new FacilityGroupId("fg-1"), UpdateFacilityGroupRequest.builder()
                .version(1)
                .nameLocalized(Map.of("en", "Updated"))
                .build());

        // Then
        server.verify(putRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1")))
                .withRequestBody(matchingJsonPath("$.nameLocalized.en", equalTo("Updated"))));
        assertThat(group.version()).isEqualTo(2);
    }

    @Test
    void update_requiresVersion() {
        assertThatNullPointerException().isThrownBy(() ->
                UpdateFacilityGroupRequest.builder().build()
        ).withMessageContaining("version");
    }

    // --- delete ---

    @Test
    void delete_sendsDeleteRequest() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilitygroups/fg-1"))
                .willReturn(aResponse().withStatus(204)));

        // When
        client.facilityGroups().delete(new FacilityGroupId("fg-1"));

        // Then
        server.verify(deleteRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1")));
    }

    // --- addFacilities ---

    @Test
    void addFacilities_withVersion_sendsVersionedAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":2,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[\"fac-1\",\"fac-2\"],\"nameLocalized\":{}}")));

        // When
        client.facilityGroups().addFacilities(new FacilityGroupId("fg-1"), List.of(new FacilityId("fac-2")), 1);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("ADD_FACILITIES_TO_GROUP")))
                .withRequestBody(matchingJsonPath("$.facilitiesToAdd[0]", equalTo("fac-2")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1"))));
    }

    @Test
    void addFacilities_withoutVersion_sendsVersionlessAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":1,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[\"fac-1\"],\"nameLocalized\":{}}")));

        // When
        client.facilityGroups().addFacilities(new FacilityGroupId("fg-1"), List.of(new FacilityId("fac-1")), null);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("VERSIONLESS_ADD_FACILITIES_TO_GROUP"))));
    }

    // --- removeFacilities ---

    @Test
    void removeFacilities_withVersion_sendsVersionedAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":2,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[],\"nameLocalized\":{}}")));

        // When
        client.facilityGroups().removeFacilities(new FacilityGroupId("fg-1"), List.of(new FacilityId("fac-1")), 1);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("REMOVE_FACILITIES_FROM_GROUP")))
                .withRequestBody(matchingJsonPath("$.facilitiesToRemove[0]", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1"))));
    }

    @Test
    void removeFacilities_withoutVersion_sendsVersionlessAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .willReturn(okJson("{\"id\":\"fg-1\",\"version\":1,\"tenantFacilityGroupId\":\"tg-1\",\"facilityRefs\":[],\"nameLocalized\":{}}")));

        // When
        client.facilityGroups().removeFacilities(new FacilityGroupId("fg-1"), List.of(new FacilityId("fac-1")), null);

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/fg-1/actions"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("VERSIONLESS_REMOVE_FACILITIES_FROM_GROUP"))));
    }

    // --- search ---

    @Test
    void search_returnsDeserializedPage() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/search"))
                .willReturn(okJson("""
                        {
                          "facilityGroups": [
                            {"id":"fg-1","version":1,"tenantFacilityGroupId":"tg-1","facilityRefs":["fac-1"],"nameLocalized":{"en":"G1"}}
                          ],
                          "pageInfo": {"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}
                        }
                        """)));

        // When
        Page<FacilityGroup> page = client.facilityGroups().search(FacilityGroupSearchRequest.builder().size(10).build());

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).id()).isEqualTo("fg-1");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cur-2");
    }

    @Test
    void search_sendsCorrectBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/search"))
                .willReturn(okJson("{\"facilityGroups\":[]}")));

        // When
        client.facilityGroups().search(FacilityGroupSearchRequest.builder()
                .size(5)
                .after("cur-abc")
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/search"))
                .withRequestBody(matchingJsonPath("$.size", equalTo("5")))
                .withRequestBody(matchingJsonPath("$.after", equalTo("cur-abc"))));
    }

    @Test
    void search_sendsTypedQuery() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/search"))
                .willReturn(okJson("{\"facilityGroups\":[]}")));

        // When
        client.facilityGroups().search(FacilityGroupSearchRequest.builder()
                .query(FacilityGroupSearchQuery.builder()
                        .tenantFacilityGroupIdEq("tg-1")
                        .facilityRefsContains("fac-1")
                        .build())
                .size(10)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilitygroups/search"))
                .withRequestBody(matchingJsonPath("$.query.tenantFacilityGroupId.eq", equalTo("tg-1")))
                .withRequestBody(matchingJsonPath("$.query.facilityRefs.contains", equalTo("fac-1")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("10"))));
    }

    @Test
    void searchAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/search"))
                .withRequestBody(notMatching(".*\"after\".*"))
                .willReturn(okJson("""
                        {"facilityGroups":[
                          {"id":"fg-1","version":1,"tenantFacilityGroupId":"t1","facilityRefs":[],"nameLocalized":{}},
                          {"id":"fg-2","version":1,"tenantFacilityGroupId":"t2","facilityRefs":[],"nameLocalized":{}}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));
        server.stubFor(post(urlPathEqualTo("/api/facilitygroups/search"))
                .withRequestBody(matchingJsonPath("$.after", equalTo("cur-2")))
                .willReturn(okJson("""
                        {"facilityGroups":[
                          {"id":"fg-3","version":1,"tenantFacilityGroupId":"t3","facilityRefs":[],"nameLocalized":{}}
                        ]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        client.facilityGroups().searchAll(FacilityGroupSearchRequest.builder().build()).forEach(g -> ids.add(g.id()));

        // Then
        assertThat(ids).containsExactly("fg-1", "fg-2", "fg-3");
        server.verify(2, postRequestedFor(urlPathEqualTo("/api/facilitygroups/search")));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
