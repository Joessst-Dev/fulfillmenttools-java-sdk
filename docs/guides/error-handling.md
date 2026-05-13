# Error Handling

The SDK provides a rich exception hierarchy for different error scenarios. Understand and handle errors gracefully in your applications.

## Exception Hierarchy

All SDK exceptions inherit from `FulfillmenttoolsException`:

```java
try {
    Order order = client.orders().get(new OrderId("ord-invalid"));
} catch (FulfillmenttoolsException e) {
    System.err.println("API error: " + e.getMessage());
}
```

## Specific Exceptions

### NotFoundException

Thrown when a requested resource does not exist (HTTP 404).

```java
try {
    Order order = client.orders().get(new OrderId("nonexistent"));
} catch (NotFoundException e) {
    System.out.println("Order not found: " + e.getMessage());
}
```

### ValidationException

Thrown when request validation fails (HTTP 422).

```java
try {
    Page<Order> page = client.orders().search(invalidRequest);
} catch (ValidationException e) {
    System.out.println("Invalid request: " + e.getMessage());
}
```

### AuthenticationException

Thrown when authentication fails (HTTP 401).

```java
try {
    Order order = client.orders().get(new OrderId("ord-123"));
} catch (AuthenticationException e) {
    System.out.println("Authentication failed. Refresh credentials.");
}
```

### ConflictException

Thrown when the request conflicts with the current state (HTTP 409).

```java
try {
    // Attempt operation that violates a constraint
} catch (ConflictException e) {
    System.out.println("Conflict: " + e.getMessage());
}
```

### RateLimitException

Thrown when rate limits are exceeded (HTTP 429).

```java
try {
    Order order = client.orders().get(new OrderId("ord-123"));
} catch (RateLimitException e) {
    System.out.println("Rate limited. Retry after: " + e.getRetryAfter());
}
```

### ServerException

Thrown for server-side errors (HTTP 5xx).

```java
try {
    Order order = client.orders().get(new OrderId("ord-123"));
} catch (ServerException e) {
    System.out.println("Server error: " + e.getMessage());
}
```

## Async Error Handling

Handle errors in async operations using `exceptionally()` or `whenComplete()`:

```java
client.orders().getAsync(new OrderId("ord-123"))
    .exceptionally(throwable -> {
        if (throwable instanceof NotFoundException) {
            System.out.println("Order not found");
        } else if (throwable instanceof AuthenticationException) {
            System.out.println("Auth failed");
        }
        return null;
    })
    .thenAccept(order -> {
        if (order != null) {
            System.out.println("Order: " + order.getOrderNumber());
        }
    });
```

Or use `handle()` to process both success and error cases:

```java
client.orders().getAsync(new OrderId("ord-123"))
    .handle((order, throwable) -> {
        if (throwable != null) {
            System.err.println("Error: " + throwable.getMessage());
            return null;
        }
        return order;
    });
```

## Retry Logic

The SDK automatically retries transient failures (5xx errors, timeouts) up to the configured `retryMaxAttempts`. Configure retries when building the client:

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("...")
    .credentials(...)
    .retryMaxAttempts(5)  // Default is 3
    .build();
```

For rate limiting, inspect the `RateLimitException` for retry guidance:

```java
try {
    Order order = client.orders().get(new OrderId("ord-123"));
} catch (RateLimitException e) {
    long retryAfterMs = e.getRetryAfter(); // Milliseconds to wait
    Thread.sleep(retryAfterMs);
    // Retry the operation
}
```
