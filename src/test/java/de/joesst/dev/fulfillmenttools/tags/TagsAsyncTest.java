package de.joesst.dev.fulfillmenttools.tags;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class TagsAsyncTest {

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

    @Test
    void getAsync_returnsTag() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags/color"))
                .willReturn(okJson("{\"id\":\"color\",\"version\":1,\"allowedValues\":[\"red\"]}")));

        // When
        Tag tag = client.tags().getAsync("color").get();

        // Then
        assertThat(tag.id()).isEqualTo("color");
        assertThat(tag.allowedValues()).containsExactly("red");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/tags"))
                .willReturn(okJson("""
                        {"tags":[
                          {"id":"color","version":1,"allowedValues":["red"]},
                          {"id":"size","version":1,"allowedValues":["S"]}
                        ],"total":2}
                        """)));

        // When
        Page<Tag> page = client.tags().listAsync(TagListRequest.builder().size(10).build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void createAsync_returnsTag() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"brand\",\"version\":1,\"allowedValues\":[\"nike\"]}")));

        // When
        Tag tag = client.tags().createAsync(CreateTagRequest.builder()
                .id("brand")
                .allowedValues(List.of("nike"))
                .build()).get();

        // Then
        assertThat(tag.id()).isEqualTo("brand");
    }

    @Test
    void addAllowedValueAsync_returnsUpdatedTag() throws Exception {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/tags/color"))
                .willReturn(okJson("{\"id\":\"color\",\"version\":2,\"allowedValues\":[\"red\",\"blue\"]}")));

        // When
        Tag tag = client.tags().addAllowedValueAsync("color", "blue", 1).get();

        // Then
        assertThat(tag.version()).isEqualTo(2);
        assertThat(tag.allowedValues()).contains("blue");
    }

    @Test
    void searchAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/search"))
                .willReturn(okJson("""
                        {"tags":[{"id":"color","version":1,"allowedValues":["red"]}],
                         "pageInfo":{"endCursor":"cur-1","hasNextPage":false,"hasPreviousPage":false,"startCursor":"cur-1"}}
                        """)));

        // When
        Page<Tag> page = client.tags().searchAsync(TagSearchRequest.builder().size(5).build()).get();

        // Then
        assertThat(page.items()).hasSize(1);
        assertThat(page.items().get(0).id()).isEqualTo("color");
    }

    @Test
    void needsPackingAsync_returnsResults() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/tags/packing/needspacking"))
                .willReturn(okJson("[{\"needsPacking\":true}]")));

        // When
        List<NeedsPacking> result = client.tags()
                .needsPackingAsync(List.of(new TagReference("color", "red"))).get();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).needsPacking()).isTrue();
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
