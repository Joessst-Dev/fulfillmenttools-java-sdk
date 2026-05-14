# Sourcing Options Client

The Sourcing Options client evaluates order sourcing options to determine the best fulfillment choices. Analyze facility availability, costs, and constraints to compute feasible sourcing solutions for orders.

## Evaluating Sourcing Options

`order` is required; `consumer` within the order is also required:

```java
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsResult;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ConsumerAddressesForSourcingOptions;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    SourcingOptionsResult result = client.sourcingOptions().evaluate(
        SourcingOptionsRequest.builder()
            .order(
                OrderForSourcingOptionsRequest.builder()
                    .consumer(
                        ConsumerAddressesForSourcingOptions.builder()
                            .consumerId("consumer-001")
                            .build()
                    )
                    .build()
            )
            .build()
    );

    System.out.println("Request ID: " + result.id().value());
    System.out.println("Options count: " + result.options().size());
} catch (FulfillmenttoolsException e) {
    System.out.println("Evaluation failed: " + e.getMessage());
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<SourcingOptionsResult> future = client.sourcingOptions().evaluateAsync(
    SourcingOptionsRequest.builder()
        .order(
            OrderForSourcingOptionsRequest.builder()
                .consumer(
                    ConsumerAddressesForSourcingOptions.builder()
                        .consumerId("consumer-001")
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
        System.out.println("Options count: " + result.options().size());
    }
});
```

## API Reference

### evaluate(SourcingOptionsRequest)

Evaluate an order to compute feasible sourcing options. `order` is required.

**Parameters:**
- `request: SourcingOptionsRequest` — Request with order and constraints

**Returns:** `SourcingOptionsResult`

**Throws:** `FulfillmenttoolsException` if the evaluation fails

### evaluateAsync(SourcingOptionsRequest)

Evaluate an order asynchronously to compute feasible sourcing options.

**Parameters:**
- `request: SourcingOptionsRequest` — Request with order and constraints

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
