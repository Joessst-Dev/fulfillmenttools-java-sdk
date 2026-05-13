package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

class FacilityDiscountsClientTest {

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
    void get_returnsDeserializedDiscount() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(okJson(relativeDiscountJson("disc-1", "fac-1", 1))));

        // When
        FacilityDiscount discount = client.facilityDiscounts().get(new FacilityId("fac-1"), new FacilityDiscountId("disc-1"));

        // Then
        assertThat(discount.id().value()).isEqualTo("disc-1");
        assertThat(discount.facilityRef().value()).isEqualTo("fac-1");
        assertThat(discount.version()).isEqualTo(1);
        assertThat(discount.type()).isEqualTo("SALES_PRICE");
        assertThat(discount.priority()).isEqualTo(1);
        assertThat(discount.discount()).isInstanceOf(FacilityDiscountValue.Relative.class);
        assertThat(((FacilityDiscountValue.Relative) discount.discount()).value()).isEqualTo(7.5);
    }

    @Test
    void get_sendsBearerToken() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(okJson(relativeDiscountJson("disc-1", "fac-1", 1))));

        // When
        client.facilityDiscounts().get(new FacilityId("fac-1"), new FacilityDiscountId("disc-1"));

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .withHeader("Authorization", equalTo("Bearer test-bearer")));
    }

    @Test
    void get_notFound_throwsNotFoundException() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts/missing"))
                .willReturn(aResponse().withStatus(404)));

        // Then
        assertThatThrownBy(() -> client.facilityDiscounts().get(new FacilityId("fac-1"), new FacilityDiscountId("missing")))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void get_absoluteDiscount_isDeserialized() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-abs"))
                .willReturn(okJson(absoluteDiscountJson("disc-abs", "fac-1", 1))));

        // When
        FacilityDiscount discount = client.facilityDiscounts().get(new FacilityId("fac-1"), new FacilityDiscountId("disc-abs"));

        // Then
        assertThat(discount.discount()).isInstanceOf(FacilityDiscountValue.Absolute.class);
        FacilityDiscountValue.Absolute abs = (FacilityDiscountValue.Absolute) discount.discount();
        assertThat(abs.values()).hasSize(1);
        assertThat(abs.values().get(0).currency()).isEqualTo("EUR");
        assertThat(abs.values().get(0).value()).isEqualTo(500);
        assertThat(abs.values().get(0).decimalPlaces()).isEqualTo(2);
    }

    // --- list ---

    @Test
    void list_returnsDeserializedPage() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(okJson("""
                        {"items":[%s,%s],"total":5}
                        """.formatted(relativeDiscountJson("d1", "fac-1", 1),
                                      relativeDiscountJson("d2", "fac-1", 1)))));

        // When
        Page<FacilityDiscount> page = client.facilityDiscounts()
                .list(new FacilityId("fac-1"), FacilityDiscountListRequest.builder().size(2).build());

        // Then
        assertThat(page.items()).hasSize(2);
        assertThat(page.hasMore()).isTrue();
        assertThat(page.nextCursor()).isEqualTo("d2");
    }

    @Test
    void list_lastPage_hasMoreFalse() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(okJson("""
                        {"items":[%s],"total":1}
                        """.formatted(relativeDiscountJson("d1", "fac-1", 1)))));

        // When
        Page<FacilityDiscount> page = client.facilityDiscounts()
                .list(new FacilityId("fac-1"), FacilityDiscountListRequest.builder().size(10).build());

        // Then
        assertThat(page.hasMore()).isFalse();
    }

    @Test
    void list_sendsQueryParams() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(okJson("{\"items\":[],\"total\":0}")));

        // When
        client.facilityDiscounts().list(new FacilityId("fac-1"),
                FacilityDiscountListRequest.builder().size(10).startAfterId("d0").build());

        // Then
        server.verify(getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .withQueryParam("size", equalTo("10"))
                .withQueryParam("startAfterId", equalTo("d0")));
    }

    // --- listAll ---

    @Test
    void listAll_lazilyTraversesMultiplePages() {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .withQueryParam("size", equalTo("2"))
                .willReturn(okJson("""
                        {"items":[%s,%s],"total":3}
                        """.formatted(relativeDiscountJson("d1", "fac-1", 1),
                                      relativeDiscountJson("d2", "fac-1", 1)))));
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .withQueryParam("startAfterId", equalTo("d2"))
                .willReturn(okJson("""
                        {"items":[%s],"total":3}
                        """.formatted(relativeDiscountJson("d3", "fac-1", 1)))));

        // When
        List<String> ids = new ArrayList<>();
        client.facilityDiscounts()
                .listAll(new FacilityId("fac-1"), FacilityDiscountListRequest.builder().size(2).build())
                .forEach(d -> ids.add(d.id().value()));

        // Then
        assertThat(ids).containsExactly("d1", "d2", "d3");
        server.verify(2, getRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts")));
    }

    // --- create ---

    @Test
    void create_returnsCreatedDiscount() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody(relativeDiscountJson("disc-new", "fac-1", 1))));

        // When
        FacilityDiscount discount = client.facilityDiscounts().create(new FacilityId("fac-1"),
                CreateFacilityDiscountRequest.builder()
                        .type("SALES_PRICE")
                        .priority(1)
                        .discount(FacilityDiscountValue.Relative.of(7.5))
                        .build());

        // Then
        assertThat(discount.id().value()).isEqualTo("disc-new");
    }

    @Test
    void create_sendsCorrectBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody(relativeDiscountJson("disc-new", "fac-1", 1))));

        // When
        client.facilityDiscounts().create(new FacilityId("fac-1"),
                CreateFacilityDiscountRequest.builder()
                        .type("SALES_PRICE")
                        .priority(2)
                        .discount(FacilityDiscountValue.Relative.of(10.0))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .withRequestBody(matchingJsonPath("$.type", equalTo("SALES_PRICE")))
                .withRequestBody(matchingJsonPath("$.priority", equalTo("2")))
                .withRequestBody(matchingJsonPath("$.discount.type", equalTo("RELATIVE")))
                .withRequestBody(matchingJsonPath("$.discount.value", equalTo("10.0"))));
    }

    @Test
    void create_requiresType() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateFacilityDiscountRequest.builder()
                        .priority(1)
                        .discount(FacilityDiscountValue.Relative.of(5.0))
                        .build()
        ).withMessageContaining("type");
    }

    @Test
    void create_requiresPriority() {
        assertThatNullPointerException().isThrownBy(() ->
                CreateFacilityDiscountRequest.builder()
                        .type("SALES_PRICE")
                        .discount(FacilityDiscountValue.Relative.of(5.0))
                        .build()
        ).withMessageContaining("priority");
    }

    @Test
    void create_withAbsoluteDiscount_sendsCorrectBody() {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody(absoluteDiscountJson("disc-abs", "fac-1", 1))));

        // When
        client.facilityDiscounts().create(new FacilityId("fac-1"),
                CreateFacilityDiscountRequest.builder()
                        .type("SALES_PRICE")
                        .priority(1)
                        .discount(FacilityDiscountValue.Absolute.of(
                                List.of(new FacilityDiscountAbsoluteElement(500, "EUR", 2))))
                        .build());

        // Then
        server.verify(postRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .withRequestBody(matchingJsonPath("$.discount.type", equalTo("ABSOLUTE")))
                .withRequestBody(matchingJsonPath("$.discount.values[0].currency", equalTo("EUR")))
                .withRequestBody(matchingJsonPath("$.discount.values[0].value", equalTo("500"))));
    }

    // --- update ---

    @Test
    void update_sendsCorrectPatchBody() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(okJson(relativeDiscountJson("disc-1", "fac-1", 2))));

        // When
        FacilityDiscount updated = client.facilityDiscounts().update(new FacilityId("fac-1"), new FacilityDiscountId("disc-1"),
                UpdateFacilityDiscountRequest.builder()
                        .version(1)
                        .priority(3)
                        .build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1")))
                .withRequestBody(matchingJsonPath("$.priority", equalTo("3"))));
        assertThat(updated.version()).isEqualTo(2);
    }

    @Test
    void update_omitsNullFields() {
        // Given
        server.stubFor(patch(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(okJson(relativeDiscountJson("disc-1", "fac-1", 2))));

        // When
        client.facilityDiscounts().update(new FacilityId("fac-1"), new FacilityDiscountId("disc-1"),
                UpdateFacilityDiscountRequest.builder().version(1).build());

        // Then
        server.verify(patchRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .withRequestBody(matchingJsonPath("$.version", equalTo("1")))
                .withRequestBody(not(matchingJsonPath("$.type")))
                .withRequestBody(not(matchingJsonPath("$.priority")))
                .withRequestBody(not(matchingJsonPath("$.discount"))));
    }

    @Test
    void update_requiresVersion() {
        assertThatNullPointerException().isThrownBy(() ->
                UpdateFacilityDiscountRequest.builder().priority(1).build()
        ).withMessageContaining("version");
    }

    // --- delete ---

    @Test
    void delete_sendsDeleteRequest() {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatNoException().isThrownBy(() ->
                client.facilityDiscounts().delete(new FacilityId("fac-1"), new FacilityDiscountId("disc-1")));
        server.verify(deleteRequestedFor(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1")));
    }

    // --- async ---

    @Test
    void getAsync_returnsDiscount() throws Exception {
        // Given
        server.stubFor(get(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(okJson(relativeDiscountJson("disc-1", "fac-1", 1))));

        // When
        FacilityDiscount discount = client.facilityDiscounts().getAsync(new FacilityId("fac-1"), new FacilityDiscountId("disc-1")).get();

        // Then
        assertThat(discount.id().value()).isEqualTo("disc-1");
    }

    @Test
    void createAsync_returnsDiscount() throws Exception {
        // Given
        server.stubFor(post(urlPathEqualTo("/api/facilities/fac-1/discounts"))
                .willReturn(aResponse().withStatus(201).withHeader("Content-Type", "application/json")
                        .withBody(relativeDiscountJson("disc-new", "fac-1", 1))));

        // When
        FacilityDiscount discount = client.facilityDiscounts().createAsync(new FacilityId("fac-1"),
                CreateFacilityDiscountRequest.builder()
                        .type("SALES_PRICE").priority(1)
                        .discount(FacilityDiscountValue.Relative.of(5.0))
                        .build()).get();

        // Then
        assertThat(discount.id().value()).isEqualTo("disc-new");
    }

    @Test
    void deleteAsync_completes() throws Exception {
        // Given
        server.stubFor(delete(urlPathEqualTo("/api/facilities/fac-1/discounts/disc-1"))
                .willReturn(aResponse().withStatus(200)));

        // When / Then
        assertThatNoException().isThrownBy(() ->
                client.facilityDiscounts().deleteAsync(new FacilityId("fac-1"), new FacilityDiscountId("disc-1")).get());
    }

    // --- Helpers ---

    private static String relativeDiscountJson(String id, String facilityRef, int version) {
        return """
                {"id":"%s","facilityRef":"%s","version":%d,"type":"SALES_PRICE","priority":1,
                 "discount":{"type":"RELATIVE","value":7.5}}
                """.formatted(id, facilityRef, version);
    }

    private static String absoluteDiscountJson(String id, String facilityRef, int version) {
        return """
                {"id":"%s","facilityRef":"%s","version":%d,"type":"SALES_PRICE","priority":1,
                 "discount":{"type":"ABSOLUTE","values":[{"value":500,"currency":"EUR","decimalPlaces":2}]}}
                """.formatted(id, facilityRef, version);
    }

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
