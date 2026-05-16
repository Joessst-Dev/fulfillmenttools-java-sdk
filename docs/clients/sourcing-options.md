# Sourcing Options Client

The Sourcing Options client evaluates order sourcing options to determine the best fulfillment choices. Analyze facility availability, costs, and constraints to compute feasible sourcing solutions for orders.

The evaluate endpoint takes a `SourcingOptionsRequest` describing the customer's order and returns a `SourcingOptionsResult` containing ranked fulfillment options across your facility network.

## Minimal Example

Only `order` (with `consumer`) is required:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressesForSourcingOptions;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressForSourcingOptions;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

try {
    SourcingOptionsResult result = client.sourcingOptions().evaluate(
        SourcingOptionsRequest.builder()
            .order(
                OrderForSourcingOptionsRequest.builder()
                    .consumer(
                        ConsumerAddressesForSourcingOptions.builder()
                            .addresses(List.of(
                                ConsumerAddressForSourcingOptions.builder()
                                    .country("DE")
                                    .city("Berlin")
                                    .postalCode("10115")
                                    .build()
                            ))
                            .build()
                    )
                    .build()
            )
            .build()
    );

    System.out.println("Request ID: " + result.id().value());
    System.out.println("Options found: " + result.options().size());
} catch (FulfillmenttoolsException e) {
    System.out.println("Evaluation failed: " + e.getMessage());
}
```

## Shipping Order with Line Items

Include `orderLineItems` and shipping `deliveryPreferences` to get facility routing for home delivery:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressesForSourcingOptions;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressForSourcingOptions;
import de.joesst.dev.fulfillmenttools.sourcingoptions.DeliveryPreferences;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferencesShipping;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticleForCreation;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

SourcingOptionsResult result = client.sourcingOptions().evaluate(
    SourcingOptionsRequest.builder()
        .order(
            OrderForSourcingOptionsRequest.builder()
                .consumer(
                    ConsumerAddressesForSourcingOptions.builder()
                        .consumerId("customer-001")
                        .addresses(List.of(
                            ConsumerAddressForSourcingOptions.builder()
                                .country("DE")
                                .firstName("Jane")
                                .lastName("Smith")
                                .street("Unter den Linden")
                                .houseNumber("1")
                                .city("Berlin")
                                .postalCode("10117")
                                .build()
                        ))
                        .build()
                )
                .tenantOrderId(TenantOrderId.builder().value("order-XYZ-001").build())
                .orderLineItems(List.of(
                    OrderLineItemForCreation.builder()
                        .article(
                            OrderLineItemArticleForCreation.builder()
                                .tenantArticleId(TenantArticleId.builder().value("SKU-12345").build())
                                .title("Blue Sneakers")
                                .build()
                        )
                        .quantity(2)
                        .build(),
                    OrderLineItemForCreation.builder()
                        .article(
                            OrderLineItemArticleForCreation.builder()
                                .tenantArticleId(TenantArticleId.builder().value("SKU-67890").build())
                                .title("Cotton T-Shirt")
                                .build()
                        )
                        .quantity(1)
                        .build()
                ))
                .deliveryPreferences(
                    DeliveryPreferences.builder()
                        .shipping(
                            DeliveryPreferencesShipping.builder()
                                .serviceLevel("DELIVERY")
                                .build()
                        )
                        .build()
                )
                .build()
        )
        .build()
);
```

## Click-and-Collect Order

Use `collect` delivery preferences when the customer picks up in store:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressesForSourcingOptions;
import de.joesst.dev.fulfillmenttools.sourcingoptions.DeliveryPreferences;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemForCreation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticleForCreation;
import de.joesst.dev.fulfillmenttools.orders.CollectDelivery;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

SourcingOptionsResult result = client.sourcingOptions().evaluate(
    SourcingOptionsRequest.builder()
        .order(
            OrderForSourcingOptionsRequest.builder()
                .consumer(
                    ConsumerAddressesForSourcingOptions.builder()
                        .consumerId("customer-001")
                        .build()
                )
                .orderLineItems(List.of(
                    OrderLineItemForCreation.builder()
                        .article(
                            OrderLineItemArticleForCreation.builder()
                                .tenantArticleId(TenantArticleId.builder().value("SKU-12345").build())
                                .build()
                        )
                        .quantity(1)
                        .build()
                ))
                .deliveryPreferences(
                    DeliveryPreferences.builder()
                        .collect(List.of(
                            CollectDelivery.builder()
                                .facilityRef(FacilityId.builder().value("fac-001").build())
                                .build()
                        ))
                        .build()
                )
                .build()
        )
        .build()
);
```

## Controlling the Engine with Optimization Hints

Use `optimizationHints` to request a specific number of options, and `resourceInvestment` to trade off computation time vs. result quality (0.0–1.0, higher = more computation):

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressesForSourcingOptions;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OptimizationHints;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ResourceInvestment;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequestAdditionalInfo;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

SourcingOptionsResult result = client.sourcingOptions().evaluate(
    SourcingOptionsRequest.builder()
        .order(
            OrderForSourcingOptionsRequest.builder()
                .consumer(
                    ConsumerAddressesForSourcingOptions.builder()
                        .consumerId("customer-001")
                        .build()
                )
                .build()
        )
        .optimizationHints(
            OptimizationHints.builder()
                .requestedResultCount(5)
                .includeCalculationHints(true)
                .build()
        )
        .resourceInvestment(
            ResourceInvestment.builder()
                .level(0.5)
                .build()
        )
        .additionalInfo(
            SourcingOptionsRequestAdditionalInfo.builder()
                .includeListingCustomAttributes(true)
                .build()
        )
        .build()
);
```

## Reading the Result

Each `SourcingOption` in the result represents one feasible fulfillment plan, ranked by `totalPenalty` (lower is better). An option contains facility nodes, stock transfers between them, associated costs, and rating criteria that contributed to its score:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOption;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionNode;
import de.joesst.dev.fulfillmenttools.sourcingoptions.HandledItem;

for (SourcingOption option : result.options()) {
    System.out.println("Option: " + option.id().value());
    System.out.println("  Penalty: " + option.totalPenalty());
    System.out.println("  Est. delivery: " + option.estimatedDeliveryDate());
    System.out.println("  Valid until: " + option.validUntil());
    System.out.println("  Nodes: " + option.nodes().size());

    // Examine nodes (facility sources)
    for (SourcingOptionNode node : option.nodes()) {
        System.out.println("    Node: " + node.type() + " [" + node.facilityRef().value() + "]");
        for (HandledItem item : node.lineItems()) {
            System.out.println("      - " + item.tenantArticleId().value() + " x " + item.quantity());
        }
    }

    // Examine unassigned items
    if (option.nonAssignedOrderLineItems() != null
            && !option.nonAssignedOrderLineItems().isEmpty()) {
        System.out.println("  WARNING: " + option.nonAssignedOrderLineItems().size()
                + " line item(s) could not be assigned");
    }

    // Examine costs
    if (option.totalCosts() != null) {
        var costs = option.totalCosts();
        System.out.println("  Total cost: " + costs.totalCosts().value() + " " 
            + costs.totalCosts().currency());
    }
}
```

## Retrieving Computed Results

Retrieve a previously computed sourcing options result by ID:

```java
import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;
import de.joesst.dev.fulfillmenttools.NotFoundException;

try {
    SourcingOptionsResult result = client.sourcingOptions().get(
        SourcingOptionsRequestId.builder().value("sor-req-123").build()
    );
    System.out.println("Options count: " + result.options().size());
} catch (NotFoundException e) {
    System.out.println("Sourcing options result not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressesForSourcingOptions;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressForSourcingOptions;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.List;

CompletableFuture<SourcingOptionsResult> future = client.sourcingOptions().evaluateAsync(
    SourcingOptionsRequest.builder()
        .order(
            OrderForSourcingOptionsRequest.builder()
                .consumer(
                    ConsumerAddressesForSourcingOptions.builder()
                        .consumerId("customer-001")
                        .addresses(List.of(
                            ConsumerAddressForSourcingOptions.builder()
                                .country("DE")
                                .postalCode("10115")
                                .build()
                        ))
                        .build()
                )
                .build()
        )
        .build()
);

future.whenComplete((result, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Options found: " + result.options().size());
    }
});
```

## Core Concepts

### Node Types

A `SourcingOption` comprises one or more `SourcingOptionNode` entries, each representing a facility source in the fulfillment plan. Every node has a `NodeType`:

- `SUPPLIER` — external supplier providing stock
- `MANAGED_FACILITY` — fulfillmenttools-managed warehouse or fulfillment center
- `CUSTOMER` — the customer's address (applicable for returns or special handling)

Nodes are identified by typed IDs: `SourcingOptionNodeId` (platform-assigned), with optional references to `FacilityId` (fulfillmenttools facility) and `TenantFacilityId` (tenant-specific facility identifier).

### Handled Items

Line items in a sourcing option are represented as `HandledItem` objects, which combine a tenant-specific article ID (`TenantArticleId`) and quantity. This unified model replaces the previous separate types and appears throughout:

- `SourcingOptionNode.lineItems()` — articles to fulfill from a facility
- `SourcingOptionTransfer.lineItems()` — articles being transferred between nodes
- `SourcingOption.nonAssignedOrderLineItems()` — articles that could not be assigned to any facility

### Cost Breakdown

Sourcing option costs are modeled in `SourcingOptionCosts`, which breaks down the financial impact:

- `salesPrices` — per-article sales prices (list of `SourcingOptionSalesPrice`)
- `totalCosts` — total cost amount (`Money`)
- `totalSalesPriceAmount` — total sales price amount (`Money`)
- `totalShippingCosts` — shipping cost breakdown (`ShippingCosts`)

The `ShippingCosts` type further breaks down transport costs:

- `totalTransportCostAmount` — aggregate transport cost (`Money`)
- `packageCosts` — per-package shipping costs (list of `PackageCost`)

All monetary amounts use the `Money` type: `value` (numeric amount), `currency` (ISO 4217 code), and `decimalPlaces` (decimal precision).

### Transfers and Packaging

Transfers between nodes are captured in `SourcingOptionTransfer`:

- `sourceNodeRef` / `targetNodeRef` — `SourcingOptionNodeId` references to the source and destination facility nodes
- `lineItems` — articles being moved (list of `HandledItem`)
- `packagingInformation` — list of `SourcingOptionsTransferPackagingInformation` describing units and dimensions
- `carrier` — `TransferCarrier` (carrier name and service type)
- `timeLine` — `TransferTimeLine` (departure and arrival times)
- `facilityConnectionRef` — `ConnectionId` linking to the facility connection used

### Rating Results

Each sourcing option includes a list of `SourcingOptionRatingResult` objects representing individual rating criteria that contributed to the overall penalty score:

- `id` — `RatingResultId` (unique identifier for this rating)
- `name` — criterion name (e.g., `DISTANCE`, `COST`)
- `penalty` — the penalty value assigned by this criterion
- `type` — `RatingResultType` (e.g., `StandardRating`, `ToolkitRating`)
- `routingStrategyNodeId` — `RoutingStrategyNodeId` reference to the rating source

### Delivery Cost Composition

The `SourcingOptionsTransferDeliveryCost` type demonstrates the SDK's pattern for composing the OpenAPI schema's `allOf: [Money]` pattern:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsTransferDeliveryCost;
import de.joesst.dev.fulfillmenttools.model.Money;

// Build using composed Money object
var cost = SourcingOptionsTransferDeliveryCost.builder()
    .money(Money.builder()
        .value(25.50)
        .currency("EUR")
        .decimalPlaces(2.0)
        .build())
    .deliveryCostCoefficient(...)
    .build();

// Access monetary fields directly (serialized as flat JSON)
System.out.println(cost.value());           // 25.50
System.out.println(cost.currency());        // "EUR"
System.out.println(cost.decimalPlaces());   // 2.0

// Or access the composed Money object
System.out.println(cost.money().value());   // 25.50
```

This design ensures compatibility with the API's flat JSON structure while maintaining an object-oriented interface in Java.

### Delivery Preferences with Typed IDs

The `DeliveryPreferences` type now uses typed ID references:

- `sourcingOptionRefs` — list of `SourcingOptionsRequestId` (references to pre-computed sourcing option runs)
- `supplyingFacilities` — list of `FacilityId` (facilities to source from, if constraining)

Other fields like `shipping`, `collect`, and `reservationPreferences` remain unchanged and reuse the orders domain types.

## API Reference

### evaluate(SourcingOptionsRequest)

Evaluate an order to compute feasible sourcing options. `order` (with `consumer`) is required.

**Parameters:**
- `request: SourcingOptionsRequest` — Request containing the order, optional delivery preferences, and engine hints

**Returns:** `SourcingOptionsResult`

**Throws:** `FulfillmenttoolsException` if the evaluation fails

### evaluateAsync(SourcingOptionsRequest)

Evaluate an order asynchronously to compute feasible sourcing options.

**Parameters:**
- `request: SourcingOptionsRequest` — Request containing the order, optional delivery preferences, and engine hints

**Returns:** `CompletableFuture<SourcingOptionsResult>`

### get(SourcingOptionsRequestId)

Retrieve a previously computed sourcing options result by ID.

**Parameters:**
- `sourcingOptionsRequestId: SourcingOptionsRequestId` — The sourcing options request identifier

**Returns:** `SourcingOptionsResult`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(SourcingOptionsRequestId)

Retrieve a previously computed sourcing options result by ID asynchronously.

**Parameters:**
- `sourcingOptionsRequestId: SourcingOptionsRequestId` — The sourcing options request identifier

**Returns:** `CompletableFuture<SourcingOptionsResult>`
