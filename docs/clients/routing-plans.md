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

**Returns:** `RoutingPlan`

### list(RoutingPlanListRequest)

List routing plans with pagination.

**Returns:** `Page<RoutingPlan>`

### listAll(RoutingPlanListRequest)

List all routing plans.

**Returns:** `Iterable<RoutingPlan>`
