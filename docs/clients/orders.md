# Orders Client

The Orders client provides access to order management operations in the fulfillmenttools platform. Retrieve, search, create, and manage orders across your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.orders.Order;

// Get a single order
Order order = client.orders().get(new OrderId("ord-abc123"));
System.out.println(order.id());              // OrderId
System.out.println(order.tenantOrderId());   // TenantOrderId (your external reference)
System.out.println(order.status());          // e.g. "OPEN", "LOCKED", "CANCELLED"

// Get asynchronously
CompletableFuture<Order> futureOrder = client.orders()
    .getAsync(new OrderId("ord-abc123"));
```

## Listing Orders

List orders with pagination:

```java
Page<Order> page = client.orders().list(
    OrderListRequest.builder()
        .size(50)
        .build()
);

for (Order order : page.items()) {
    System.out.println(order.id() + " → " + order.status());
}

// Fetch next page using the cursor
if (page.hasMore()) {
    Page<Order> nextPage = client.orders().list(
        OrderListRequest.builder()
            .size(50)
            .startAfterId(page.nextCursor())
            .build()
    );
}
```

Filter by tenant order ID or consumer ID:

```java
Page<Order> filtered = client.orders().list(
    OrderListRequest.builder()
        .tenantOrderId(new TenantOrderId("EXT-0042"))
        .build()
);
```

Iterate through all orders automatically:

```java
for (Order order : client.orders().listAll(OrderListRequest.builder().size(100).build())) {
    System.out.println(order.id() + " → " + order.status());
}
```

## Searching Orders

Search orders using `OrderSearchQuery`:

```java
// Orders with status OPEN
Page<Order> openOrders = client.orders().search(
    OrderSearchRequest.builder()
        .query(OrderSearchQuery.builder()
            .statusEq("OPEN")
            .build())
        .size(50)
        .build()
);

// Orders for a specific tenant order ID
Page<Order> byExternalRef = client.orders().search(
    OrderSearchRequest.builder()
        .query(OrderSearchQuery.builder()
            .tenantOrderIdEq("EXT-0042")
            .build())
        .build()
);

// Orders placed in a time range
Page<Order> recent = client.orders().search(
    OrderSearchRequest.builder()
        .query(OrderSearchQuery.builder()
            .orderDateBetween(
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-01-31T23:59:59Z"))
            .build())
        .size(100)
        .build()
);
```

Combine multiple conditions:

```java
// OPEN or LOCKED orders for a specific consumer
Page<Order> results = client.orders().search(
    OrderSearchRequest.builder()
        .query(OrderSearchQuery.builder()
            .statusIn("OPEN", "LOCKED")
            .consumerIdEq("customer-42")
            .build())
        .build()
);
```

Iterate through all matching orders:

```java
for (Order order : client.orders().searchAll(
        OrderSearchRequest.builder()
            .query(OrderSearchQuery.builder().statusEq("OPEN").build())
            .build())) {
    System.out.println(order.id());
}
```

## Creating an Order

`orderDate`, `orderLineItems`, and `consumer` are required:

```java
import de.joesst.dev.fulfillmenttools.orders.*;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;

Order created = client.orders().create(
    CreateOrderRequest.builder()
        .orderDate(Instant.now())
        .tenantOrderId(new TenantOrderId("EXT-0042"))
        .consumer(OrderForCreationConsumer.builder()
            .email("jane.doe@example.com")
            .addresses(List.of(new ConsumerAddress(
                "Unter den Linden",  // street
                "1",                 // houseNumber
                "Berlin",            // city
                "10117",             // postalCode
                "DE",                // country (ISO 3166-1 alpha-2)
                null, null,
                "Jane", "Doe",
                null, null, null,
                "jane.doe@example.com",
                "POSTAL_ADDRESS",
                null, null, null
            )))
            .build())
        .orderLineItems(List.of(
            OrderLineItemForCreation.builder()
                .article(OrderLineItemArticleForCreation.builder()
                    .tenantArticleId(new TenantArticleId("SKU-001"))
                    .title("Blue Widget")
                    .build())
                .quantity(2)
                .build()
        ))
        .build()
);

System.out.println(created.id());           // platform-assigned OrderId
System.out.println(created.tenantOrderId()); // TenantOrderId("EXT-0042")
```

## Updating an Order

`version` is required for optimistic locking:

```java
Order updated = client.orders().update(
    new OrderId("ord-abc123"),
    UpdateOrderRequest.builder()
        .version(order.version())
        .comment("Address corrected by customer service")
        .consumer(OrderForCreationConsumer.builder()
            .email("corrected@example.com")
            .build())
        .build()
);
```

## Cancelling an Order

```java
// Cancel with a reason
Order cancelled = client.orders().cancel(
    new OrderId("ord-abc123"),
    CancelOrderRequest.builder()
        .version(order.version())
        .cancelationReasonId("CUSTOMER_REQUEST")
        .build()
);

// Force cancel (bypasses normal validation)
Order forceCancelled = client.orders().forceCancel(
    new OrderId("ord-abc123"),
    order.version()
);
```

## Unlocking an Order

```java
// Release an order from its locked state immediately
Order unlocked = client.orders().unlock(
    new OrderId("ord-abc123"),
    order.version()
);

// Unlock at a specific future time
Order scheduledUnlock = client.orders().unlock(
    new OrderId("ord-abc123"),
    order.version(),
    Instant.parse("2024-06-01T08:00:00Z")
);
```

## Deleting an Order

```java
client.orders().delete(new OrderId("ord-abc123"));
```

## Async Operations

Every method has an async variant returning `CompletableFuture`:

```java
client.orders().getAsync(new OrderId("ord-abc123"))
    .thenAccept(order -> System.out.println(order.status()))
    .exceptionally(ex -> {
        System.err.println("Failed: " + ex.getMessage());
        return null;
    });

// Create and immediately list the result
client.orders().createAsync(request)
    .thenCompose(order -> client.orders().getAsync(order.id()))
    .thenAccept(order -> System.out.println("Confirmed: " + order.id()));
```

## Error Handling

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.NotFoundException;

try {
    Order order = client.orders().get(new OrderId("ord-abc123"));
} catch (NotFoundException e) {
    System.err.println("Order not found");
} catch (FulfillmenttoolsException e) {
    System.err.println("Request failed: " + e.getMessage());
}
```

## API Reference

### get(OrderId)

Get a single order by ID.

**Parameters:**
- `orderId: OrderId` — The order identifier

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(OrderId)

Get a single order by ID asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier

**Returns:** `CompletableFuture<Order>`

### list(OrderListRequest)

List orders with optional filtering and pagination.

**Parameters:**
- `request: OrderListRequest` — size, startAfterId cursor, optional tenantOrderId / consumerId filters

**Returns:** `Page<Order>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(OrderListRequest)

List orders asynchronously.

**Parameters:**
- `request: OrderListRequest` — List request

**Returns:** `CompletableFuture<Page<Order>>`

### listAll(OrderListRequest)

List all orders, automatically iterating through all pages.

**Parameters:**
- `request: OrderListRequest` — List request

**Returns:** `Iterable<Order>`

### search(OrderSearchRequest)

Search orders using a structured `OrderSearchQuery`.

**Parameters:**
- `request: OrderSearchRequest` — Search query, size, and pagination cursors

**Returns:** `Page<Order>`

**Throws:** `FulfillmenttoolsException` on request failure

### searchAsync(OrderSearchRequest)

Search orders asynchronously.

**Parameters:**
- `request: OrderSearchRequest` — Search request

**Returns:** `CompletableFuture<Page<Order>>`

### searchAll(OrderSearchRequest)

Search all matching orders, automatically iterating through all pages.

**Parameters:**
- `request: OrderSearchRequest` — Search request

**Returns:** `Iterable<Order>`

### create(CreateOrderRequest)

Create a new order. `orderDate`, `orderLineItems`, and `consumer` are required.

**Parameters:**
- `request: CreateOrderRequest` — Create order request

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### createAsync(CreateOrderRequest)

Create a new order asynchronously.

**Parameters:**
- `request: CreateOrderRequest` — Create order request

**Returns:** `CompletableFuture<Order>`

### update(OrderId, UpdateOrderRequest)

Update an existing order. `version` is required for optimistic locking.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `request: UpdateOrderRequest` — Fields to update plus the current version

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### updateAsync(OrderId, UpdateOrderRequest)

Update an existing order asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `request: UpdateOrderRequest` — Update request

**Returns:** `CompletableFuture<Order>`

### delete(OrderId)

Delete an order by ID.

**Parameters:**
- `orderId: OrderId` — The order identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` on request failure

### deleteAsync(OrderId)

Delete an order by ID asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier

**Returns:** `CompletableFuture<Void>`

### cancel(OrderId, CancelOrderRequest)

Cancel an order. `version` is required; `cancelationReasonId` is optional.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `request: CancelOrderRequest` — Cancellation request

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### cancelAsync(OrderId, CancelOrderRequest)

Cancel an order asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `request: CancelOrderRequest` — Cancellation request

**Returns:** `CompletableFuture<Order>`

### forceCancel(OrderId, int)

Force cancel an order, bypassing normal validation.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `version: int` — Current version for optimistic locking

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### forceCancelAsync(OrderId, int)

Force cancel an order asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<Order>`

### unlock(OrderId, int)

Unlock an order immediately.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `version: int` — Current version for optimistic locking

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### unlock(OrderId, int, Instant)

Unlock an order at a specific target time.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `version: int` — Current version for optimistic locking
- `targetTime: Instant` — Time at which to unlock the order

**Returns:** `Order`

**Throws:** `FulfillmenttoolsException` on request failure

### unlockAsync(OrderId, int)

Unlock an order asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<Order>`

### unlockAsync(OrderId, int, Instant)

Unlock an order asynchronously at a specific target time.

**Parameters:**
- `orderId: OrderId` — The order identifier
- `version: int` — Current version for optimistic locking
- `targetTime: Instant` — Time at which to unlock the order

**Returns:** `CompletableFuture<Order>`
