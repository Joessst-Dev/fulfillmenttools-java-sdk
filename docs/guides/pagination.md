# Pagination

The SDK uses cursor-based pagination for listing resources. Efficiently iterate through large result sets without loading everything into memory.

## Page Object

List operations return a `Page<T>` containing a batch of items:

```java
Page<Order> page = client.orders().list(
    OrderListRequest.builder()
        .size(50)
        .build()
);

System.out.println("Items: " + page.items());  // List<Order>
System.out.println("More: " + page.hasMore()); // boolean
if (page.hasMore()) {
    System.out.println("Cursor: " + page.nextCursor()); // String
}
```

## Manual Pagination

Fetch pages one at a time using cursors:

```java
OrderListRequest.Builder builder = OrderListRequest.builder().size(50);
Page<Order> page = client.orders().list(builder.build());

while (page.hasMore()) {
    processOrders(page.items());
    page = client.orders().list(
        builder.startAfterId(page.nextCursor()).build()
    );
}
processOrders(page.items());
```

## Automatic Iteration

Use `listAll()` to iterate through all pages automatically:

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

Or with streams:

```java
client.orders().listAll(
    OrderListRequest.builder()
        .size(100)
        .build()
)
    .forEach(order -> System.out.println(order.getOrderNumber()));
```

## Lazy Loading

The `Iterable` returned by `listAll()` is lazy—pages are fetched on-demand as you iterate:

```java
Iterable<Order> allOrders = client.orders().listAll(
    OrderListRequest.builder()
        .size(100)
        .build()
);

// No API calls yet
int count = 0;
for (Order order : allOrders) {
    count++;
    if (count >= 10) break; // Only fetches the first page
}
```

## Search Pagination

Search operations support the same pagination patterns as list:

```java
// Single page
Page<Order> results = client.orders().search(
    OrderSearchRequest.builder()
        .orderNumber("ORD-2024-*")
        .size(50)
        .build()
);

// All results
Iterable<Order> allResults = client.orders().searchAll(
    OrderSearchRequest.builder()
        .orderNumber("ORD-2024-*")
        .size(100)
        .build()
);
```

## Async Pagination

Async operations work the same way:

```java
client.orders().listAsync(
    OrderListRequest.builder()
        .size(50)
        .build()
)
    .thenAccept(page -> {
        System.out.println("Items: " + page.items());
        System.out.println("More: " + page.hasMore());
    });
```

## Choosing Page Size

Page size affects latency and memory usage:

- **Small pages (10–25)**: Lower memory per page, more API calls
- **Medium pages (50–100)**: Balanced, typical default
- **Large pages (500+)**: Higher memory, fewer API calls

For example, iterating through 10,000 items:
- Size 100: 100 API calls, minimal memory overhead
- Size 10: 1,000 API calls, more overhead
- Size 1000: 10 API calls, higher memory per page

Choose based on your network latency and memory constraints.
