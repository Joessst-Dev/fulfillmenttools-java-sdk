package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.id.RatingResultType;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.*;

/**
 * BDD tests verifying that the sourcing option response types introduced in this PR
 * deserialize correctly from JSON, covering the novel paths:
 * <ul>
 *   <li>{@link NodeType} enum and typed node IDs</li>
 *   <li>{@link SourcingOptionsTransferDeliveryCost} with {@code @JsonCreator}/{@code @JsonProperty} flat Money composition</li>
 *   <li>Typed rating result fields ({@link de.joesst.dev.fulfillmenttools.id.RatingResultId},
 *       {@link RatingResultType}, {@link de.joesst.dev.fulfillmenttools.id.RoutingStrategyNodeId})</li>
 *   <li>{@link SourcingOptionCosts} with nested {@link ShippingCosts} and {@link Money}</li>
 * </ul>
 */
class SourcingOptionDeserializationTest {

    private static WireMockServer server;
    private FulfillmenttoolsClient client;

    private static final String ENDPOINT = "/api/routing/sourcingoptions";

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

    // -------------------------------------------------------------------------
    // Nodes
    // -------------------------------------------------------------------------

    @Nested
    class WhenResponseContainsNodes {

        @Test
        void evaluate_deserializesNodeTypeEnumAndTypedIds() {
            // Given
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1",
                          "runId": "run-1",
                          "totalPenalty": 0.0,
                          "nodes": [{
                            "id": "node-1",
                            "type": "MANAGED_FACILITY",
                            "facilityRef": "fac-1",
                            "tenantFacilityId": "tf-1",
                            "isPickUpLocation": false,
                            "lineItems": [{"tenantArticleId": "art-1", "quantity": 2.0}]
                          }],
                          "transfers": [],
                          "ratingResults": []
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            SourcingOptionNode node = result.options().get(0).nodes().get(0);
            assertThat(node.id().value()).isEqualTo("node-1");
            assertThat(node.type()).isEqualTo(NodeType.MANAGED_FACILITY);
            assertThat(node.facilityRef().value()).isEqualTo("fac-1");
            assertThat(node.tenantFacilityId().value()).isEqualTo("tf-1");
            assertThat(node.isPickUpLocation()).isFalse();
            assertThat(node.lineItems()).hasSize(1);
            assertThat(node.lineItems().get(0).tenantArticleId().value()).isEqualTo("art-1");
            assertThat(node.lineItems().get(0).quantity()).isEqualTo(2.0);
        }

        @Test
        void evaluate_deserializesAllNodeTypeEnumValues() {
            // Given
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1", "runId": "run-1", "totalPenalty": 0.0,
                          "nodes": [
                            {"id": "n1", "type": "SUPPLIER",          "lineItems": []},
                            {"id": "n2", "type": "MANAGED_FACILITY",  "lineItems": []},
                            {"id": "n3", "type": "CUSTOMER",          "lineItems": []}
                          ],
                          "transfers": [], "ratingResults": []
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            var nodes = result.options().get(0).nodes();
            assertThat(nodes.get(0).type()).isEqualTo(NodeType.SUPPLIER);
            assertThat(nodes.get(1).type()).isEqualTo(NodeType.MANAGED_FACILITY);
            assertThat(nodes.get(2).type()).isEqualTo(NodeType.CUSTOMER);
        }
    }

    // -------------------------------------------------------------------------
    // Transfers — DeliveryCost @JsonCreator + @JsonProperty flat Money path
    // -------------------------------------------------------------------------

    @Nested
    class WhenResponseContainsTransfers {

        @Test
        void evaluate_deserializesDeliveryCostWithFlatMoneyFields() {
            // Given — the API emits value/currency/decimalPlaces at the same level as
            // deliveryCostCoefficient (allOf: [Money] in the OpenAPI spec)
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1", "runId": "run-1", "totalPenalty": 0.0,
                          "nodes": [], "ratingResults": [],
                          "transfers": [{
                            "sourceNodeRef": "node-src",
                            "targetNodeRef": "node-tgt",
                            "carrier": {"carrierKey": "DHL", "carrierName": "DHL Express"},
                            "packagingInformation": [{
                              "packagingUnit": {
                                "name": "Small Box",
                                "priority": 1,
                                "prices": [{
                                  "value": 4.99,
                                  "currency": "EUR",
                                  "decimalPlaces": 2.0,
                                  "deliveryCostCoefficient": {
                                    "value": 0.001,
                                    "measurementUnit": "GRAM"
                                  }
                                }]
                              },
                              "totalCosts": {"value": 4.99, "currency": "EUR"},
                              "packedItems": [{"tenantArticleId": "art-1", "quantity": 1.0}]
                            }]
                          }]
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            SourcingOptionTransfer transfer = result.options().get(0).transfers().get(0);
            assertThat(transfer.sourceNodeRef().value()).isEqualTo("node-src");
            assertThat(transfer.targetNodeRef().value()).isEqualTo("node-tgt");
            assertThat(transfer.carrier().carrierKey()).isEqualTo("DHL");
            assertThat(transfer.carrier().carrierName()).isEqualTo("DHL Express");

            SourcingOptionsTransferPackagingInformation packaging = transfer.packagingInformation().get(0);
            assertThat(packaging.totalCosts().value()).isEqualTo(4.99);
            assertThat(packaging.totalCosts().currency()).isEqualTo("EUR");
            assertThat(packaging.packedItems()).hasSize(1);
            assertThat(packaging.packedItems().get(0).tenantArticleId().value()).isEqualTo("art-1");

            SourcingOptionsTransferDeliveryCost deliveryCost = packaging.packagingUnit().prices().get(0);
            assertThat(deliveryCost.money().value()).isEqualTo(4.99);
            assertThat(deliveryCost.money().currency()).isEqualTo("EUR");
            assertThat(deliveryCost.money().decimalPlaces()).isEqualTo(2.0);
            assertThat(deliveryCost.deliveryCostCoefficient().value()).isEqualTo(0.001);
            assertThat(deliveryCost.deliveryCostCoefficient().measurementUnit())
                    .isEqualTo(CostCoefficientMeasurementUnit.GRAM);
        }

        @Test
        void evaluate_deserializesDeliveryCostWithoutOptionalCoefficient() {
            // Given
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1", "runId": "run-1", "totalPenalty": 0.0,
                          "nodes": [], "ratingResults": [],
                          "transfers": [{
                            "sourceNodeRef": "n1", "targetNodeRef": "n2",
                            "packagingInformation": [{
                              "packagingUnit": {
                                "name": "Box",
                                "prices": [{"value": 3.50, "currency": "USD"}]
                              }
                            }]
                          }]
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            SourcingOptionsTransferDeliveryCost cost = result.options().get(0)
                    .transfers().get(0).packagingInformation().get(0).packagingUnit().prices().get(0);
            assertThat(cost.money().value()).isEqualTo(3.50);
            assertThat(cost.money().currency()).isEqualTo("USD");
            assertThat(cost.deliveryCostCoefficient()).isNull();
        }
    }

    // -------------------------------------------------------------------------
    // Rating results — typed ID wrappers
    // -------------------------------------------------------------------------

    @Nested
    class WhenResponseContainsRatingResults {

        @Test
        void evaluate_deserializesTypedRatingResultFields() {
            // Given
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1", "runId": "run-1", "totalPenalty": 15.5,
                          "nodes": [], "transfers": [],
                          "ratingResults": [{
                            "id": "rating-1",
                            "name": "DISTANCE",
                            "penalty": 15.5,
                            "type": "StandardRating",
                            "routingStrategyNodeId": "rsn-1"
                          }]
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            SourcingOptionRatingResult rating = result.options().get(0).ratingResults().get(0);
            assertThat(rating.id().value()).isEqualTo("rating-1");
            assertThat(rating.name()).isEqualTo("DISTANCE");
            assertThat(rating.penalty()).isEqualTo(15.5);
            assertThat(rating.type()).isEqualTo(new RatingResultType("StandardRating"));
            assertThat(rating.routingStrategyNodeId().value()).isEqualTo("rsn-1");
        }

        @Test
        void evaluate_deserializesToolkitRatingType() {
            // Given
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1", "runId": "run-1", "totalPenalty": 5.0,
                          "nodes": [], "transfers": [],
                          "ratingResults": [{
                            "id": "rating-2",
                            "name": "CUSTOM",
                            "penalty": 5.0,
                            "type": "ToolkitRating",
                            "routingStrategyNodeId": "rsn-2"
                          }]
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            SourcingOptionRatingResult rating = result.options().get(0).ratingResults().get(0);
            assertThat(rating.type()).isEqualTo(new RatingResultType("ToolkitRating"));
            assertThat(rating.routingStrategyNodeId().value()).isEqualTo("rsn-2");
        }
    }

    // -------------------------------------------------------------------------
    // Total costs
    // -------------------------------------------------------------------------

    @Nested
    class WhenResponseContainsTotalCosts {

        @Test
        void evaluate_deserializesTotalCostsWithShippingBreakdown() {
            // Given
            server.stubFor(post(urlPathEqualTo(ENDPOINT)).willReturn(okJson("""
                    {
                      "id": "run-1",
                      "result": {
                        "options": [{
                          "id": "opt-1", "runId": "run-1", "totalPenalty": 0.0,
                          "nodes": [], "transfers": [], "ratingResults": [],
                          "totalCosts": {
                            "totalCosts":           {"value": 10.0, "currency": "EUR"},
                            "totalSalesPriceAmount": {"value": 50.0, "currency": "EUR"},
                            "totalShippingCosts": {
                              "totalTransportCostAmount": {"value": 4.99, "currency": "EUR"},
                              "packageCosts": [{
                                "packageName": "Small Box",
                                "costAmount": {"value": 4.99, "currency": "EUR"}
                              }]
                            }
                          }
                        }]
                      }
                    }
                    """)));

            // When
            SourcingOptionsResult result = client.sourcingOptions().evaluate(minimalRequest());

            // Then
            SourcingOptionCosts costs = result.options().get(0).totalCosts();
            assertThat(costs.totalCosts().value()).isEqualTo(10.0);
            assertThat(costs.totalCosts().currency()).isEqualTo("EUR");
            assertThat(costs.totalSalesPriceAmount().value()).isEqualTo(50.0);

            ShippingCosts shipping = costs.totalShippingCosts();
            assertThat(shipping.totalTransportCostAmount().value()).isEqualTo(4.99);
            assertThat(shipping.totalTransportCostAmount().currency()).isEqualTo("EUR");
            assertThat(shipping.packageCosts()).hasSize(1);
            assertThat(shipping.packageCosts().get(0).packageName()).isEqualTo("Small Box");
            assertThat(shipping.packageCosts().get(0).costAmount().value()).isEqualTo(4.99);
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static SourcingOptionsRequest minimalRequest() {
        return SourcingOptionsRequest.builder()
                .order(OrderForSourcingOptionsRequest.builder()
                        .consumer(ConsumerAddressesForSourcingOptions.builder().build())
                        .build())
                .build();
    }

    private static TokenProvider fixedToken(String token) {
        return new TokenProvider() {
            @Override public String getAccessToken() { return token; }
            @Override public void invalidate() {}
        };
    }
}
