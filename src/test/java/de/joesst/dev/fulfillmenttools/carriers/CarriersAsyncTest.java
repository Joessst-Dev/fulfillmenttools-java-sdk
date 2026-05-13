package de.joesst.dev.fulfillmenttools.carriers;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.CarrierId;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class CarriersAsyncTest {

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
    void getAsync_returnsCarrier() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers/c-1"))
                .willReturn(okJson("{\"id\":\"c-1\",\"name\":\"DHL\",\"status\":\"ACTIVE\"}")));

        // When
        Carrier carrier = client.carriers().getAsync(new CarrierId("c-1")).get();

        // Then
        assertThat(carrier.id()).isEqualTo("c-1");
        assertThat(carrier.name()).isEqualTo("DHL");
    }

    @Test
    void listAsync_returnsPage() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("{\"carriers\":[{\"id\":\"c-1\",\"name\":\"DHL\"},{\"id\":\"c-2\",\"name\":\"UPS\"}]}")));

        // When
        var page = client.carriers().listAsync(CarrierListRequest.builder().build()).get();

        // Then
        assertThat(page.items()).hasSize(2);
    }

    @Test
    void createAsync_returnsCreatedCarrier() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/carriers"))
                .willReturn(okJson("{\"id\":\"c-new\",\"name\":\"FedEx\",\"status\":\"ACTIVE\"}")));

        // When
        Carrier carrier = client.carriers()
                .createAsync(CreateCarrierRequest.builder().key("fedex").name("FedEx").build()).get();

        // Then
        assertThat(carrier.id()).isEqualTo("c-new");
        assertThat(carrier.name()).isEqualTo("FedEx");
    }

    @Test
    void deleteAsync_completesWithoutException() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/carriers/c-1"))
                .willReturn(aResponse().withStatus(204)));

        // When / Then
        assertThatCode(() -> client.carriers().deleteAsync(new CarrierId("c-1")).get())
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
