# Routing Plans Client

The Routing Plans client provides full CRUD access to routing plans in the fulfillmenttools platform. A routing plan represents the decision-making record for fulfilling a customer order from one or more facilities.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlan;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a routing plan by ID
try {
    RoutingPlan plan = client.routingPlans().get(RoutingPlanId.builder().value("rplan-001").build());
    System.out.println("Status: " + plan.status());
    System.out.println("Facility: " + plan.facilityRef().value());
    System.out.println("Order: " + plan.orderRef().value());
} catch (NotFoundException e) {
    System.out.println("Routing plan not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Routing Plans

`RoutingPlanListRequest` supports filtering by order reference:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlanListRequest;
import de.joesst.dev.fulfillmenttools.id.OrderId;

// List all routing plans for a specific order
Page<RoutingPlan> page = client.routingPlans().list(
    RoutingPlanListRequest.builder()
        .orderRef(OrderId.builder().value("order-001").build())
        .build()
);

for (RoutingPlan plan : page.items()) {
    System.out.println(plan.id().value() + " — " + plan.status());
}
```

Iterate through all routing plans automatically:

```java
Iterable<RoutingPlan> allPlans = client.routingPlans().listAll(
    RoutingPlanListRequest.builder().build()
);

for (RoutingPlan plan : allPlans) {
    System.out.println(plan.id().value());
}
```

## Creating a Routing Plan

`name` is required:

```java
import de.joesst.dev.fulfillmenttools.routingplans.CreateRoutingPlanRequest;

RoutingPlan created = client.routingPlans().create(
    CreateRoutingPlanRequest.builder()
        .name("Standard Routing Plan")
        .build()
);
System.out.println("Created routing plan: " + created.id().value());
```

## Updating a Routing Plan

`version` is required for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.routingplans.UpdateRoutingPlanRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

RoutingPlan plan = client.routingPlans().get(RoutingPlanId.builder().value("rplan-001").build());

RoutingPlan updated = client.routingPlans().update(
    RoutingPlanId.builder().value("rplan-001").build(),
    UpdateRoutingPlanRequest.builder()
        .version(plan.version())
        .facilityRef(FacilityId.builder().value("fac-001").build())
        .build()
);
System.out.println("Updated status: " + updated.status());
```

## Deleting a Routing Plan

```java
client.routingPlans().delete(RoutingPlanId.builder().value("rplan-001").build());
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<RoutingPlan> future = client.routingPlans().getAsync(
    RoutingPlanId.builder().value("rplan-001").build()
);

future.whenComplete((plan, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Status: " + plan.status());
    }
});
```

## API Reference

### get(RoutingPlanId)

Get a routing plan by ID.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `RoutingPlan`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(RoutingPlanId)

Get a routing plan by ID asynchronously.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `CompletableFuture<RoutingPlan>`

### list(RoutingPlanListRequest)

List routing plans with optional order reference filtering.

**Parameters:**
- `request: RoutingPlanListRequest` — List request with optional `orderRef` filter

**Returns:** `Page<RoutingPlan>`

**Throws:** `FulfillmenttoolsException` if the request fails

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

Create a new routing plan. `name` is required.

**Parameters:**
- `request: CreateRoutingPlanRequest` — Create request with routing plan configuration

**Returns:** `RoutingPlan`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateRoutingPlanRequest)

Create a new routing plan asynchronously.

**Parameters:**
- `request: CreateRoutingPlanRequest` — Create request

**Returns:** `CompletableFuture<RoutingPlan>`

### update(RoutingPlanId, UpdateRoutingPlanRequest)

Update an existing routing plan. The `version` field is required for optimistic locking.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier
- `request: UpdateRoutingPlanRequest` — Update request with new values and current version

**Returns:** `RoutingPlan`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

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

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(RoutingPlanId)

Delete a routing plan by ID asynchronously.

**Parameters:**
- `routingPlanId: RoutingPlanId` — The routing plan identifier

**Returns:** `CompletableFuture<Void>`
