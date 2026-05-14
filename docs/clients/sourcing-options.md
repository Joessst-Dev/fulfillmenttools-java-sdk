# Sourcing Options Client

The Sourcing Options client evaluates order sourcing options to determine the best fulfillment choices. Analyze facility availability, costs, and constraints to compute feasible sourcing solutions for orders.

The evaluate endpoint takes an `OrderForSourcingOptionsRequest` describing the customer's order and returns a `SourcingOptionsResult` containing ranked fulfillment options across your facility network.

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

Each `SourcingOption` in the result represents one feasible fulfillment plan, ranked by `totalPenalty` (lower is better):

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOption;

for (SourcingOption option : result.options()) {
    System.out.println("Option: " + option.id());
    System.out.println("  Penalty: " + option.totalPenalty());
    System.out.println("  Est. delivery: " + option.estimatedDeliveryDate());
    System.out.println("  Valid until: " + option.validUntil());
    System.out.println("  Nodes: " + option.nodes().size());

    if (option.nonAssignedOrderLineItems() != null
            && !option.nonAssignedOrderLineItems().isEmpty()) {
        System.out.println("  WARNING: " + option.nonAssignedOrderLineItems().size()
                + " line item(s) could not be assigned");
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
