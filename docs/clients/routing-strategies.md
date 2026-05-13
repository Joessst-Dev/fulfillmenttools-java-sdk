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

**Returns:** `RoutingStrategy`

### list(RoutingStrategyListRequest)

List routing strategies with pagination.

**Returns:** `Page<RoutingStrategy>`

### listAll(RoutingStrategyListRequest)

List all routing strategies.

**Returns:** `Iterable<RoutingStrategy>`
