# Routing Plans Client

The Routing Plans client manages routing configurations. Define and manage routing rules for order fulfillment.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;

// Get a routing plan
RoutingPlan plan = client.routingPlans().get(new RoutingPlanId("rplan-001"));
System.out.println("Name: " + plan.getName());
```

## Listing Routing Plans

```java
Page<RoutingPlan> page = client.routingPlans().list(
    RoutingPlanListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(RoutingPlanId)

Get a routing plan by ID.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `RoutingPlan`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(RoutingPlanId)

Get a routing plan by ID asynchronously.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `CompletableFuture<RoutingPlan>`

### list(RoutingPlanListRequest)

List routing plans with pagination.

**Parameters:**
- `request: RoutingPlanListRequest` — List request with filter and pagination parameters

**Returns:** `Page<RoutingPlan>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(RoutingPlanListRequest)

List routing plans asynchronously.

**Parameters:**
- `request: RoutingPlanListRequest` — List request

**Returns:** `CompletableFuture<Page<RoutingPlan>>`

### listAll(RoutingPlanListRequest)

List all routing plans, automatically iterating through pages.

**Parameters:**
- `request: RoutingPlanListRequest` — List request

**Returns:** `Iterable<RoutingPlan>`

### create(CreateRoutingPlanRequest)

Create a new routing plan.

**Parameters:**
- `request: CreateRoutingPlanRequest` — Create request with routing plan configuration

**Returns:** `RoutingPlan`

**Throws:** `FulfillmenttoolsException` on request failure

### createAsync(CreateRoutingPlanRequest)

Create a new routing plan asynchronously.

**Parameters:**
- `request: CreateRoutingPlanRequest` — Create request

**Returns:** `CompletableFuture<RoutingPlan>`

### update(RoutingPlanId, UpdateRoutingPlanRequest)

Update an existing routing plan.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier
- `request: UpdateRoutingPlanRequest` — Update request with new values and version for optimistic locking

**Returns:** `RoutingPlan`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### updateAsync(RoutingPlanId, UpdateRoutingPlanRequest)

Update an existing routing plan asynchronously.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier
- `request: UpdateRoutingPlanRequest` — Update request

**Returns:** `CompletableFuture<RoutingPlan>`

### delete(RoutingPlanId)

Delete a routing plan by ID.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` on request failure

### deleteAsync(RoutingPlanId)

Delete a routing plan by ID asynchronously.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `CompletableFuture<Void>`
