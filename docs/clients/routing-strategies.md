# Routing Strategies Client

The Routing Strategies client manages routing strategy configurations. Define how orders are routed through your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.RoutingStrategyId;

// Get a routing strategy
RoutingStrategy strategy = client.routingStrategies().get(new RoutingStrategyId("rstrat-001"));
System.out.println("Name: " + strategy.getName());
```

## Listing Routing Strategies

```java
Page<RoutingStrategy> page = client.routingStrategies().list(
    RoutingStrategyListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(RoutingStrategyId)

Get a routing strategy by ID.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier

**Returns:** `RoutingStrategy`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(RoutingStrategyId)

Get a routing strategy by ID asynchronously.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier

**Returns:** `CompletableFuture<RoutingStrategy>`

### list(RoutingStrategyListRequest)

List routing strategies with pagination.

**Parameters:**
- `request: RoutingStrategyListRequest` — List request with filter and pagination parameters

**Returns:** `Page<RoutingStrategy>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(RoutingStrategyListRequest)

List routing strategies asynchronously.

**Parameters:**
- `request: RoutingStrategyListRequest` — List request

**Returns:** `CompletableFuture<Page<RoutingStrategy>>`

### listAll(RoutingStrategyListRequest)

List all routing strategies, automatically iterating through pages.

**Parameters:**
- `request: RoutingStrategyListRequest` — List request

**Returns:** `Iterable<RoutingStrategy>`

### create(CreateRoutingStrategyRequest)

Create a new routing strategy.

**Parameters:**
- `request: CreateRoutingStrategyRequest` — Create request with routing strategy configuration

**Returns:** `RoutingStrategy`

**Throws:** `FulfillmenttoolsException` on request failure

### createAsync(CreateRoutingStrategyRequest)

Create a new routing strategy asynchronously.

**Parameters:**
- `request: CreateRoutingStrategyRequest` — Create request

**Returns:** `CompletableFuture<RoutingStrategy>`

### update(RoutingStrategyId, UpdateRoutingStrategyRequest)

Update an existing routing strategy.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier
- `request: UpdateRoutingStrategyRequest` — Update request with new values and version for optimistic locking

**Returns:** `RoutingStrategy`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### updateAsync(RoutingStrategyId, UpdateRoutingStrategyRequest)

Update an existing routing strategy asynchronously.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier
- `request: UpdateRoutingStrategyRequest` — Update request

**Returns:** `CompletableFuture<RoutingStrategy>`
