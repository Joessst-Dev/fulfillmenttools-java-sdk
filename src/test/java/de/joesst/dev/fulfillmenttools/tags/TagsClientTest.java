package de.joesst.dev.fulfillmenttools.tags;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.TagId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class TagsClientTest {

    private static WireMockServer server;
    private FulfillmenttoolsClient client;

    @BeforeAll
    static void startServer() {
        server = new WireMockServer(wireMockConfig().dynamicPort());
        server.start();
    }

    @AfterAll
    static void stopServer() { server.stop(); }

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
    void get_returnsDeserializedTag() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags/color"))
                .willReturn(okJson("""
                        {"id":"color","version":1,"allowedValues":["red","green","blue"]}
                        """)));

        // When
        Tag tag = client.tags().get(new TagId("color"));

        // Then
        assertThat(tag.id().value()).isEqualTo("color");
        assertThat(tag.version()).isEqualTo(1);
        assertThat(tag.allowedValues()).containsExactly("red", "green", "blue");
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags/color"))
                .willReturn(okJson("{\"id\":\"color\",\"version\":1,\"allowedValues\":[]}")));

        // When
        client.tags().get(new TagId("color"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/tags/color"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_notFound_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags/missing"))
                .willReturn(aResponse().withStatus(404)));

        // Then
        assertThatThrownBy(() -> client.tags().get(new TagId("missing")))
                .isInstanceOf(NotFoundException.class);
    }

    // --- list ---

    @Test
    void list_returnsDeserializedPage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags"))
                .willReturn(okJson("""
                        {
                          "tags": [
                            {"id":"color","version":1,"allowedValues":["red"]},
                            {"id":"size","version":1,"allowedValues":["S","M","L"]}
                          ],
                          "total": 5
                        }
                        """)));

        // When
        Page<Tag> page = client.tags().list(TagListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("color");
        assertThat(page.items().get(1).id().value()).isEqualTo("size");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("size");
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags"))
                .willReturn(okJson("{\"tags\":[]}")));

        // When
        client.tags().list(TagListRequest.builder().size(10).startAfterId("cur-abc").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/tags"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cur-abc")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"tags":[
                          {"id":"tag-1","version":1,"allowedValues":[]},
                          {"id":"tag-2","version":1,"allowedValues":[]}
                        ],"total":3}
                        """)));
        server.stubFor(get(urlPathEqualTo("/api/tags"))
                .withQueryParam("startAfterId", equalTo("tag-2"))
                .willReturn(okJson("""
                        {"tags":[
                          {"id":"tag-3","version":1,"allowedValues":[]}
                        ],"total":3}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        client.tags().listAll(TagListRequest.builder().size(2).build()).forEach(t -> ids.add(t.id().value()));

        // Then
        assertThat(ids).containsExactly("tag-1", "tag-2", "tag-3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/tags")));
    }

    // --- create ---

    @Test
    void create_returnsCreatedTag() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"material\",\"version\":1,\"allowedValues\":[\"cotton\",\"wool\"]}")));

        // When
        Tag tag = client.tags().create(CreateTagRequest.builder()
                .id("material")
                .allowedValues(List.of("cotton", "wool"))
                .build());

        // Then
        assertThat(tag.id().value()).isEqualTo("material");
        assertThat(tag.allowedValues()).containsExactly("cotton", "wool");
    }

    @Test
    void create_sendsCorrectBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"color\",\"version\":1,\"allowedValues\":[\"red\"]}")));

        // When
        client.tags().create(CreateTagRequest.builder()
                .id("color")
                .allowedValues(List.of("red"))
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/tags"))
                .withRequestBody(matchingJsonPath("$.id", equalTo("color")))
                .withRequestBody(matchingJsonPath("$.allowedValues[0]", equalTo("red"))));
    }

    @Test
    void create_requiresId() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateTagRequest.builder().allowedValues(List.of("red")).build()
        ).withMessageContaining("id");
    }

    @Test
    void create_requiresAllowedValues() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateTagRequest.builder().id("color").build()
        ).withMessageContaining("allowedValues");
    }

    // --- addAllowedValue ---

    @Test
    void addAllowedValue_sendsCorrectPatchBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/tags/color"))
                .willReturn(okJson("{\"id\":\"color\",\"version\":2,\"allowedValues\":[\"red\",\"blue\"]}")));

        // When
        Tag tag = client.tags().addAllowedValue(new TagId("color"), "blue", 1);

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/tags/color"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("AddAllowedValueToTag")))
                .withRequestBody(matchingJsonPath("$.actions[0].allowedValue", equalTo("blue"))));
        assertThat(tag.version()).isEqualTo(2);
        assertThat(tag.allowedValues()).contains("blue");
    }

    // --- search ---

    @Test
    void search_returnsDeserializedPage() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/search"))
                .willReturn(okJson("""
                        {
                          "tags": [
                            {"id":"color","version":1,"allowedValues":["red","blue"]}
                          ],
                          "pageInfo": {"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}
                        }
                        """)));

        // When
        Page<Tag> page = client.tags().search(TagSearchRequest.builder().size(10).build());

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).id().value()).isEqualTo("color");
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("cur-2");
    }

    @Test
    void search_sendsTypedQuery() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/search"))
                .willReturn(okJson("{\"tags\":[]}")));

        // When
        client.tags().search(TagSearchRequest.builder()
                .query(TagSearchQuery.builder().idEq("color").build())
                .size(5)
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/tags/search"))
                .withRequestBody(matchingJsonPath("$.query.id.eq", equalTo("color")))
                .withRequestBody(matchingJsonPath("$.size", equalTo("5"))));
    }

    @Test
    void searchAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/search"))
                .withRequestBody(notMatching(".*\"after\".*"))
                .willReturn(okJson("""
                        {"tags":[
                          {"id":"tag-1","version":1,"allowedValues":[]},
                          {"id":"tag-2","version":1,"allowedValues":[]}
                        ],"pageInfo":{"endCursor":"cur-2","hasNextPage":true,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));
        server.stubFor(post(urlPathEqualTo("/api/tags/search"))
                .withRequestBody(matchingJsonPath("$.after", equalTo("cur-2")))
                .willReturn(okJson("""
                        {"tags":[
                          {"id":"tag-3","version":1,"allowedValues":[]}
                        ]}
                        """)));

        // When
        List<String> ids = new ArrayList<>();
        client.tags().searchAll(TagSearchRequest.builder().build()).forEach(t -> ids.add(t.id().value()));

        // Then
        assertThat(ids).containsExactly("tag-1", "tag-2", "tag-3");
        server.verify(2, postRequestedFor(urlPathEqualTo("/api/tags/search")));
    }

    // --- needsPacking ---

    @Test
    void needsPacking_returnsResults() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/packing/needspacking"))
                .willReturn(okJson("[{\"needsPacking\":true},{\"needsPacking\":false}]")));

        // When
        List<NeedsPacking> result = client.tags().needsPacking(List.of(
                new TagReference("color", "red"),
                new TagReference("size", "XL")));

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).needsPacking()).isTrue();
        assertThat(result.get(1).needsPacking()).isFalse();
    }

    @Test
    void needsPacking_sendsTagRefsAsArray() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/packing/needspacking"))
                .willReturn(okJson("[{\"needsPacking\":true}]")));

        // When
        client.tags().needsPacking(List.of(new TagReference("color", "red")));

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/tags/packing/needspacking"))
                .withRequestBody(matchingJsonPath("$[0].id", equalTo("color")))
                .withRequestBody(matchingJsonPath("$[0].value", equalTo("red"))));
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
