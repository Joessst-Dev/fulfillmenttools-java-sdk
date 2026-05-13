package de.joesst.dev.fulfillmenttools.externalactions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class ExternalActionsClientTest {

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
    void get_returnsDeserializedExternalAction() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions/ea-1"))
                .willReturn(okJson("""
                        {
                          "id": "ea-1",
                          "version": 1,
                          "name": "Notify Warehouse",
                          "processRef": "proc-1",
                          "nameLocalized": {"en_US": "Notify Warehouse"},
                          "groups": ["warehouse"],
                          "action": {"type": "BLANK_LINK", "linkUrl": "https://example.com"}
                        }
                        """)));

        // When
        ExternalAction action = client.externalActions().get(new ExternalActionId("ea-1"));

        // Then
        assertThat(action.id().value()).isEqualTo("ea-1");
        assertThat(action.name()).isEqualTo("Notify Warehouse");
        assertThat(action.processRef().value()).isEqualTo("proc-1");
        assertThat(action.groups()).containsExactly("warehouse");
        assertThat(action.version()).isEqualTo(1);
        assertThat(action.action()).isInstanceOf(ExternalLinkActionDefinition.class);
        assertThat(((ExternalLinkActionDefinition) action.action()).linkUrl()).isEqualTo("https://example.com");
    }

    @Test
    void get_deserializesFormAction() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions/ea-2"))
                .willReturn(okJson("""
                        {
                          "id": "ea-2",
                          "version": 1,
                          "name": "Fill Form",
                          "processRef": "proc-1",
                          "nameLocalized": {"en_US": "Fill Form"},
                          "groups": [],
                          "action": {
                            "type": "FORM",
                            "elements": [
                              {
                                "elementType": "TEXT_INPUT",
                                "elementId": "field-1",
                                "titleLocalized": {"en_US": "Your name"}
                              }
                            ],
                            "cancel": {"labelLocalized": {"en_US": "Cancel"}}
                          }
                        }
                        """)));

        // When
        ExternalAction action = client.externalActions().get(new ExternalActionId("ea-2"));

        // Then
        assertThat(action.action()).isInstanceOf(ExternalFormActionDefinition.class);
        ExternalFormActionDefinition form = (ExternalFormActionDefinition) action.action();
        assertThat(form.elements()).hasSize(1);
        assertThat(form.elements().get(0)).isInstanceOf(FormInputElement.class);
        assertThat(((FormInputElement) form.elements().get(0)).elementId()).isEqualTo("field-1");
    }

    @Test
    void get_deserializesCommentAction() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions/ea-3"))
                .willReturn(okJson("""
                        {
                          "id": "ea-3",
                          "version": 1,
                          "name": "Add Comment",
                          "processRef": "proc-1",
                          "nameLocalized": {"en_US": "Add Comment"},
                          "groups": [],
                          "action": {"type": "COMMENT", "isInternal": true}
                        }
                        """)));

        // When
        ExternalAction action = client.externalActions().get(new ExternalActionId("ea-3"));

        // Then
        assertThat(action.action()).isInstanceOf(ExternalCommentActionDefinition.class);
        assertThat(((ExternalCommentActionDefinition) action.action()).isInternal()).isTrue();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions/ea-1"))
                .willReturn(okJson("{\"id\":\"ea-1\"}")));

        // When
        client.externalActions().get(new ExternalActionId("ea-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/externalactions/ea-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"External action not found","description":"No external action with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.externalActions().get(new ExternalActionId("missing")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("External action not found");
    }

    // --- list ---

    @Test
    void list_returnsPageDeserializedFromRawArray() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions"))
                .willReturn(okJson("""
                        [
                          {"id":"ea-1","name":"Action A","processRef":"proc-1","groups":[]},
                          {"id":"ea-2","name":"Action B","processRef":"proc-1","groups":[]}
                        ]
                        """)));

        // When
        Page<ExternalAction> page = client.externalActions().list(
                ExternalActionListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id().value()).isEqualTo("ea-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions"))
                .willReturn(okJson("[]")));

        // When
        client.externalActions().list(ExternalActionListRequest.builder()
                .size(10)
                .startAfterId("cursor-abc")
                .processRef("proc-1")
                .groups(List.of("warehouse", "shipping"))
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/externalactions"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-abc"))
                .withQueryParam("processRef", equalTo("proc-1"))
                .withQueryParam("groups", equalTo("warehouse"))
                .withQueryParam("groups", equalTo("shipping")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/externalactions"))
                .willReturn(okJson("[{\"id\":\"ea-1\"},{\"id\":\"ea-2\"},{\"id\":\"ea-3\"}]")));

        // When
        List<String> ids = new ArrayList<>();
        for (ExternalAction ea : client.externalActions().listAll(ExternalActionListRequest.builder().build())) {
            ids.add(ea.id().value());
        }

        // Then
        assertThat(ids).containsExactly("ea-1", "ea-2", "ea-3");
    }

    // --- create ---

    @Test
    void create_returnsCreatedExternalAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/externalactions"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"ea-new\",\"name\":\"New Action\",\"processRef\":\"proc-1\",\"groups\":[]}")));

        // When
        ExternalAction action = client.externalActions().create(CreateExternalActionRequest.builder()
                .processRef("proc-1")
                .nameLocalized(Map.of("en_US", "New Action"))
                .groups(List.of())
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://example.com"))
                .build());

        // Then
        assertThat(action.id().value()).isEqualTo("ea-new");
        assertThat(action.processRef().value()).isEqualTo("proc-1");
    }

    @Test
    void create_sendsProcessRefNameLocalizedGroupsAndAction() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/externalactions"))
                .willReturn(aResponse().withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"ea-new\"}")));

        // When
        client.externalActions().create(CreateExternalActionRequest.builder()
                .processRef("proc-1")
                .nameLocalized(Map.of("en_US", "New Action"))
                .groups(List.of("warehouse"))
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://example.com"))
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/externalactions"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.processRef", equalTo("proc-1")))
                .withRequestBody(matchingJsonPath("$.nameLocalized.en_US", equalTo("New Action")))
                .withRequestBody(matchingJsonPath("$.groups[0]", equalTo("warehouse")))
                .withRequestBody(matchingJsonPath("$.action.type", equalTo("BLANK_LINK"))));
    }

    @Test
    void create_requiresProcessRef() {
        assertThatThrownBy(() -> CreateExternalActionRequest.builder()
                .nameLocalized(Map.of("en_US", "X")).groups(List.of())
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://x.com")).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("processRef");
    }

    @Test
    void create_requiresNameLocalized() {
        assertThatThrownBy(() -> CreateExternalActionRequest.builder()
                .processRef("proc-1").groups(List.of())
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://x.com")).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("nameLocalized");
    }

    @Test
    void create_requiresGroups() {
        assertThatThrownBy(() -> CreateExternalActionRequest.builder()
                .processRef("proc-1").nameLocalized(Map.of("en_US", "X"))
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://x.com")).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("groups");
    }

    @Test
    void create_requiresAction() {
        assertThatThrownBy(() -> CreateExternalActionRequest.builder()
                .processRef("proc-1").nameLocalized(Map.of("en_US", "X")).groups(List.of()).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("action");
    }

    // --- update ---

    @Test
    void update_sendsPutWithVersionAndBody() {
        // Given
        server.stubFor(put(urlPathEqualTo("/api/externalactions/ea-1"))
                .willReturn(okJson("{\"id\":\"ea-1\",\"name\":\"Updated Action\",\"processRef\":\"proc-1\",\"groups\":[]}")));

        // When
        ExternalAction action = client.externalActions().update(new ExternalActionId("ea-1"), UpdateExternalActionRequest.builder()
                .version(2)
                .nameLocalized(Map.of("en_US", "Updated Action"))
                .groups(List.of())
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://new.example.com"))
                .build());

        // Then
        assertThat(action.id().value()).isEqualTo("ea-1");
        server.verify(putRequestedFor(urlPathEqualTo("/api/externalactions/ea-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.nameLocalized.en_US", equalTo("Updated Action")))
                .withRequestBody(matchingJsonPath("$.action.type", equalTo("BLANK_LINK"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateExternalActionRequest.builder()
                .nameLocalized(Map.of("en_US", "X")).groups(List.of())
                .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://x.com")).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
