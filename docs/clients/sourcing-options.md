# Sourcing Options Client

The Sourcing Options client evaluates order sourcing options to determine the best fulfillment choices. Analyze facility availability, costs, and constraints to compute feasible sourcing solutions for orders.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;

// Evaluate sourcing options for an order
SourcingOptionsResult result = client.sourcingOptions().evaluate(
    SourcingOptionsRequest.builder()
        .order(/* order details */)
        .build()
);

System.out.println("Options: " + result.getOptions());
```

## Retrieving Computed Results

Retrieve a previously computed sourcing options result by ID:

```java
SourcingOptionsResult result = client.sourcingOptions().get(
    new SourcingOptionsRequestId("sor-req-123")
);
```

## API Reference

### evaluate(SourcingOptionsRequest)

Evaluate an order to compute feasible sourcing options.

**Parameters:**
- `request: SourcingOptionsRequest` — Request with order and constraints

**Returns:** `SourcingOptionsResult`

**Throws:** `FulfillmenttoolsException` on evaluation failure

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

**Throws:** `FulfillmenttoolsException` on request failure or result not found

### getAsync(SourcingOptionsRequestId)

Retrieve a previously computed sourcing options result by ID asynchronously.

**Parameters:**
- `sourcingOptionsRequestId: SourcingOptionsRequestId` — The sourcing options request identifier

**Returns:** `CompletableFuture<SourcingOptionsResult>`
