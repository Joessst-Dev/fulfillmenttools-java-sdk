# Orders Client

The Orders client provides access to order management operations in the fulfillmenttools platform. Retrieve, search, and manage orders across your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.OrderId;

// Get a single order
Order order = client.orders().get(new OrderId("ord-123"));
System.out.println(order.getOrderNumber());

// Get asynchronously
CompletableFuture<Order> futureOrder = client.orders()
    .getAsync(new OrderId("ord-123"));
```

## Listing Orders

List orders with pagination:

```java
Page<Order> page = client.orders().list(
    OrderListRequest.builder()
        .size(50)
        .startAfterId("cursor-abc")
        .build()
);

System.out.println("Orders: " + page.items());
System.out.println("Has more: " + page.hasMore());
if (page.hasMore()) {
    String nextCursor = page.nextCursor();
}
```

Iterate through all orders automatically:

```java
Iterable<Order> allOrders = client.orders().listAll(
    OrderListRequest.builder()
        .size(100)
        .build()
);

for (Order order : allOrders) {
    System.out.println(order.getOrderNumber());
}
```

## Searching Orders

Search for orders by order number or other criteria:

```java
Page<Order> results = client.orders().search(
    OrderSearchRequest.builder()
        .orderNumber("ORD-2024-001")
        .build()
);

System.out.println("Found: " + results.items());
```

Search all matching orders:

```java
Iterable<Order> allMatching = client.orders().searchAll(
    OrderSearchRequest.builder()
        .orderNumber("ORD-2024-*")
        .build()
);
```

## Async Operations

All operations have async variants returning `CompletableFuture`:

```java
client.orders().getAsync(new OrderId("ord-123"))
    .thenApply(Order::getOrderNumber)
    .thenAccept(System.out::println)
    .exceptionally(ex -> {
        ex.printStackTrace();
        return null;
    });
```

## API Reference

### get(OrderId)

Get a single order by ID.

**Parameters:**
- `orderId: OrderId` — The order identifier

**Returns:** `Order`

**Throws:** `NotFoundException` (404), `ValidationException` (422), `AuthenticationException` (401)

### getAsync(OrderId)

Get a single order by ID asynchronously.

**Parameters:**
- `orderId: OrderId` — The order identifier

**Returns:** `CompletableFuture<Order>`

### list(OrderListRequest)

List orders with pagination.

**Parameters:**
- `request: OrderListRequest` — List request with size and cursor

**Returns:** `Page<Order>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(OrderListRequest)

List orders asynchronously.

**Parameters:**
- `request: OrderListRequest` — List request

**Returns:** `CompletableFuture<Page<Order>>`

### listAll(OrderListRequest)

List all orders, automatically iterating through pages.

**Parameters:**
- `request: OrderListRequest` — List request

**Returns:** `Iterable<Order>`

### search(OrderSearchRequest)

Search for orders by criteria.

**Parameters:**
- `request: OrderSearchRequest` — Search request

**Returns:** `Page<Order>`

### searchAsync(OrderSearchRequest)

Search for orders asynchronously.

**Parameters:**
- `request: OrderSearchRequest` — Search request

**Returns:** `CompletableFuture<Page<Order>>`

### searchAll(OrderSearchRequest)

Search for all matching orders, automatically iterating through pages.

**Parameters:**
- `request: OrderSearchRequest` — Search request

**Returns:** `Iterable<Order>`
