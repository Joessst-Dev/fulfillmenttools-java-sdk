package de.joesst.dev.fulfillmenttools.carriers;

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

class CarriersClientTest {

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
    void get_returnsDeserializedCarrier() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers/c-1"))
                .willReturn(okJson("""
                        {
                          "id": "c-1",
                          "version": 1,
                          "key": "dhl",
                          "name": "DHL",
                          "status": "ACTIVE",
                          "created": "2024-01-01T00:00:00Z",
                          "lastModified": "2024-01-02T00:00:00Z"
                        }
                        """)));

        // When
        Carrier carrier = client.carriers().get("c-1");

        // Then
        assertThat(carrier.id()).isEqualTo("c-1");
        assertThat(carrier.key()).isEqualTo("dhl");
        assertThat(carrier.name()).isEqualTo("DHL");
        assertThat(carrier.status()).isEqualTo("ACTIVE");
        assertThat(carrier.version()).isEqualTo(1);
        assertThat(carrier.created()).isNotNull();
    }

    @Test
    void get_sendsBearerTokenHeader() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers/c-1"))
                .willReturn(okJson("{\"id\":\"c-1\"}")));

        // When
        client.carriers().get("c-1");

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/carriers/c-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_on404_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers/missing"))
                .willReturn(aResponse().withStatus(404).withBody("""
                        [{"summary":"Carrier not found","description":"No carrier with id missing",
                          "requestVersion":1,"version":2}]
                        """)));

        // When / Then
        assertThatThrownBy(() -> client.carriers().get("missing"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Carrier not found");
    }

    // --- list ---

    @Test
    void list_returnsPageDeserializedFromWrappedResponse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("""
                        {
                          "carriers": [
                            {"id":"c-1","name":"DHL"},
                            {"id":"c-2","name":"UPS"}
                          ]
                        }
                        """)));

        // When
        Page<Carrier> page = client.carriers().list(CarrierListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.items().get(0).id()).isEqualTo("c-1");
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("{\"carriers\":[]}")));

        // When
        client.carriers().list(CarrierListRequest.builder()
                .size(10)
                .startAfterId("cursor-xyz")
                .build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/carriers"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("cursor-xyz")));
    }

    // --- listAll ---

    @Test
    void listAll_collectsItemsFromSinglePage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("{\"carriers\":[{\"id\":\"c-1\"},{\"id\":\"c-2\"}]}")));

        // When
        List<String> ids = new ArrayList<>();
        for (Carrier c : client.carriers().listAll(CarrierListRequest.builder().build())) {
            ids.add(c.id());
        }

        // Then
        assertThat(ids).containsExactly("c-1", "c-2");
    }

    // --- create ---

    @Test
    void create_returnsCreatedCarrier() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("{\"id\":\"c-new\",\"key\":\"fedex\",\"name\":\"FedEx\",\"status\":\"ACTIVE\"}")));

        // When
        Carrier carrier = client.carriers().create(CreateCarrierRequest.builder()
                .key("fedex")
                .name("FedEx")
                .build());

        // Then
        assertThat(carrier.id()).isEqualTo("c-new");
        assertThat(carrier.key()).isEqualTo("fedex");
        assertThat(carrier.name()).isEqualTo("FedEx");
    }

    @Test
    void create_sendsKeyAndName() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("{\"id\":\"c-new\"}")));

        // When
        client.carriers().create(CreateCarrierRequest.builder()
                .key("dhl")
                .name("DHL")
                .status("ACTIVE")
                .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/carriers"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.key", equalTo("dhl")))
                .withRequestBody(matchingJsonPath("$.name", equalTo("DHL")))
                .withRequestBody(matchingJsonPath("$.status", equalTo("ACTIVE"))));
    }

    @Test
    void create_requiresKey() {
        assertThatThrownBy(() -> CreateCarrierRequest.builder().name("DHL").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("key");
    }

    @Test
    void create_requiresName() {
        assertThatThrownBy(() -> CreateCarrierRequest.builder().key("dhl").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("name");
    }

    // --- update ---

    @Test
    void update_sendsCarrierPatchActionsFormat() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/carriers/c-1"))
                .willReturn(okJson("{\"id\":\"c-1\",\"name\":\"DHL Express\",\"status\":\"ACTIVE\"}")));

        // When
        Carrier carrier = client.carriers().update("c-1", UpdateCarrierRequest.builder()
                .version(2)
                .name("DHL Express")
                .status("ACTIVE")
                .build());

        // Then
        assertThat(carrier.name()).isEqualTo("DHL Express");
        server.verify(patchRequestedFor(urlPathEqualTo("/api/carriers/c-1"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.actions[0].action", equalTo("ModifyCarrier")))
                .withRequestBody(matchingJsonPath("$.actions[0].name", equalTo("DHL Express")))
                .withRequestBody(matchingJsonPath("$.actions[0].status", equalTo("ACTIVE"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatThrownBy(() -> UpdateCarrierRequest.builder().name("DHL").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("version");
    }

    // --- delete ---

    @Test
    void delete_sends204() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/carriers/c-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.carriers().delete("c-1"))
                .doesNotThrowAnyException();
    }

    // --- Helpers ---

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
