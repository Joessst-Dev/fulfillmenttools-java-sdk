# Routing Strategies Client

The Routing Strategies client provides access to routing strategy management in the fulfillmenttools platform. A routing strategy determines how fulfillment orders are routed to facilities; only one strategy can be active at a time.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.RoutingStrategyId;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategy;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a routing strategy by ID
try {
    RoutingStrategy strategy = client.routingStrategies().get(
        RoutingStrategyId.builder().value("rstrat-001").build()
    );
    System.out.println("Name: " + strategy.name());
    System.out.println("In use: " + strategy.inUse());
    System.out.println("Revision: " + strategy.revision());
} catch (NotFoundException e) {
    System.out.println("Routing strategy not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Routing Strategies

List routing strategies with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategyListRequest;

Page<RoutingStrategy> page = client.routingStrategies().list(
    RoutingStrategyListRequest.builder()
        .size(50)
        .build()
);

for (RoutingStrategy strategy : page.items()) {
    System.out.println(strategy.id().value() + " — " + strategy.name());
}
```

Iterate through all routing strategies automatically:

```java
Iterable<RoutingStrategy> allStrategies = client.routingStrategies().listAll(
    RoutingStrategyListRequest.builder()
        .size(100)
        .build()
);

for (RoutingStrategy strategy : allStrategies) {
    System.out.println(strategy.name() + " (in use: " + strategy.inUse() + ")");
}
```

Manual pagination using `nextCursor()`:

```java
Page<RoutingStrategy> page = client.routingStrategies().list(
    RoutingStrategyListRequest.builder().size(20).build()
);

while (page.hasMore()) {
    page = client.routingStrategies().list(
        RoutingStrategyListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (RoutingStrategy strategy : page.items()) {
        System.out.println(strategy.id().value());
    }
}
```

## Creating a Routing Strategy

`nameLocalized` is required:

```java
import de.joesst.dev.fulfillmenttools.routingstrategies.CreateRoutingStrategyRequest;
import java.util.Map;

RoutingStrategy created = client.routingStrategies().create(
    CreateRoutingStrategyRequest.builder()
        .nameLocalized(Map.of("en_US", "Nearest Facility Strategy"))
        .build()
);
System.out.println("Created strategy: " + created.id().value());
```

## Updating a Routing Strategy

`version`, `nameLocalized`, and `rootNode` are required:

```java
import de.joesst.dev.fulfillmenttools.routingstrategies.UpdateRoutingStrategyRequest;
import java.util.Map;

RoutingStrategy strategy = client.routingStrategies().get(
    RoutingStrategyId.builder().value("rstrat-001").build()
);

RoutingStrategy updated = client.routingStrategies().update(
    RoutingStrategyId.builder().value("rstrat-001").build(),
    UpdateRoutingStrategyRequest.builder()
        .version(strategy.version())
        .nameLocalized(Map.of("en_US", "Updated Strategy"))
        .rootNode(strategy.rootNode())
        .build()
);
System.out.println("Updated revision: " + updated.revision());
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<RoutingStrategy> future = client.routingStrategies().getAsync(
    RoutingStrategyId.builder().value("rstrat-001").build()
);

future.whenComplete((strategy, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Name: " + strategy.name());
    }
});
```

## API Reference

### get(RoutingStrategyId)

Get a routing strategy by ID.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier

**Returns:** `RoutingStrategy`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(RoutingStrategyId)

Get a routing strategy by ID asynchronously.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier

**Returns:** `CompletableFuture<RoutingStrategy>`

### list(RoutingStrategyListRequest)

List routing strategies with pagination.

**Parameters:**
- `request: RoutingStrategyListRequest` — List request with `size` and `startAfterId` cursor

**Returns:** `Page<RoutingStrategy>`

**Throws:** `FulfillmenttoolsException` if the request fails

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

Create a new routing strategy. `nameLocalized` is required.

**Parameters:**
- `request: CreateRoutingStrategyRequest` — Create request with routing strategy configuration

**Returns:** `RoutingStrategy`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateRoutingStrategyRequest)

Create a new routing strategy asynchronously.

**Parameters:**
- `request: CreateRoutingStrategyRequest` — Create request

**Returns:** `CompletableFuture<RoutingStrategy>`

### update(RoutingStrategyId, UpdateRoutingStrategyRequest)

Update an existing routing strategy. `version`, `nameLocalized`, and `rootNode` are required.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier
- `request: UpdateRoutingStrategyRequest` — Update request with new values and current version

**Returns:** `RoutingStrategy`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(RoutingStrategyId, UpdateRoutingStrategyRequest)

Update an existing routing strategy asynchronously.

**Parameters:**
- `routingStrategyId: RoutingStrategyId` — The routing strategy identifier
- `request: UpdateRoutingStrategyRequest` — Update request

**Returns:** `CompletableFuture<RoutingStrategy>`
